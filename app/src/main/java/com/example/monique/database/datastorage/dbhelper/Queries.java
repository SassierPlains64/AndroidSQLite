package com.example.monique.database.datastorage.dbhelper;

import com.example.monique.database.datastorage.contract.ContactContract.ContactEntry;

/**
 * Created by Monique on 20-10-2015.
 */
public final class Queries {
    public static final String TEXT_TYPE = " TEXT";
    public static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_CONTACTS =
            "CREATE TABLE " + ContactEntry.TABLE_NAME + "( " +
                    ContactEntry._ID + "INTEGER PRIMARY KEY," +
                    ContactEntry.COLUMN_NAME_CONTACT_NAME + TEXT_TYPE + COMMA_SEP +
                    ContactEntry.COLUMN_NAME_CONTACT_EMAIL + TEXT_TYPE +
                    ")";
    public static final String SQL_DROP_CONTACTS =
            "DROP TABLE IF EXISTS " + ContactEntry.TABLE_NAME;
}
