package com.example.demo.service;

import com.example.demo.model.Filter;
import org.springframework.data.domain.Sort;

public interface CacheService {
    Filter getFilter();
    Sort getSortBy();
    void setFilter(Filter filter);
    void setSortBy(String sortBy);
    void setType(String type);
    String getType();
    String getSort();
}
