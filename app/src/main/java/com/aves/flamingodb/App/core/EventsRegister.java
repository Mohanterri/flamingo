package com.aves.flamingodb.App.core;

import android.util.Log;

import com.aves.flamingodb.App.models.QuerySnapshot;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class EventsRegister {

    private static EventsRegister instance;
    private List<QuerySnapshot.OnCompleteListener> process;

    public static EventsRegister getInstance() {
        instance = (instance != null) ? instance : new EventsRegister();
        return instance;
    }

    public void registerProcessResult(QuerySnapshot.OnCompleteListener onCompleteListener) {
        process = (process != null) ? process : new ArrayList<>();
        process.add(onCompleteListener);
    }

    public void execProcess(QuerySnapshot.OnCompleteListener onCompleteListener, JSONArray jsonArray) {
        List<QuerySnapshot.OnCompleteListener> result = new ArrayList<>(process);
        process.clear();
    }
}
