package com.aves.flamingodb.App.models;

import android.util.Log;

import androidx.annotation.NonNull;

import com.aves.flamingodb.App.FlamingoApp;
import com.aves.flamingodb.Database.DatabaseStore;
import com.aves.flamingodb.Database.Query;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.core.ViewSnapshot;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.EventListener;

import okhttp3.Request;
import okhttp3.Response;

public class QuerySnapshot{

    private final Query originalQuery;
    private final DatabaseStore databaseStore;
    private final FlamingoApp flamingoApp;
    private OnCompleteListener onCompleteListener;

    public interface OnCompleteListener{
        void onComplete(JSONArray task);
    }

    public void addOnCompleteListener(OnCompleteListener eventListener){
        this.onCompleteListener = eventListener;
    }

    protected QuerySnapshot(Query originalQuery, DatabaseStore databaseStore){
        this.originalQuery = originalQuery;
        this.databaseStore = databaseStore;
        this.flamingoApp  = databaseStore.getFlamingoApp();

        try {
            onCompleteListener.onComplete(flamingoApp.get());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
