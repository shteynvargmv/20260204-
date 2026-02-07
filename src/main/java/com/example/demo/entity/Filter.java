package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

public class Filter extends CacheDataBody{
    private List<String> selectedSectors;
    private List<String> selectedBondParameters;
    private List<String> selectedShareParameters;
    private List<String> selectedAllParameters;

    public void setSelectedSectors(List<String> selectedSectors) {
        this.selectedSectors = selectedSectors;
    }

    public void setSelectedBondParameters(List<String> selectedBondParameters) {
        this.selectedBondParameters = selectedBondParameters;
    }

    public void setSelectedShareParameters(List<String> selectedShareParameters) {
        this.selectedShareParameters = selectedShareParameters;
    }

    public Filter() {
        this.selectedSectors = new ArrayList<>();
        this.selectedBondParameters = new ArrayList<>();
        this.selectedShareParameters = new ArrayList<>();
        this.selectedAllParameters = new ArrayList<>();
    }

    public List<String> getSelectedAllParameters() {
        return selectedAllParameters;
    }

    public void setSelectedAllParameters(List<String> selectedAllParameters) {
        this.selectedAllParameters = selectedAllParameters;
    }

    public List<String> getSelectedSectors() {
        return selectedSectors;
    }

    public List<String> getSelectedShareParameters() {
        return selectedShareParameters;
    }

    public List<String> getSelectedBondParameters() {
        return selectedBondParameters;
    }
}
