package com.example.vajronew.service;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class IntListTypeConverters {
    @TypeConverter
    public static List<Integer> stringToIds(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Integer>>() {}.getType();
        List<Integer> measurements = gson.fromJson(json, type);
        return measurements;
    }

    @TypeConverter
    public static String idsToString(List<Integer> list) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Integer>>() {}.getType();
        String json = gson.toJson(list, type);
        return json;
    }
}
