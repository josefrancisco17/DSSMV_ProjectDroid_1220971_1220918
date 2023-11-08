package com.example.dssmv_projectdroid_1220971_1220918.helper;

import com.google.gson.Gson;

public class UtilsJson {
    public static <T> T fromJson(String json, Class<T> TypeClass) {
        Gson gson = new Gson();
        return gson.fromJson(json, TypeClass);
    }

    public static <T> String toJson(T object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }
}
