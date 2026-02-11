package com.example.demo.dto;

import com.example.demo.model.CacheDataBody;
import com.example.demo.model.Filter;

public class FilterRequest extends CacheDataBody {
    private Filter filter;

    public Filter getFilter() {
        return filter;
    }
}
