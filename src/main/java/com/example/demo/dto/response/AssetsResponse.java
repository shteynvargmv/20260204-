package com.example.demo.dto.response;

import com.example.demo.dto.AssetDto;

import java.util.List;

public class AssetsResponse {

    private List<AssetDto> assets;

    public List<AssetDto> getAssets() {
        return assets;
    }

    public void setAssets(List<AssetDto> assets) {
        this.assets = assets;
    }
}
