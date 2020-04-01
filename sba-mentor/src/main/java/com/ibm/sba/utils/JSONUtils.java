package com.ibm.sba.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.http.ResponseEntity;

public class JSONUtils {
    public static JsonObject getResult(ResponseEntity<Object> result) {
        Gson gson = new Gson();
        String jsonResultStr = gson.toJson(result.getBody());
        JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject) parser.parse(jsonResultStr);
        return object;
    }
}
