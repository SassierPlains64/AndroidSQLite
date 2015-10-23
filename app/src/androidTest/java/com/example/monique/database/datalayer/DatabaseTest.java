/**
 * Created by Bas Martens on October 22nd, 2015
 */

package com.example.monique.database.datalayer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import com.example.monique.database.datastorage.contract.ContactContract;
import com.example.monique.database.datastorage.dbhelper.ContactDbHelper;
import com.example.monique.database.utilities.TestUtilities;

import java.util.HashSet;

/**
 * This class derives from the AndroidTestCase class, and defines several test cases for testing of
 * the database functionality, such as storing and retrieving data.
 */
public class DatabaseTest extends AndroidTestCase {

    private static final String LOG_TAG = DatabaseTest.class.getSimpleName();

    /**
     * Called before each test case. Used to quickly dispose of the database, so that each test case
     * is run against a new database.
     */
    void deleteTheDatabase() {
        mContext.deleteDatabase(ContactDbHelper.DATABASE_NAME);
    }

    /**
     * Called by the testing framework prior to each test case.
     */
    public void setUp() {
        deleteTheDatabase();
    }

    /**
     * Method to test the creation of the database. It does NOT test the functionality of the database,
     * it only tests whether the database is constructed according to the defined contract.
     * @throws Throwable
     */
    public void testCreateDb() throws Throwable {
        final HashSet<String> tableNameHashSet = new HashSet<>();
        tableNameHashSet.add(ContactContract.ContactEntry.TABLE_NAME);

        mContext.deleteDatabase(ContactDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new ContactDbHelper(this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type = 'table'", null);

        assertTrue("Error: Database has not been created correctly",
                c.moveToFirst());

        do {
            tableNameHashSet.remove(c.getString(0));
        } while (c.moveToNext());

        assertTrue("Error: Database did not contain the specified tables",
                tableNameHashSet.isEmpty());

        c = db.rawQuery("PRAGMA table_info(" + ContactContract.ContactEntry.TABLE_NAME + ")",
                null);

        assertTrue("Error: Unable to query for table information",
                c.moveToFirst());

        HashSet<String> contactColumnHashSet = new HashSet<>();
        contactColumnHashSet.add(ContactContract.ContactEntry._ID);
        contactColumnHashSet.add(ContactContract.ContactEntry.COLUMN_NAME_CONTACT_NAME);
        contactColumnHashSet.add(ContactContract.ContactEntry.COLUMN_NAME_CONTACT_EMAIL);

        int columnNameIndex = c.getColumnIndex("name");
        do {
            Log.d(LOG_TAG, "c.getString: " + c.getString(columnNameIndex));
            String columnName = c.getString(columnNameIndex);
            contactColumnHashSet.remove(columnName);
        } while (c.moveToNext());

        assertTrue("Error: The database does not contain all of the specified columns",
                contactColumnHashSet.isEmpty());
        c.close();
        db.close();
    }

    /**
     * Method which tests the insertion and retrieving of data from the created database. It creates
     * a set of test values of type ContactEntry, which is added to the database. Later on, using
     * a cursor, the data is retrieved from the database, and compared with the values which where
     * inserted, to check for any mismatch.
     */
    public void testContactTable() {
        ContactDbHelper helper = new ContactDbHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contactValues = TestUtilities.createContactValues();

        long contactRowId = db.insert(ContactContract.ContactEntry.TABLE_NAME, null, contactValues);
        assertTrue(contactRowId != -1);

        Cursor contactCursor = db.query(
                ContactContract.ContactEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        assertTrue("Error: No records returned from contact query", contactCursor.moveToFirst());

        TestUtilities.validateCurrentRecord("testContactTable contactEntry failed to validate",
                contactCursor, contactValues);

        assertFalse("Error: More than one record returned from contact query",
                contactCursor.moveToNext());

        contactCursor.close();
        db.close();
    }

}