package com.aves.flamingodb.App.models;

public class Snapshot {

    private Object id;
    private Object collection;
    private Object data;

    public Snapshot(){}

    public Snapshot(Object id, Object collection, Object data){
        this.id = id;
        this.collection = collection;
        this.data = data;
    }

    public Object getId() {
        return id;
    }

    public Object getCollection() {
        return collection;
    }

    public Object getData() {
        return data;
    }

    public Object get(String field){
        return null;
    }

}
