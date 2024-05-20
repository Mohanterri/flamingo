package com.aves.flamingodb.Database;


import com.aves.flamingodb.App.models.QuerySnapshot;

public class Tasks extends QuerySnapshot {

    private String source;

    public Tasks(Query source, DatabaseStore databaseStore) {
        super(source, databaseStore);
    }

    @Override
    public void addOnCompleteListener(OnCompleteListener eventListener) {
        super.addOnCompleteListener(eventListener);
    }
}
