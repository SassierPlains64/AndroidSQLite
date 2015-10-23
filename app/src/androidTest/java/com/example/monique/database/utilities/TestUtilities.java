/**
 * Created by Bas Martens on October 23rd, 2015
 */

package com.example.monique.database.utilities;

import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;

import com.example.monique.database.datastorage.contract.ContactContract;

import java.util.Map;
import java.util.Set;

/**
 * This class provides several helper methods used throughout the rest of the test framework for
 * this application.
 */
public class TestUtilities extends AndroidTestCase {

    /**
     * Creates a set of test values for testing the contact table of the database.
     * @return  ContentValues object containing test values.
     */
    public static ContentValues createContactValues() {
        ContentValues values = new ContentValues();
        values.put(ContactContract.ContactEntry.COLUMN_NAME_CONTACT_NAME, "John Doe");
        values.put(ContactContract.ContactEntry.COLUMN_NAME_CONTACT_EMAIL, "john_doe@gmail.com");
        return values;
    }

    /**
     * This method validates the data in the given cursor with the data of the given ContentValues
     * object, making it easy to determine whether data was saved in the database successfully and
     * correctly.
     * @param error             The error message to give when the data doesn't add up.
     * @param valueCursor       The cursor containing data retrieved from the database.
     * @param expectedValues    The data which is expected to be returned from the database.
     */
    public static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == 1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                "' did not match the expected value '" +
                expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }
}