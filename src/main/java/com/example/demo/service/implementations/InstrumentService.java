package com.example.demo.service.implementations;

import com.example.demo.controller.HomeController;
import com.example.demo.dto.AssetDto;
import com.example.demo.dto.InstrumentDto;
import com.example.demo.dto.LastPriceDto;
import com.example.demo.dto.QuotationDto;
import com.example.demo.entity.*;
import com.example.demo.entity.CurrencySymbol;
import com.example.demo.model.Filter;
import com.example.demo.repository.InstrumentRepository;
import com.example.demo.repository.BondRepository;
import com.example.demo.repository.ShareRepository;
import com.example.demo.service.entservice.CurrencyService;
import com.example.demo.service.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class InstrumentService implements DBService {
    @Autowired
    InstrumentRepository instrumentRepository;
    @Autowired
    BondRepository bondRepository;
    @Autowired
    ShareRepository shareRepository;
    @Autowired
    Environment env;
    @Autowired
    CurrencyService currencyService;

    @Override
    public Page<Instrument> findAll(int page, Sort sort) {
        Pageable pageable = PageRequest.of(page, HomeController.ON_PAGE, sort);
        return instrumentRepository.findByBondIsNotNullOrShareIsNotNullOrCurrencyIsNotNull(pageable);
    }

    @Override
    public Page<Instrument> findByBondIsNotEmpty(int page, Sort sort) {
        Pageable pageable = PageRequest.of(page, HomeController.ON_PAGE, sort);
        return instrumentRepository.findByBondIsNotNull(pageable);
    }

    @Override
    public Page<Instrument> findByShareIsNotEmpty(int page, Sort sort) {
        Pageable pageable = PageRequest.of(page, HomeController.ON_PAGE, sort);
        return instrumentRepository.findByShareIsNotNull(pageable);
    }

    @Override
    public Page<Instrument> findByCurrencyIsNotEmpty(int page, Sort sort) {
        Pageable pageable = PageRequest.of(page, HomeController.ON_PAGE, sort);
        return instrumentRepository.findByCurrencyIsNotNull(pageable);
    }

    @Override
    public void saveAll(List<Instrument> instruments) {
        instrumentRepository.saveAll(instruments);
    }

    @Override
    public Instrument dtoToEntity(InstrumentDto dto, List<LastPriceDto> prices) {
        return dtoToEntity(dto, prices, new AssetDto());
    }

    @Override
    public Instrument dtoToEntity(InstrumentDto dto, List<LastPriceDto> prices, AssetDto asset) {
        Instrument instrument = new Instrument();
        instrument.setUid(dto.getUid());
        instrument.setAssetUid(dto.getAssetUid());
        instrument.setName(dto.getName());
        instrument.setNameLong(asset.getName());
        instrument.setDescription(asset.getDescription());
        instrument.setBrCodeName(asset.getBrCodeName());
        instrument.setLot(dto.getLot());
        instrument.setSellAvailableFlag(dto.isSellAvailableFlag());
        instrument.setBuyAvailableFlag(dto.isBuyAvailableFlag());
        instrument.setWeekendFlag(dto.isWeekendFlag());
        instrument.setClassCode(dto.getClassCode());
        instrument.setTicker(dto.getTicker());
        instrument.setForQualInvestorFlag(dto.isForQualInvestorFlag());
        instrument.setPositionUid(dto.getPositionUid());
        instrument.setExchange(dto.getExchange());
        instrument.setCountryOfRisk(dto.getCountryOfRisk());
        instrument.setCountryOfRiskName(dto.getCountryOfRiskName());
        instrument.setIsin(dto.getIsin());
        instrument.setNominalCurrency(!dto.getCurrency().equals("") ?
                dto.getCurrency() :
                dto.getNominal().getCurrency());
        instrument.setNominalUnits(dto.getNominal().getUnits());
        instrument.setNominalNano(dto.getNominal().getNano());
        QuotationDto price = prices.stream()
                .filter(p -> p.getInstrumentUid().equals(dto.getUid()))
                .findFirst()
                .map(LastPriceDto::getPrice)
                .orElse(null);
        if (price != null){
            instrument.setLastPriceUnits(Integer.parseInt(price.getUnits()));
            instrument.setLastPriceNano(price.getNano());
        } else {
            return null;
        }
        if (instrument.getLot() == 0){
           return null;
        }
        if (dto.getInstrumentType().equals("currency") &&
              instrument.getLastPriceUnits() == 0)  {
          instrument.setLastPriceUnits(1);
        }
        Brand brand = new Brand();
        brand.setLogoName(dto.getBrand().getLogoName());
        brand.setLogoBaseColor(dto.getBrand().getLogoBaseColor());
        brand.setTextColor(dto.getBrand().getTextColor());
        instrument.setBrand(brand);
        instrument.setUpdateDate(Date.from(LocalDateTime.now()
                .atZone(ZoneId.systemDefault())
                .toInstant()));

        CurrencySymbol currencySymbol = currencyService.findByCode(instrument.getNominalCurrency().toUpperCase());
        instrument.setSymbol(currencySymbol);

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
                Date callDate = dto.getCallDate();
                bond.setCallDate(callDate);
                if (callDate != null && callDate.after(new Date(0))) {
                    bond.setNoCallFlag(false);
                } else {
                    bond.setNoCallFlag(true);
                }
                if (dto.getCouponQuantityPerYear() == 12) {
                    bond.setCouponEveryMonthFlag(true);
                } else {
                    bond.setCouponEveryMonthFlag(false);
                }
                bond.setMaturityDate(dto.getMaturityDate());
                bond.setAciСurrency(dto.getAciValue().getCurrency());
                bond.setAciUnits(dto.getAciValue().getUnits());
                bond.setAciNano(dto.getAciValue().getNano());
                bond.setCouponQuantityPerYear(dto.getCouponQuantityPerYear());
                bond.setFloatingCouponFlag(dto.isFloatingCouponFlag());
                bond.setSubordinatedFlag(dto.isSubordinatedFlag());
                bond.setAmortizationFlag(dto.isAmortizationFlag());
                bond.setPerpetualFlag(dto.isPerpetualFlag());
                bond.setSector(dto.getSector());
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

        instrument.setPrice(this.getPrice(instrument));
        if (instrument.getPrice().compareTo(BigDecimal.ZERO) == 0){
           return null;
        }
        return instrument;
    }

    public BigDecimal getPrice(Instrument instrument) {
        int units = instrument.getLastPriceUnits();
        int nano = instrument.getLastPriceNano();

        String curr;
        int decimalDigits;
        CurrencySymbol symbol = instrument.getSymbol();
        if (symbol != null){
            decimalDigits = symbol.getDecimalDigits();
            curr = instrument.getSymbol().getSymb();
        } else {
            decimalDigits = 3;
            curr = instrument.getNominalCurrency().toUpperCase();
        }
        BigDecimal total = new BigDecimal(units).add(
                        new BigDecimal(nano).divide(new BigDecimal("1000000000")))
                .setScale(decimalDigits, RoundingMode.HALF_UP);

        if ( instrument.getBond() != null && instrument.getNominalUnits() != 0) {
            total = total.divide(BigDecimal.valueOf(100)).multiply(BigDecimal.valueOf(instrument.getNominalUnits())).setScale(decimalDigits, RoundingMode.HALF_UP);;
        } else if (instrument.getCurrency() != null && instrument.getNominalUnits() != 0) {
            total = total.divide(BigDecimal.valueOf(instrument.getNominalUnits())).setScale(decimalDigits, RoundingMode.HALF_UP);;
        }

        return total;
    }

    @Override
    public Instrument dtoToEntity(InstrumentDto dto, List<LastPriceDto> prices, List<AssetDto> assets) {
        AssetDto asset = assets.stream().filter(x -> x.getUid().equals(dto.getAssetUid()))
                .findFirst()
                .orElse(new AssetDto());
        return dtoToEntity(dto,prices,asset);
    }

    @Override
    public List<String> findSectorsAll() {
        List<String> bondsSectors = bondRepository.findSectorsAll();
        List<String> sharesSectors = shareRepository.findSectorsAll();
        return Stream.concat(bondsSectors.stream(), sharesSectors.stream()).distinct().toList();
    }

    @Override
    public Page<Instrument> findAllBy(List<String> sectors, List<String> parameters,
                                             String searchValue, int page, Sort sort) {
        Pageable pageable = PageRequest.of(page, HomeController.ON_PAGE, sort);
        Page<Instrument> instruments = instrumentRepository.findAllBy(
                (sectors.size() == 0) ? null : sectors,
                parameters.contains("amortizationFlag"),
                parameters.contains("noCallFlag"),
                parameters.contains("floatingCouponFlag"),
                parameters.contains("perpetualFlag"),
                parameters.contains("subordinatedFlag"),
                parameters.contains("couponEveryMonthFlag"),
                parameters.contains("divYieldFlag"),
                parameters.contains("liquidityFlag"),
                parameters.contains("forQualInvestorFlag"),
                searchValue,
                pageable);
        return instruments;
    }

    @Override
    public Page<Instrument> findShareBy(List<String> sectors, List<String> parameters,
                                               String searchValue, int page, Sort sort) {
        Pageable pageable = PageRequest.of(page, HomeController.ON_PAGE, sort);
        Page<Instrument> instruments = instrumentRepository.findShareBy(
                (sectors.size() == 0) ? null : sectors,
                parameters.contains("divYieldFlag"),
                parameters.contains("liquidityFlag"),
                parameters.contains("forQualInvestorFlag"),
                searchValue,
                pageable);
        return instruments;
    }

    @Override
    public Page<Instrument> findBondBy(List<String> sectors, List<String> parameters,
                                              String searchValue, int page, Sort sort) {
        Pageable pageable = PageRequest.of(page, HomeController.ON_PAGE, sort);
        Page<Instrument> instruments = instrumentRepository.findBondBy(
                (sectors.size() == 0) ? null : sectors,
                parameters.contains("amortizationFlag"),
                parameters.contains("noCallFlag"),
                parameters.contains("floatingCouponFlag"),
                parameters.contains("perpetualFlag"),
                parameters.contains("subordinatedFlag"),
                parameters.contains("couponEveryMonthFlag"),
                parameters.contains("forQualInvestorFlag"),
                searchValue,
                pageable);
        return instruments;
    }

    @Override
    public Page<Instrument> findCurrencyBy(String searchValue, int page, Sort sort) {
        Pageable pageable = PageRequest.of(page, HomeController.ON_PAGE, sort);
        Page<Instrument> instruments = instrumentRepository.findCurrencyBy(
                 searchValue,
                 pageable);
        return instruments;
    }

    @Override
    public Page<Instrument> findInstruments(String type, Filter filter, String pageNum, Sort sort) {

        List<String> sectors = filter.getSelectedSectors().stream().filter(x -> x != null && !x.isEmpty()).toList();
        List<String> shareParameters = filter.getSelectedShareParameters();
        List<String> bondParameters = filter.getSelectedBondParameters();
        List<String> allParameters = filter.getSelectedAllParameters();
        String searchValue = filter.getSearchValue();
        switch (type) {
            case "all" -> {
                if (sectors.isEmpty() &&
                        (shareParameters == null || shareParameters.isEmpty()) &&
                        (bondParameters == null || bondParameters.isEmpty()) &&
                        (allParameters == null || allParameters.isEmpty()) &&
                        (searchValue == null || searchValue.isEmpty())) {
                    return findAll(Integer.parseInt(pageNum) - 1, sort);
                } else {
                    return findAllBy(sectors,
                            Stream.concat(
                                    Stream.concat(
                                           shareParameters.stream(),
                                           bondParameters.stream()
                                            ),
                                            allParameters.stream()
                                    )
                                    .collect(Collectors.toList()),
                            searchValue,
                            Integer.parseInt(pageNum) - 1,
                            sort);
                }

            }
            case "share" -> {
                if (sectors.isEmpty() &&
                        (shareParameters == null || shareParameters.isEmpty()) &&
                        (allParameters == null || allParameters.isEmpty()) &&
                        (searchValue == null || searchValue.isEmpty())) {
                    return findByShareIsNotEmpty(Integer.parseInt(pageNum) - 1, sort);
                } else {
                    return findShareBy(sectors,
                            Stream.concat(shareParameters.stream(),
                                            allParameters.stream())
                                    .collect(Collectors.toList()),
                            searchValue,
                            Integer.parseInt(pageNum) - 1,
                            sort);
                }
            }
            case "bond" -> {
                if (sectors.isEmpty() &&
                        (bondParameters == null || bondParameters.isEmpty()) &&
                        (allParameters == null || allParameters.isEmpty()) &&
                        (searchValue == null || searchValue.isEmpty())) {
                    return findByBondIsNotEmpty(Integer.parseInt(pageNum) - 1, sort);
                } else {
                    return findBondBy(
                            sectors,
                            Stream.concat(
                                    bondParameters.stream(),
                                    allParameters.stream())
                            .collect(Collectors.toList()),
                            searchValue,
                            Integer.parseInt(pageNum) - 1,
                            sort);
                }
            }
            case "currency" -> {
                if (searchValue == null || searchValue.isEmpty()) {
                    return findByCurrencyIsNotEmpty(Integer.parseInt(pageNum) - 1, sort);
                } else {
                    return findCurrencyBy(
                            searchValue,
                            Integer.parseInt(pageNum) - 1,
                            sort);
                }
            }
            default -> {
                return null;
            }
        }
    }

    @Override
    public Instrument findFirstByUid(String uid) {
        Optional<Instrument> instrumentOpt = instrumentRepository.findFirstByUid(uid);
        return instrumentOpt.orElse(null);
    }

    @Override
    public Instrument save(Instrument instrument) {
        return instrumentRepository.save(instrument);
    }


}
