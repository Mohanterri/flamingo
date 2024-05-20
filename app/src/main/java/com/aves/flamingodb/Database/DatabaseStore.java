package com.aves.flamingodb.Database;

import static com.aves.flamingodb.Database.Utils.checkNotNull;

import com.aves.flamingodb.App.FlamingoApp;

import java.util.ArrayList;

public class DatabaseStore{

    private static DatabaseStore instance;
    private static FlamingoApp flamingoApp;

    volatile String collection;
    volatile ArrayList<String> filters = new ArrayList<>();

    private DatabaseStore(FlamingoApp flamingoApp){
        DatabaseStore.flamingoApp = flamingoApp;
    }

    public static DatabaseStore getInstance() {
        FlamingoApp flamingoApp = FlamingoApp.getInstance();
        checkNotNull(flamingoApp, "Provided FlamingoApp must not be null.");
        return getInstance(flamingoApp);
    }

    private static DatabaseStore getInstance(FlamingoApp flamingoApp) {
        instance = (instance != null) ? instance : new DatabaseStore(flamingoApp);
        return instance;
    }

    public DocumentReference collection(String collection){
        checkNotNull(collection, "Provided collection must not be null.");
        this.collection = collection;
        return new DocumentReference(instance);
    }

    public FlamingoApp getFlamingoApp() {
        return flamingoApp;
    }

    public void addFilter(String filter) {
        filters.add(filter);
    }

    public ArrayList<String> getFilters() {
        return filters;
    }

    public String getCollection() {
        return collection;
    }

    public void delete() {
        //
    }


}
