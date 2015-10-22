package com.example.monique.database.datastorage.fragments;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.monique.database.R;
import com.example.monique.database.datastorage.contract.ContactContract.ContactEntry;
import com.example.monique.database.datastorage.dbhelper.ContactDbHelper;

public class MainFragment extends Fragment {

    SQLiteDatabase db;

    private EditText name;
    private EditText email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_view, container, false);

        name = (EditText)rootView.findViewById(R.id.editName);
        email = (EditText)rootView.findViewById(R.id.editEmail);
        final Button save = (Button)rootView.findViewById(R.id.saveButton);
        final Button count = (Button)rootView.findViewById(R.id.countButton);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = new ContactDbHelper(getActivity()).getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(ContactEntry.COLUMN_NAME_CONTACT_NAME, name.getText().toString());
                values.put(ContactEntry.COLUMN_NAME_CONTACT_EMAIL, email.getText().toString());

                long newRowId = db.insert(ContactEntry.TABLE_NAME, null, values);

                if(newRowId >= 0) {
                    Toast.makeText(getActivity(), "Successfully inserted a new record: " + newRowId,
                            Toast.LENGTH_SHORT).show();
                }

                clearControls();
                db.close();
            }
        });

        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new ContactDbHelper(getActivity()).getReadableDatabase();

                String[] projection = {ContactEntry.COLUMN_NAME_CONTACT_NAME};

                Cursor c = db.query(
                        ContactEntry.TABLE_NAME,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        null);

                if (c.moveToFirst()) {
                    Toast.makeText(getActivity(), "Found " + c.getCount() + " records in the database",
                            Toast.LENGTH_SHORT).show();
                }
                c.close();
                db.close();
            }
        });

        return rootView;
    }

    private void clearControls() {
        name.setText("");
        email.setText("");
    }
}