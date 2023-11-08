package com.example.dssmv_projectdroid_1220971_1220918.handler;

import com.google.gson.Gson;

public class JsonHandler {
    public static <T> T fromJson(String json, Class<T> TypeClass) {
        // Deserialize the JSON into dto object
        Gson gson = new Gson();
        return gson.fromJson(json, TypeClass);
    }

    public static <T> String toJson(T object) {
        // Serialize the dto object into JSON
        Gson gson = new Gson();
        return gson.toJson(object);
    }
}
