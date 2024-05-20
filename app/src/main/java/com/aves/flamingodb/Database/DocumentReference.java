package com.aves.flamingodb.Database;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.concurrent.Executor;

public class DocumentReference extends Query {

    DocumentReference(DatabaseStore databaseStore) {
        super(databaseStore);
    }

}
