package com.example.demo.service.implementations;


import com.example.demo.dto.response.*;
import com.example.demo.model.Cache;
import com.example.demo.model.CacheStat;
import com.example.demo.service.BrokerApiService;
import com.example.demo.utils.RestUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TBankApiService implements BrokerApiService {
    @Autowired
    private Cache cache;
    @Autowired
    private ObjectFactory<CacheStat> cacheStatFactory;
    @Autowired
    private RestUtils restUtils;


    @Override
    public ResponseEntity<InstrumentsResponse> getAllBonds() {
        return restUtils.getAllBonds();
    }
    @Override
    public ResponseEntity<InstrumentsResponse> getAllShares() {
        return restUtils.getAllShares();
    }
    @Override
    public ResponseEntity<InstrumentsResponse> getAllCurrencies() {
        return restUtils.getAllCurrencies();
    }
    @Override
    public ResponseEntity<LastPricesResponse> getLastPrices(List<String> uids) {
        return restUtils.getLastPrices(uids);
    }
    @Override
    public ResponseEntity<LastPricesResponse> getLastPrices(String uid) {
        return this.getLastPrices(Arrays.asList(uid));
    }
    @Override
    public ResponseEntity<InstrumentResponse> getShareByUid(String uid) {
        return restUtils.getShareByUid(uid);
    }
    @Override
    public ResponseEntity<InstrumentResponse> getBondByUid(String uid) {
        return restUtils.getBondByUid(uid);
    }
    @Override
    public ResponseEntity<InstrumentResponse> getCurrencyByUid(String uid) {
        return restUtils.getCurrencyByUid(uid);
    }

    @Override
    public ResponseEntity<AssetResponse> getAssetById(String id) {
        return restUtils.getAssetByUid(id);
    }

    @Override
    public ResponseEntity<AssetsResponse> getAllAssetsBonds() {
        return restUtils.getAllAssetsBonds();
    }

    @Override
    public ResponseEntity<AssetsResponse> getAllAssetsShares() {
        return restUtils.getAllAssetsShares();
    }

    @Override
    public ResponseEntity<AssetsResponse> getAllAssetsCurrencies() {
        return restUtils.getAllAssetsCurrencies();
    }
//    public ResponseEntity<?> price(String ids) {
//        List<String> idsList = this.getIdsExist(ids);
//
//        if (idsList.isEmpty()) {
//            HashMap<String, String> result = new HashMap<>();
//            result.put("result", "Ошибка: Указанные криптовалюты не найдены");
//            return ResponseEntity.badRequest().body(result);
//        }
//
//        ids = String.join(",", idsList);
//        CacheKey cacheKey = CacheUtils.getCacheKey("/price", new Param("ids", ids));
//
//        CacheDataBody[] cacheDataBody = cache.get(cacheKey);
//        if (cacheDataBody != null) {
//            System.out.println("Запрос из кэша : " + cacheKey);
//            CoinPriceApiResponse cached = (CoinPriceApiResponse) cacheDataBody[0];
//            return ResponseEntity.ok(cached);
//        }
//
//        System.out.println("Запрос из API : " + cacheKey);
//        ResponseEntity<CoinPriceApiResponse> response = restUtils.price(idsList);
//
//        if (response.getStatusCode().is2xxSuccessful()) {
//            cache.put(cacheKey, response.getBody());
//        }
//
//        return response;
//
//    }

//    public ResponseEntity<?> top(Integer limit) {
//        if (limit <= 0) {
//            HashMap<String, String> result = new HashMap<>();
//            result.put("result", "Ошибка ввода: limit <= 0");
//            return ResponseEntity.badRequest().body(result);
//        }
//
//        CacheKey cacheKey = CacheUtils.getCacheKey("/top", new Param("limit", Integer.toString(limit)));
//        TopCoinApiResponse[] cached = (TopCoinApiResponse[]) cache.get(cacheKey);
//        if (cached != null) {
//            System.out.println("Запрос из кэша : " + cacheKey);
//            return ResponseEntity.ok(cached);
//        }
//
//        System.out.println("Запрос из API : " + cacheKey);
//        ResponseEntity<TopCoinApiResponse[]> response = restUtils.top(limit);
//        if (response.getStatusCode().is2xxSuccessful()) {
//            TopCoinApiResponse[] result = response.getBody();
//            cache.put(cacheKey, result);
//            return ResponseEntity.ok(result);
//        }
//
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body(new HashMap<String, String>() {{
//                    put("result", "Not Found");
//                }});
//    }

//    public ResponseEntity<?> search(String query) {
//
//        CacheKey cacheKey = CacheUtils.getCacheKey("/search", new Param("query", query));
//        CacheDataBody[] cacheDataBody = cache.get(cacheKey);
//        if (cacheDataBody != null) {
//            System.out.println("Запрос из кэша : " + cacheKey);
//            CoinApiResponse cached = (CoinApiResponse) cacheDataBody[0];
//            return ResponseEntity.ok(cached);
//        }
//
//        System.out.println("Запрос из API : " + cacheKey);
//        ResponseEntity<CoinApiResponse> response = restUtils.search(query);
//        if (response.getStatusCode().is2xxSuccessful()) {
//            cache.put(cacheKey, response.getBody());
//            return response;
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body(new HashMap<String, String>() {{
//                    put("result", "Not Found");
//                }});
//    }
//
//    public ResponseEntity<?> compare(String ids) {
//        List<String> idsList = this.getIdsExist(ids);
//
//        if (idsList.size() != 2) {
//            HashMap<String, String> result = new HashMap<>();
//            result.put("result", "Ошибка: Сравнивать можно только 2 криптовалюты (или указанные криптовалюты не найдены)");
//            return ResponseEntity.badRequest().body(result);
//        }
//
//        ids = String.join(",", idsList);
//        CacheKey cacheKey = CacheUtils.getCacheKey("/compare", new Param("ids", ids));
//
//        CacheDataBody[] cacheDataBody = cache.get(cacheKey);
//        if (cacheDataBody != null) {
//            System.out.println("Запрос из кэша : " + cacheKey);
//            CryptoCompareResponse cached = (CryptoCompareResponse) cacheDataBody[0];
//            return ResponseEntity.ok(cached);
//        }
//
//        System.out.println("Запрос из API : " + cacheKey);
//        ResponseEntity<CryptoCompareResponse> response = restUtils.compare(idsList);
//        if (response.getStatusCode().is2xxSuccessful()) {
//            cache.put(cacheKey, response.getBody());
//        }
//        return response;
//    }
//
//    public ResponseEntity<?> cacheStats() {
//        CacheStat cacheStat = cacheStatFactory.getObject();
//        if (!cacheStat.getCacheEntries().isEmpty()) {
//            return ResponseEntity.ok(cacheStat);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(new HashMap<String,String>(){{put("result","Кэш пуст");}});
//        }
//    }
//
//    public ResponseEntity<HashMap<String, String>> cacheClear() {
//        cache.clear();
//        HashMap<String,String> result = new HashMap<>();
//        if (cache.isEmpty()) {
//            result.put("result", "Кэш успешно очищен");
//        } else {
//            result.put("result", "Очистить кэш не удалось");
//        }
//        return ResponseEntity.ok(result) ;
//    }

}
