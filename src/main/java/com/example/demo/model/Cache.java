package com.example.demo.model;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Cache {
    private Map<CacheKey, CacheData> cache = new ConcurrentHashMap<>();
    private long timeToLive = 60 * 1000 * 30;

    public void put(CacheKey cacheKey, CacheDataBody[] data) {

        cache.put(cacheKey, new CacheData(data));
    }
    public void put(CacheKey cacheKey, CacheDataBody data) {

        this.put(cacheKey,new CacheDataBody[]{data});
    }

    public CacheDataBody[] get(CacheKey cacheKey) {
        CacheData cacheData = cache.get(cacheKey);

        if (cacheData == null) {
            return null;
        }

        if (cacheData.isExpired(this.timeToLive)) {
            cache.remove(cacheKey);
            return null;
        }

        cacheData.addUsage();
        return cacheData.data;
    }

    public Boolean isEmpty(){
        if (cache.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public void clear() {
        cache.clear();
    }

    public int size() {
        return cache.size();
    }

    public long getTimeToLive() {
        return timeToLive;
    }

    public Map<CacheKey, CacheData> getCache() {
        return cache;
    }

    public void del(CacheKey cacheKey){
        cache.remove(cacheKey);
    }
}

