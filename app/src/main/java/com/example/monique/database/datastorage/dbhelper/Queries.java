/**
 * Created by Bas Martens on October 22nd, 2015
 */

package com.example.monique.database.datastorage.dbhelper;

import com.example.monique.database.datastorage.contract.ContactContract.ContactEntry;

/**
 * This class contains all of the queries used in the database actions taken in this application.
 */
public final class Queries {
    public static final String TEXT_TYPE = " TEXT NOT NULL";
    public static final String COMMA_SEP = ",";

    // Query which creates the table according to the ContactEntry inner class in the ContactContract class.
    public static final String SQL_CREATE_CONTACTS =
            "CREATE TABLE " + ContactEntry.TABLE_NAME + " (" +
                    ContactEntry._ID + " INTEGER PRIMARY KEY," +
                    ContactEntry.COLUMN_NAME_CONTACT_NAME + TEXT_TYPE + COMMA_SEP +
                    ContactEntry.COLUMN_NAME_CONTACT_EMAIL + TEXT_TYPE +
                    ")";

    // Query which drops (deletes) the contact table from the database.
    public static final String SQL_DROP_CONTACTS =
            "DROP TABLE IF EXISTS " + ContactEntry.TABLE_NAME;
}
