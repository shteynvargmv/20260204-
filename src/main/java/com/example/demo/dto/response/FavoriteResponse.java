package com.example.demo.dto.response;

import com.example.demo.dto.InstrumentDto;

import java.util.List;

public class FavoriteResponse {
    private List<String> favoriteUids;
    private String favoriteResult;

    public FavoriteResponse(List<String> favoriteUids, String favoriteResult) {
        this.favoriteUids = favoriteUids;
        this.favoriteResult = favoriteResult;
    }

    public FavoriteResponse(List<String> favoriteUids) {
        this.favoriteUids = favoriteUids;
    }

    public List<String> getFavoriteUids() {
        return favoriteUids;
    }

    public void setFavoriteUids(List<String> favoriteUids) {
        this.favoriteUids = favoriteUids;
    }

    public String getFavoriteResult() {
        return favoriteResult;
    }

    public void setFavoriteResult(String favoriteResult) {
        this.favoriteResult = favoriteResult;
    }
}
