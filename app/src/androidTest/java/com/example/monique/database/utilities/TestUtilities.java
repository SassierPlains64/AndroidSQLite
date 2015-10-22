package com.example.monique.database.utilities;

import android.content.ContentValues;
import android.test.AndroidTestCase;

import com.example.monique.database.datastorage.contract.ContactContract;

public class TestUtilities extends AndroidTestCase {

    static ContentValues createContactValues() {
        ContentValues values = new ContentValues();
        values.put(ContactContract.ContactEntry.COLUMN_NAME_CONTACT_NAME, "John Doe");
        values.put(ContactContract.ContactEntry.COLUMN_NAME_CONTACT_EMAIL, "john_doe@gmail.com");
        return values;
    }

}