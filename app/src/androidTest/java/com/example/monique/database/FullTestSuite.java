/**
 * Created by Bas Martens on October 22nd, 2015
 */

package com.example.monique.database;

import android.test.suitebuilder.TestSuiteBuilder;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * This class derives from the TestSuite class and is used to easily run all of the defined test
 * cases defined in (sub)packages under the 'main' package of '.database'.
 */
public class FullTestSuite extends TestSuite {

    /**
     * Method which gets called in the test framework.
     * @return  The TestSuite containing all of the defined tests in this package and all packages
     *          'under' it.
     */
    public static Test suite() {
        return new TestSuiteBuilder(FullTestSuite.class)
                .includeAllPackagesUnderHere().build();
    }

    /**
     * The constructor of this class.
     */
    public FullTestSuite() {
        super();
    }
}