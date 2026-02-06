package com.example.demo.utils;

import com.example.demo.entity.CacheKey;
import com.example.demo.entity.Param;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CacheUtils {
    public static CacheKey getCacheKey(String service, Param...params){
        Map<String, String> keyParams = new HashMap<>();
        for (Param param : params){
            param.Value = param.Value.toLowerCase();
            if (param.Value.contains(",")) {
                String[] list = Arrays.stream(param.Value.split(",")).sorted().toArray(String[]::new);
                keyParams.put(param.Name, Arrays.toString(list));
            } else {
                keyParams.put(param.Name, param.Value);
            }
        }
        return new CacheKey(service, keyParams);
    }

    public static CacheKey getCacheKey(String service, String param){
        Map<String, String> keyParams = new HashMap<>();
        keyParams.put(param,"");
        return new CacheKey(service, keyParams);
    }

    public static CacheKey getCacheKey(String service){
        Map<String, String> keyParams = new HashMap<>();
        return new CacheKey(service, keyParams);
    }
}
