/**
 * Created by Bas Martens on October 22nd, 2015
 */

package com.example.monique.database.datastorage.contract;

import android.provider.BaseColumns;

/**
 * This class defines the contract for the Contact table of the SQLite database used in this
 * application. It could also be viewed as the 'scheme' for the database table.
 */
public final class ContactContract {

    /**
     * To prevent someone from accidentally instantiating this class, we give it an empty
     * constructor.
     */
    public ContactContract(){}

    /**
     * The actual Contract class. This can be seen as the 'scheme' of the database table "contacts".
     */
    public static abstract class ContactEntry implements BaseColumns {
        public static final String TABLE_NAME = "contacts";
        public static final String COLUMN_NAME_CONTACT_NAME = "name";
        public static final String COLUMN_NAME_CONTACT_EMAIL = "email";
    }
}
