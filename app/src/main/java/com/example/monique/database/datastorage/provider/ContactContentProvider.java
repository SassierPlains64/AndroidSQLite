/**
 * Created by Bas Martens on October 23rd, 2015
 */

package com.example.monique.database.datastorage.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.example.monique.database.datastorage.contract.ContactContract;
import com.example.monique.database.datastorage.dbhelper.ContactDbHelper;

import java.util.Arrays;
import java.util.HashSet;

public class ContactContentProvider extends ContentProvider {

    // Link to the database
    private ContactDbHelper helper;

    // Constants used by the UriMatcher
    private static final int CONTACTS = 10;
    private static final int CONTACT_ID = 20;
    private static final String AUTHORITY = "com.example.monique.database.datastorage.provider";
    private static final String BASE_PATH = "contacts";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
        + "/" + BASE_PATH);
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/contacts";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/contact";
    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, CONTACTS);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", CONTACT_ID);
    }

    @Override
    public boolean onCreate() {
        helper = new ContactDbHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        // Using a SQLiteQueryBuilder instead of a call to the query() method.
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // Check if the user requested a column which does not exist.
        checkColumns(projection);

        // Set the table.
        queryBuilder.setTables(ContactContract.ContactEntry.TABLE_NAME);

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case CONTACTS:
                break;
            case CONTACT_ID:
                // Adding the ID to the original query.
                queryBuilder.appendWhere(ContactContract.ContactEntry._ID + "="
                    + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs,
                null, null, sortOrder);

        // Make sure that potential listeners are getting notified.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase db = helper.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case CONTACTS:
                id = db.insert(ContactContract.ContactEntry.TABLE_NAME, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase db = helper.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case CONTACTS:
                rowsDeleted = db.delete(ContactContract.ContactEntry.TABLE_NAME, selection,
                        selectionArgs);
                break;
            case CONTACT_ID:
                String id = uri.getLastPathSegment();
                if(TextUtils.isEmpty(selection)) {
                    rowsDeleted = db.delete(ContactContract.ContactEntry.TABLE_NAME,
                            ContactContract.ContactEntry._ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = db.delete(ContactContract.ContactEntry.TABLE_NAME,
                            ContactContract.ContactEntry._ID + "=" + id
                            + " AND " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknow URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase db = helper.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case CONTACTS:
                rowsUpdated = db.update(ContactContract.ContactEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            case CONTACT_ID:
                String id = uri.getLastPathSegment();
                if(TextUtils.isEmpty(selection)) {
                    rowsUpdated = db.update(ContactContract.ContactEntry.TABLE_NAME,
                            values,
                            ContactContract.ContactEntry._ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = db.update(ContactContract.ContactEntry.TABLE_NAME,
                            values,
                            ContactContract.ContactEntry._ID + "=" + id
                            + " AND "
                            + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    private void checkColumns(String[] projection) {
        String[] available = {
                ContactContract.ContactEntry._ID,
                ContactContract.ContactEntry.COLUMN_NAME_CONTACT_NAME,
                ContactContract.ContactEntry.COLUMN_NAME_CONTACT_EMAIL
        };
        if(projection != null) {
            HashSet<String> requestedColumns = new HashSet<>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<>(Arrays.asList(available));

            // Check if all columns which where requested are available.
            if(!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }
}
