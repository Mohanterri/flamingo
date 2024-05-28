package com.aves.flamingodb.App;

import androidx.annotation.NonNull;

import com.aves.flamingodb.App.core.EventsRegister;
import com.aves.flamingodb.Database.Document;
import com.aves.flamingodb.Database.Utils;

import org.json.JSONArray;

import java.io.IOException;

public class FlamingoApp extends Utils {

    private static final String LOG_TAG = "FlamingoApp";
    private static FlamingoApp instance;
    private EventsRegister eventsRegister = EventsRegister.getInstance();

    FlamingoApp(){
        super();
    }

    public static FlamingoApp getInstance() {
        instance = (instance != null) ? instance : new FlamingoApp();
        return instance;
    }

    @Override
    public void initializeApp(FlamingoApp flamingoApp) {
        super.initializeApp(flamingoApp);
    }

    @NonNull
    @Override
    public JSONArray get() throws IOException {
        return super.get();
    }

    @NonNull
    @Override
    public void add(Document document) {
        super.add(document);
    }

    @NonNull
    @Override
    public void delete() {
        super.delete();
    }

    @NonNull
    @Override
    public JSONArray update(Document document) {
        return super.update(document);
    }


}
