package com.example.demo.service.implementations;

import com.example.demo.model.*;
import com.example.demo.service.CacheService;
import com.example.demo.utils.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SimpleCacheService implements CacheService {
    @Autowired
    private Cache cache;

    @Override
    public Filter getFilter() {
        CacheDataBody[] cacheDataBody = cache.get(CacheUtils.getCacheKey("/catalog", "filter"));
        if (cacheDataBody != null && cacheDataBody.length > 0) {
            Filter filter = (Filter) cacheDataBody[0];
            System.out.println("get share" + filter.getSelectedShareParameters());
            System.out.println("get bond" + filter.getSelectedBondParameters());
            System.out.println("get all" + filter.getSelectedBondParameters());
            return (Filter) cacheDataBody[0];
        }
        System.out.println("get share new");
        System.out.println("get bond new");
        System.out.println("get all new");
        return new Filter();
    }

    @Override
    public Sort getSortBy() {
        CacheDataBody[] cacheDataBody = cache.get(CacheUtils.getCacheKey("/catalog","sort"));
        if (cacheDataBody != null && cacheDataBody.length > 0) {
            SortKey cached = (SortKey) cacheDataBody[0];
            return cached.getSort();
        }
        return Sort.by("uid").ascending();
    }

    @Override
    public void setFilter(Filter filter) {
        Filter cached = getFilter();
        if (cached != null && filter.getSelectedBondParameters() == null){
            filter.setSelectedBondParameters(cached.getSelectedBondParameters());
        } else if (cached != null && filter.getSelectedShareParameters() == null) {
            filter.setSelectedShareParameters(cached.getSelectedShareParameters());
        }
        System.out.println("set share" + filter.getSelectedShareParameters());
        System.out.println("set bond" + filter.getSelectedBondParameters());
        System.out.println("set all" + filter.getSelectedAllParameters());
        cache.put(CacheUtils.getCacheKey("/catalog", "filter"), filter);
    }

    @Override
    public void setSortBy(String sortBy) {
        String[] sort = sortBy.split("_");
        if (sortBy.equals("none")) {
            cache.del(CacheUtils.getCacheKey("/catalog", "sort"));

        } else if (sort.length > 1) {
            String[] sortFields = Arrays.copyOf(sort, sort.length - 1);
            String sortArg = sort[sort.length - 1];
            SortKey sortKey = new SortKey(sortFields, sortArg);
            cache.put(CacheUtils.getCacheKey("/catalog", "sort"), sortKey);
        }
    }

    @Override
    public void setType(String type) {
        cache.put(CacheUtils.getCacheKey("/catalog", "type"), new Type(type));
    }

    @Override
    public String getType() {
        CacheDataBody[] cacheDataBody = cache.get(CacheUtils.getCacheKey("/catalog", "type"));
        if (cacheDataBody != null && cacheDataBody.length > 0) {
            Type cached = (Type) cacheDataBody[0];
            return cached.getType();
        }
        return "all";
    }

    @Override
    public String getSort() {
        CacheDataBody[] cacheDataBody = cache.get(CacheUtils.getCacheKey("/catalog","sort"));
        if (cacheDataBody != null && cacheDataBody.length > 0) {
            SortKey cached = (SortKey) cacheDataBody[0];
            return Stream.concat(
                            Arrays.stream(cached.getSortFields()),
                            Stream.of(cached.getSortArg())
                    )
                    .collect(Collectors.joining("_"));
        }
        return "none";
    }
}
