package com.aves.flamingodb.App.models;

import android.util.Log;

import com.aves.flamingodb.App.FlamingoApp;
import com.aves.flamingodb.App.core.EventsRegister;
import com.aves.flamingodb.Database.DatabaseStore;
import com.aves.flamingodb.Database.Document;
import com.aves.flamingodb.Database.Query;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuerySnapshot{

    protected final Query originalQuery;
    protected final DatabaseStore databaseStore;
    protected final FlamingoApp flamingoApp;
    protected final String flagQuerry;

    protected OnCompleteListener onCompleteListener;
    protected EventsRegister eventsRegister = EventsRegister.getInstance();
    protected List<Snapshot> snapshots;
    protected Gson gson = new Gson();

    public interface OnCompleteListener{
        void onComplete(List<Snapshot> task);
    }

    protected QuerySnapshot(Query originalQuery, DatabaseStore databaseStore, String flagQuerry){
        this.originalQuery = originalQuery;
        this.databaseStore = databaseStore;
        this.flamingoApp  = databaseStore.getFlamingoApp();
        this.flagQuerry = flagQuerry;
    }

    public void addOnCompleteListener(OnCompleteListener eventListener){
        if(null != databaseStore.getDocumentId()){
            if(flagQuerry.equals("delete")){
                flamingoApp.delete();
            }
            if(flagQuerry.equals("update")){
                flamingoApp.update(null);
            }
            if(flagQuerry.equals("get")){
                try {
                    JSONArray result = flamingoApp.get();
                    snapshots = new ArrayList<>();
                    eventsRegister.registerProcessResult(eventListener);
                    this.onCompleteListener = eventListener;
                    for(int i = 0; i < result.length(); i++){
                        snapshots.add(gson.fromJson(result.get(i).toString(), Snapshot.class));
                    }
                    onCompleteListener.onComplete(snapshots);
                } catch (IOException | JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            return;
        }
        try {
            JSONArray result = flamingoApp.get();
            snapshots = new ArrayList<>();
            eventsRegister.registerProcessResult(eventListener);
            this.onCompleteListener = eventListener;
            for(int i = 0; i < result.length(); i++){
                snapshots.add(gson.fromJson(result.get(i).toString(), Snapshot.class));
            }
            onCompleteListener.onComplete(snapshots);
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
