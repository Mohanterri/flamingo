package com.aves.flamingodb.Database;

import android.util.Log;

import com.aves.flamingodb.App.models.QuerySnapshot;

public class Tasks extends QuerySnapshot {

    public Tasks(Query source, DatabaseStore databaseStore, String flagQuerry) {
        super(source, databaseStore, flagQuerry);
    }

    @Override
    public void addOnCompleteListener(OnCompleteListener eventListener) {
        super.addOnCompleteListener(eventListener);
    }
}
