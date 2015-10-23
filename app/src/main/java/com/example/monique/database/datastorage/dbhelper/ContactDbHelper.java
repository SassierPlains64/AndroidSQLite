/**
 * Created by Bas Martens on October 22nd, 2015
 */

package com.example.monique.database.datastorage.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * This class derives from the SQLiteOpenHelper class, and is used to get a reference to the actual
 * SQLite database used in this application. It is also responsible for the creation, upgrading and
 * downgrading of the database.
 */
public class ContactDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "contacts.db";

    /**
     * The constructor of this class.
     * @param context   The context in which this method takes place.
     */
    public ContactDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called at the moment of creation of this class. It also creates the SQLite database used
     * by this application.
     * @param db    A reference to the internal SQLite database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the database and tables
        db.execSQL(Queries.SQL_CREATE_CONTACTS);
    }

    /**
     * Called when the database needs to be updated.
     * @param db            The database to update.
     * @param oldVersion    The previous version of the database.
     * @param newVersion    The new (current) version of the database.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // For now, simply drop the current database and create a new one
        db.execSQL(Queries.SQL_DROP_CONTACTS);
        onCreate(db);
    }

    /**
     * Called when the database is being downgraded.
     * @param db            The database to downgrade.
     * @param oldVersion    The previous version of the database.
     * @param newVersion    The new (current) version of the database.
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // For now, simply call onUpgrade
        onUpgrade(db, oldVersion, newVersion);
    }
}
