package com.example.demo.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
@Scope("request")
public class CacheStat {

    private Integer size;
    private long timeToLive;
    private ArrayList<CacheEntry> cacheEntries;
    private int used_total;

    public CacheStat(Cache cache) {
        this.size = cache.getCache().size();
        this.cacheEntries = new ArrayList<CacheEntry>();
        this.timeToLive = cache.getTimeToLive();
        for (Map.Entry<CacheKey, CacheData> entry : cache.getCache().entrySet()) {
            CacheKey cacheKey = entry.getKey();
            CacheData cacheData = entry.getValue();
            long time = cacheData.getTime();
            long timeToExp = this.timeToLive - (System.currentTimeMillis() - time);
            CacheEntry cacheEntry = new CacheEntry(cacheKey, cacheData, timeToExp);
            this.cacheEntries.add(cacheEntry);
            used_total += cacheData.getUsed();
        }
    }

    public ArrayList<CacheEntry> getCacheEntries() {
        return cacheEntries;
    }

    public Integer getSize() {
        return size;
    }

    public long getTimeToLive() {
        return timeToLive;
    }

    public int getUsed_total() {
        return used_total;
    }
}
