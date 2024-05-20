package com.aves.flamingodb.App;

import androidx.annotation.NonNull;

import com.aves.flamingodb.Database.Document;
import com.aves.flamingodb.Database.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Request;

public class FlamingoApp extends Utils {

    private static final String LOG_TAG = "FlamingoApp";
    private static FlamingoApp instance;

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

    @Override
    public void add(Document document) {
        super.add(document);
    }
}
