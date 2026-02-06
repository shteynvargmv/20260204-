package com.example.demo.services.implementations;

import com.example.demo.controller.HomeController;
import com.example.demo.dto.InstrumentDto;
import com.example.demo.entity.*;
import com.example.demo.repository.repository.InstrumentRepository;
import com.example.demo.repository.repository.BondRepository;
import com.example.demo.services.DBService;
import com.example.demo.utils.CacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class InstrumentService implements DBService {
    @Autowired
    InstrumentRepository instrumentRepository;

    @Autowired
    BondRepository bondRepository;

    @Autowired
    BondRepository brandRepository;

    @Autowired
    private Cache cache;

    @Autowired
    @Qualifier("restTemplateLong")
    private RestTemplate restTemplate;

    @Autowired
    Environment env;

    @Override
    public Page<Instrument> findAll(int page, String sort) {
        Pageable pageable = PageRequest.of(page, HomeController.ON_PAGE, this.getSort(sort));
        return instrumentRepository.findAll(pageable);
    }
    @Override
    public Page<Instrument> findByBondIsNotEmpty(int page, String sort) {
        Pageable pageable = PageRequest.of(page, HomeController.ON_PAGE, this.getSort(sort));
        return instrumentRepository.findByBondIsNotNull(pageable);
    }
    @Override
    public Page<Instrument> findByShareIsNotEmpty(int page, String sort) {
        Pageable pageable = PageRequest.of(page, HomeController.ON_PAGE, this.getSort(sort));
        return instrumentRepository.findByShareIsNotNull(pageable);
    }
    @Override
    public Page<Instrument> findByCurrencyIsNotEmpty(int page, String sort) {
        Pageable pageable = PageRequest.of(page, HomeController.ON_PAGE, this.getSort(sort));
        return instrumentRepository.findByCurrencyIsNotNull(pageable);
    }
    @Override
    public void saveAll(List<Instrument> instruments){
        instrumentRepository.saveAll(instruments);
    }

    @Override
    public Instrument dtoToEntity(InstrumentDto dto) {
        Instrument instrument = new Instrument();
        instrument.setUid(dto.getUid());
        instrument.setAssetUid(dto.getAssetUid());
        instrument.setLot(dto.getLot());
        instrument.setSellAvailableFlag(dto.isSellAvailableFlag());
        instrument.setBuyAvailableFlag(dto.isBuyAvailableFlag());
        instrument.setWeekendFlag(dto.isWeekendFlag());
        instrument.setClassCode(dto.getClassCode());
        instrument.setTicker(dto.getTicker());
        instrument.setForQualInvestorFlag(dto.isForQualInvestorFlag());
        instrument.setPositionUid(dto.getPositionUid());
        instrument.setName(dto.getName());
        instrument.setExchange(dto.getExchange());
        instrument.setCountryOfRisk(dto.getCountryOfRisk());
        instrument.setCountryOfRiskName(dto.getCountryOfRiskName());
        instrument.setIsin(dto.getIsin());
        instrument.setNominalCurrency(dto.getNominal().getCurrency());
        instrument.setNominalUnits(dto.getNominal().getUnits());
        instrument.setNominalNano(dto.getNominal().getNano());
        Brand brand = new Brand();
        brand.setLogoName(dto.getBrand().getLogoName());
        brand.setLogoBaseColor(dto.getBrand().getLogoBaseColor());
        brand.setTextColor(dto.getBrand().getTextColor());
        instrument.setBrand(brand);
        instrument.setUpdateDate(Date.from(LocalDateTime.now()
                .atZone(ZoneId.systemDefault())
                .toInstant()));

        switch (dto.getInstrumentType()) {
            case "share" -> {
                Share share = new Share();
                share.setUid(dto.getUid());
                share.setDivYieldFlag(dto.isDivYieldFlag());
                share.setIpoDate(dto.getIpoDate());
                share.setSector(dto.getSector());
                share.setLiquidityFlag(dto.isLiquidityFlag());
                instrument.setShare(share);
            }
            case "bond" -> {
                Bond bond = new Bond();
                bond.setUid(dto.getUid());
                bond.setCallDate(dto.getCallDate());
                bond.setMaturityDate(dto.getMaturityDate());
                bond.setAciСurrency(dto.getAciValue().getCurrency());
                bond.setAciUnits(dto.getAciValue().getUnits());
                bond.setAciNano(dto.getAciValue().getNano());
                bond.setCouponQuantityPerYear(dto.getCouponQuantityPerYear());
                bond.setFloatingCouponFlag(dto.isFloatingCouponFlag());
                bond.setSubordinatedFlag(dto.isSubordinatedFlag());
                bond.setAmortizationFlag(dto.isAmortizationFlag());
                bond.setPerpetualFlag(dto.isPerpetualFlag());
                instrument.setBond(bond);
            }
            case "currency" -> {
                Currency currency = new Currency();
                currency.setUid(dto.getUid());
                currency.setIsoCurrencyName(dto.getIsoCurrencyName());
                instrument.setCurrency(currency);
            }
            default -> {
            }
        }
        return instrument;
    }

    private Sort getSort(String sortBy){
        String[] sort = sortBy.split("_");
        if (sortBy.equals("none")) {
            cache.del(CacheUtils.getCacheKey("/catalog", "sort"));

        } else if (sort.length > 1) {
            String[] sortFields = Arrays.copyOf(sort, sort.length - 1);
            String sortArg = sort[sort.length - 1];
            SortKey sortKey = new SortKey(sortFields, sortArg);

            cache.put(CacheUtils.getCacheKey("/catalog", "sort"), sortKey);
            return sortKey.getSort();

        } else {
                CacheKey cacheKey = CacheUtils.getCacheKey("/catalog", new Param("sort", ""));
                CacheDataBody[] cacheDataBody = cache.get(cacheKey);
                if (cacheDataBody != null && cacheDataBody.length > 0) {
                    SortKey cached = (SortKey) cacheDataBody[0];
                    return cached.getSort();
                }
        }
        return Sort.by("uid").ascending();
    }

    public String getSortBy(){
            CacheKey cacheKey = CacheUtils.getCacheKey("/catalog", new Param("sort", ""));
            CacheDataBody[] cacheDataBody = cache.get(cacheKey);
            if (cacheDataBody != null && cacheDataBody.length > 0) {
                SortKey cached = (SortKey) cacheDataBody[0];
                return Stream.concat(Arrays.stream(cached.getSortFields()),Stream.of(cached.getSortArg()))
                        .collect(Collectors.joining("_"));
            }
            return "none";

    }
}
