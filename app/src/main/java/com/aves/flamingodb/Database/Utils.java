package com.aves.flamingodb.Database;

import android.os.StrictMode;
import android.util.Log;

import com.aves.flamingodb.App.FlamingoApp;
import com.aves.flamingodb.App.services.GlobalServices;
import com.google.android.gms.common.util.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import io.grpc.internal.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Utils {

    protected static GlobalServices globalServices = GlobalServices.getInstance();;
    protected OkHttpClient client = new OkHttpClient();
    protected Request connection;
    protected String DB_URL = "https://api-pouletafc.net/db/%s";
    protected String AUTH_URL = "https://api-pouletafc.net/auth/create-token";
    protected String UID = "";

    protected Utils(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void initializeApp(FlamingoApp flamingoApp){
        client = new OkHttpClient();
    }

    private Request.Builder connect(String url){
        if(globalServices.get("token") == null){
            try {
                UID = getAuthorization();
                globalServices.set("token", UID);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return new Request.Builder().addHeader("Authorization", String.format("Bearer %s", UID)).url(url);
    }

    private String getAuthorization() throws IOException{
        HashMap<String, Object> map = new HashMap<>();
        map.put("key", globalServices.getUniqueID());
        Document document = new Document(map);

        Request request = new Request.Builder().url(AUTH_URL).post(document.getData()).build();

        try (Response response = client.newCall(request).execute()) {
            JSONObject jsonObject = new JSONObject(response.body().string());
            return jsonObject.get("token").toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private Request post(Document document) throws IOException {
        checkNotNull(client, "Provided database connection must not be null.");
        connection = connect(DB_URL).post(document).build();
        return connection;
    }

    @Nonnull
    public JSONArray get() throws IOException {
        String collection = DatabaseStore.getInstance().getCollection();
        String filters = String.join("&", DatabaseStore.getInstance().getFilters());
        connection = connect(String.format(DB_URL, String.format("%s/%s", collection, filters))).get().build();
        try (Response response = client.newCall(connection).execute()) {
            JSONArray jsonArray = new JSONArray(response.body().string());
            return jsonArray;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Nonnull
    public void add(Document document){

    }

    @Nonnull
    public void delete(){

    }

    @Nonnull
    public JSONArray update(Document document){
        return new JSONArray();
    }

    public static <T extends Object> T checkNotNull(@Nonnull T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }
}
