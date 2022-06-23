package cn.afternode.api;

import com.google.gson.Gson;

import java.util.HashMap;

public class Json {
    public static HashMap parse(String json) {
        Gson gson = new Gson();
        HashMap map = new HashMap();
        map = gson.fromJson(json, map.getClass());
        return map;
    }
}
