package com.example.liucx.lcweather;

import com.google.gson.Gson;

/**
 * Created by liucx on 2017/4/8.
 */

public class GsonUtil {
    public static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
        Gson gson = new Gson();
        T ret = gson.fromJson(jsonData,type);
        return ret;
    }

    public static WeatherInfo parseJsonWithGsonB(String jsonData) {
        Gson gson = new Gson();
        WeatherInfo ret = gson.fromJson(jsonData, WeatherInfo.class);
        return ret;
    }
}
