package com.example.demo.dto;

import com.example.demo.entity.CacheDataBody;
import com.example.demo.entity.Filter;

public class FilterRequest extends CacheDataBody {
    private Filter filter;

    public Filter getFilter() {
        return filter;
    }
}
