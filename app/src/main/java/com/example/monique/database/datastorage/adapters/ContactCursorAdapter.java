package com.example.monique.database.datastorage.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.monique.database.R;
import com.example.monique.database.datastorage.contract.ContactContract;

public class ContactCursorAdapter extends CursorAdapter{

    private LayoutInflater mInflater;

    public ContactCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mInflater.inflate(R.layout.record_row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textView = (TextView)view.findViewById(R.id.contactName);
        textView.setText(cursor.getString(cursor.getColumnIndex(ContactContract.ContactEntry.COLUMN_NAME_CONTACT_NAME)));
    }
}
