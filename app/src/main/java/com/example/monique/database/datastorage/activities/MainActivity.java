package com.example.monique.database.datastorage.activities;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.monique.database.R;
import com.example.monique.database.datastorage.fragments.MainFragment;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Check if the application uses the layout with the fragment container
        if (findViewById(R.id.fragment_container) != null){
            // However, if the activity is being restored, we don't have to do anything and should
            // return or else we might end up with overlapping fragments
            if(savedInstanceState != null) {
                return;
            }
            // Create a new fragment to be placed in the layout
            MainFragment fragment = new MainFragment();
            // In case this activity was raised by an Intent, pass the extras from the Intent to
            // the fragment as arguments
            fragment.setArguments(getIntent().getExtras());
            // Add the fragment to the 'fragment_container' layout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment).commit();
        }
    }
}
