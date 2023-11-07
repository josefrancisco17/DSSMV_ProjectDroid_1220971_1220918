package com.example.dssmv_projectdroid_1220971_1220918.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class Utils {
    public static <T> T fromJson(String json, T TypeClass) {
        Gson gson = new Gson();
        return gson.fromJson(json, (Type) TypeClass);
    }

    public static <T> String toJson(T object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }
}
