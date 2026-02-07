package com.example.demo.services;

import com.example.demo.entity.Filter;
import com.example.demo.entity.Type;
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
