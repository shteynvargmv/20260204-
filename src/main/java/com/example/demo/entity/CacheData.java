package com.example.demo.entity;

public class CacheData {
    CacheDataBody[] data;
    long time;
    int used;

    public CacheData(CacheDataBody[] data) {
        this.data = data;
        this.time = System.currentTimeMillis();
        this.used = 0;
    }

    boolean isExpired(long timeToLive) {
        return (System.currentTimeMillis() - this.time) > timeToLive;
    }

    public void addUsage(){
        this.used += 1;
    }

    public int getUsed() {
        return used;
    }

    public long getTime() {
        return time;
    }

    public CacheDataBody[] getData() {
        return data;
    }
}

