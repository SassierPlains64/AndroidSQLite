package com.example.monique.database.datalayer;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.example.monique.database.datastorage.contract.ContactContract;
import com.example.monique.database.datastorage.dbhelper.ContactDbHelper;

import java.util.HashSet;

public class DatabaseTest extends AndroidTestCase {

    private static final String LOG_TAG = DatabaseTest.class.getSimpleName();

    void deleteTheDatabase() {
        mContext.deleteDatabase(ContactDbHelper.DATABASE_NAME);
    }

    public void setUp() {
        deleteTheDatabase();
    }

    public void testCreateDb() throws Throwable {
        final HashSet<String> tableNameHashSet = new HashSet<String>();
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

        HashSet<String> contactColumnHashSet = new HashSet<String>();
        contactColumnHashSet.add(ContactContract.ContactEntry._ID);
        contactColumnHashSet.add(ContactContract.ContactEntry.COLUMN_NAME_CONTACT_NAME);
        contactColumnHashSet.add(ContactContract.ContactEntry.COLUMN_NAME_CONTACT_EMAIL);

        int columnNameIndex = c.getColumnIndex("name");
        do {
            String columnName = c.getString(columnNameIndex);
            contactColumnHashSet.remove(columnName);
        } while (c.moveToNext());

        assertTrue("Error: The database does not contain all of the specified columns",
                contactColumnHashSet.isEmpty());
        db.close();
    }

}