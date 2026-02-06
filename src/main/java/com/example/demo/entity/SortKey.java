package com.example.demo.entity;

import org.springframework.data.domain.Sort;

import java.util.Arrays;

public class SortKey extends CacheDataBody{
    private String[] sortFields;
    private String sortArg;

    public SortKey(String[] sortFields, String sortArg) {
        this.sortFields = sortFields;
        this.sortArg = sortArg;
    }

    public String[] getSortFields() {
        return sortFields;
    }

    public String getSortArg() {
        return sortArg;
    }

    public Sort getSort(){
        if ("desc".equalsIgnoreCase(sortArg)) {
            return Sort.by(sortFields).descending();
        } else {
            return Sort.by(sortFields).ascending();
        }
    }

    @Override
    public String toString() {
        return "SortKey{" +
                "sortFields=" + Arrays.toString(sortFields) +
                ", sortArg='" + sortArg + '\'' +
                '}';
    }
}
