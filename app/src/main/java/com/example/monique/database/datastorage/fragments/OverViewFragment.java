package com.example.monique.database.datastorage.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.monique.database.R;
import com.example.monique.database.datastorage.adapters.ContactCursorAdapter;
import com.example.monique.database.datastorage.contract.ContactContract;
import com.example.monique.database.datastorage.provider.ContactContentProvider;

public class OverViewFragment extends ListFragment implements
        LoaderManager.LoaderCallbacks<Cursor> {

    // Private cursor adapter.
    private ContactCursorAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_view, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setDividerHeight(2);
        fillData();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //TODO: implement
        return;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {ContactContract.ContactEntry._ID, ContactContract.ContactEntry.COLUMN_NAME_CONTACT_NAME};
        CursorLoader cursorLoader = new CursorLoader(getContext(),
                ContactContentProvider.CONTENT_URI, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    private void fillData() {
        // Fields from the database (projection).
        String[] from = new String[] { ContactContract.ContactEntry.COLUMN_NAME_CONTACT_NAME };

        getLoaderManager().initLoader(0, null, this);
        Cursor test = getContext().getContentResolver().query(ContactContentProvider.CONTENT_URI,
                from, null, null, null);
        adapter = new ContactCursorAdapter(getActivity(), test, 0);

        setListAdapter(adapter);
    }
}
