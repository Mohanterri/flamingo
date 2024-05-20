package com.aves.flamingodb.Database;

import static com.aves.flamingodb.Database.JsonUtils.mapToJson;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

public class Document extends RequestBody{

    private RequestBody body;
    private static final MediaType JSON = MediaType.get("application/json");

    public Document(HashMap<String, Object> data){
        body = RequestBody.create(mapToJson(data).toString(), JSON);
        create(mapToJson(data).toString(), JSON);
    }

    public RequestBody getData(){
        return body;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return null;
    }

    @Override
    public void writeTo(@NonNull BufferedSink bufferedSink) throws IOException {

    }
}
