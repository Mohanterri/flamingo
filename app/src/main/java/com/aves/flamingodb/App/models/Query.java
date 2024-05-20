package com.aves.flamingodb.App.models;


import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aves.flamingodb.App.core.Filter;
import com.aves.flamingodb.Database.DatabaseStore;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.concurrent.Executor;

public class Query extends Task<com.aves.flamingodb.Database.Query> {


    public Query() {
        super();
    }

    public com.aves.flamingodb.Database.Query addFilter(com.aves.flamingodb.Database.Query query, String filter) {
        DatabaseStore.getInstance().addFilter(filter);
        return query;
    }

    @NonNull
    @Override
    public Task<com.aves.flamingodb.Database.Query> addOnCanceledListener(@NonNull OnCanceledListener onCanceledListener) {
        return super.addOnCanceledListener(onCanceledListener);
    }

    @NonNull
    @Override
    public Task<com.aves.flamingodb.Database.Query> addOnCompleteListener(@NonNull OnCompleteListener<com.aves.flamingodb.Database.Query> onCompleteListener) {
        return super.addOnCompleteListener(onCompleteListener);
    }

    @NonNull
    @Override
    public Task<com.aves.flamingodb.Database.Query> addOnFailureListener(@NonNull OnFailureListener onFailureListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<com.aves.flamingodb.Database.Query> addOnFailureListener(@NonNull Activity activity, @NonNull OnFailureListener onFailureListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<com.aves.flamingodb.Database.Query> addOnFailureListener(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<com.aves.flamingodb.Database.Query> addOnSuccessListener(@NonNull OnSuccessListener<? super com.aves.flamingodb.Database.Query> onSuccessListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<com.aves.flamingodb.Database.Query> addOnSuccessListener(@NonNull Activity activity, @NonNull OnSuccessListener<? super com.aves.flamingodb.Database.Query> onSuccessListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<com.aves.flamingodb.Database.Query> addOnSuccessListener(@NonNull Executor executor, @NonNull OnSuccessListener<? super com.aves.flamingodb.Database.Query> onSuccessListener) {
        return null;
    }

    @Nullable
    @Override
    public Exception getException() {
        return null;
    }

    @Override
    public com.aves.flamingodb.Database.Query getResult() {
        return null;
    }

    @Override
    public <X extends Throwable> com.aves.flamingodb.Database.Query getResult(@NonNull Class<X> aClass) throws X {
        return null;
    }


    @Override
    public boolean isCanceled() {
        return false;
    }

    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public boolean isSuccessful() {
        return false;
    }

}
