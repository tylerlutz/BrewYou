package com.tylerlutz.brewyou;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Tyler on 11/27/15.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "cxojXRFsShXR2kiSQEmcXwM2MwlexHzTzmvrUc8m", "eV4EY8gydxRcDP73OW0VjiqxFylky8LpPnzNOAUx");
    }
}
