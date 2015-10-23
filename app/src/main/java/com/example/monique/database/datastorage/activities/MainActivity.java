/**
 * Created by Bas Martens on October 22nd, 2015
 */

package com.example.monique.database.datastorage.activities;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.example.monique.database.R;
import com.example.monique.database.datastorage.fragments.MainFragment;
import com.example.monique.database.datastorage.fragments.OverViewFragment;
import com.example.monique.database.datastorage.interfaces.ShowOverViewListener;

/**
 * The main activity of the application. It displays the content using fragments.
 */
public class MainActivity extends FragmentActivity implements
        ShowOverViewListener{

    /**
     * Called on the moment of creation of this activity.
     * @param savedInstanceState    Any possibly saved data from a previous instance of this activity.
     */
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

    @Override
    public void showOverViewFragment() {
        OverViewFragment overViewFragment = new OverViewFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, overViewFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
