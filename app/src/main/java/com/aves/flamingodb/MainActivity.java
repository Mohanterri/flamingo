package com.aves.flamingodb;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aves.flamingodb.App.models.QuerySnapshot;
import com.aves.flamingodb.Database.DatabaseStore;
import com.aves.flamingodb.Database.Query;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        DatabaseStore.getInstance().collection("admins").whereEqualTo("name", "Mohan")
        .whereEqualTo("lastname", "Terri").get()
        .addOnCompleteListener(new QuerySnapshot.OnCompleteListener() {
            @Override
            public void onComplete(JSONArray task) {
                Toast.makeText(MainActivity.this, ""+task.length(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}