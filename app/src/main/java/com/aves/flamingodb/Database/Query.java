package com.aves.flamingodb.Database;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aves.flamingodb.App.core.Filter;
import com.aves.flamingodb.App.models.QuerySnapshot;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Source;
import com.google.firebase.firestore.core.ViewSnapshot;
import com.google.firebase.firestore.util.Executors;

import java.util.HashMap;
import java.util.List;

public class Query {

    final DatabaseStore databaseStore;
    final com.aves.flamingodb.App.models.Query query;

    public enum Direction {
        ASCENDING,
        DESCENDING
    }

    Query(DatabaseStore databaseStore) {
        this.databaseStore = databaseStore;
        this.query = new com.aves.flamingodb.App.models.Query();
    }

    public com.aves.flamingodb.App.models.QuerySnapshot get() {
        return get(this);
    }

    @NonNull
    public com.aves.flamingodb.App.models.QuerySnapshot get(@NonNull Query source) {
        return new Tasks(source, databaseStore);
    }

    public com.aves.flamingodb.App.models.Query add(HashMap<String, Object> object) {
        JsonUtils.mapToJson(object);
        return query;
    }

    @NonNull
    public DatabaseStore getDatabaseStore() {
        return databaseStore;
    }

    @NonNull
    public Query whereEqualTo(@NonNull String field, @Nullable Object value) {
        return where(Filter.equalTo(field, value));
    }

    @NonNull
    public Query whereNotEqualTo(@NonNull String field, @Nullable Object value) {
        return where(Filter.notEqualTo(field, value));
    }

    @NonNull
    public Query whereLessThan(@NonNull String field, @NonNull Object value) {
        return where(Filter.lessThan(field, value));
    }

    @NonNull
    public Query whereLessThanOrEqualTo(@NonNull String field, @NonNull Object value) {
        return where(Filter.lessThanOrEqualTo(field, value));
    }

    @NonNull
    public Query whereGreaterThan(@NonNull String field, @NonNull Object value) {
        return where(Filter.greaterThan(field, value));
    }

    @NonNull
    public Query whereGreaterThanOrEqualTo(@NonNull String field, @NonNull Object value) {
        return where(Filter.greaterThanOrEqualTo(field, value));
    }

    @NonNull
    public Query whereArrayContains(@NonNull String field, @NonNull Object value) {
        return where(Filter.arrayContains(field, value));
    }

    @NonNull
    public Query whereArrayContainsAny(
            @NonNull String field, @NonNull List<? extends Object> values) {
        return where(Filter.arrayContainsAny(field, values));
    }

    @NonNull
    public Query whereIn(@NonNull String field, @NonNull List<? extends Object> values) {
        return where(Filter.inArray(field, values));
    }

    @NonNull
    public Query whereNotIn(@NonNull String field, @NonNull List<? extends Object> values) {
        return where(Filter.notInArray(field, values));
    }

    @NonNull
    public Query where(@NonNull String filter) {
        return query.addFilter(this, filter);
    }


}
