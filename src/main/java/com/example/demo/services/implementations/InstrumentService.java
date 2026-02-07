package com.example.demo.services.implementations;

import com.example.demo.controller.HomeController;
import com.example.demo.dto.InstrumentDto;
import com.example.demo.entity.*;
import com.example.demo.repository.repository.InstrumentRepository;
import com.example.demo.repository.repository.BondRepository;
import com.example.demo.repository.repository.ShareRepository;
import com.example.demo.services.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
    ShareRepository shareRepository;

    @Autowired
    BondRepository brandRepository;

    @Autowired
    @Qualifier("restTemplateLong")
    private RestTemplate restTemplate;

    @Autowired
    Environment env;

    @Override
    public Page<Instrument> findAll(int page, Sort sort) {
        Pageable pageable = PageRequest.of(page, HomeController.ON_PAGE, sort);
        return instrumentRepository.findAll(pageable);
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
        return instrument;
    }

    @Override
    public List<String> findSectorsAll() {
        List<String> bondsSectors = bondRepository.findSectorsAll();
        List<String> sharesSectors = shareRepository.findSectorsAll();
        return Stream.concat(bondsSectors.stream(), sharesSectors.stream()).distinct().toList();
    }

    @Override
    public Page<Instrument> findAllBySectors(List<String> sectors, List<String> parameters, int page, Sort sort) {
        System.out.println("findAllBySec " + parameters);
        Pageable pageable = PageRequest.of(page, HomeController.ON_PAGE, sort);
        Page<Instrument> instruments = instrumentRepository.findAllBySectors(
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
                pageable);
        return instruments;
    }

    @Override
    public Page<Instrument> findShareBySectors(List<String> sectors, List<String> parameters, int page, Sort sort) {
        System.out.println("findShareBySec " + parameters);
        Pageable pageable = PageRequest.of(page, HomeController.ON_PAGE, sort);
        Page<Instrument> instruments = instrumentRepository.findShareBySectors(
                (sectors.size() == 0) ? null : sectors,
                parameters.contains("divYieldFlag"),
                parameters.contains("liquidityFlag"),
                parameters.contains("forQualInvestorFlag"),
                pageable);
        return instruments;
    }

    @Override
    public Page<Instrument> findBondBySectors(List<String> sectors, List<String> parameters, int page, Sort sort) {
        System.out.println("findBondBySec " + parameters);
        Pageable pageable = PageRequest.of(page, HomeController.ON_PAGE, sort);
        Page<Instrument> instruments = instrumentRepository.findBondBySectors(
                (sectors.size() == 0) ? null : sectors,
                parameters.contains("amortizationFlag"),
                parameters.contains("noCallFlag"),
                parameters.contains("floatingCouponFlag"),
                parameters.contains("perpetualFlag"),
                parameters.contains("subordinatedFlag"),
                parameters.contains("couponEveryMonthFlag"),
                parameters.contains("forQualInvestorFlag"),
                pageable);
        return instruments;
    }

    @Override
    public Page<Instrument> findInstruments(String type, Filter filter, String pageNum, Sort sort) {

        List<String> sectors = filter.getSelectedSectors().stream().filter(x -> x != null && !x.isEmpty()).toList();
        List<String> shareParameters = filter.getSelectedShareParameters();
        List<String> bondParameters = filter.getSelectedBondParameters();
        List<String> allParameters = filter.getSelectedAllParameters();
        switch (type) {
            case "all" -> {
                if (sectors.isEmpty() &&
                        (shareParameters == null || shareParameters.isEmpty()) &&
                        (bondParameters == null || bondParameters.isEmpty()) &&
                        (allParameters == null || allParameters.isEmpty())) {
                    return findAll(Integer.parseInt(pageNum) - 1, sort);
                } else {
                    return findAllBySectors(sectors,
                            Stream.concat(
                                    Stream.concat(
                                           shareParameters.stream(),
                                           bondParameters.stream()
                                            ),
                                            allParameters.stream()
                                    )
                                    .collect(Collectors.toList()),
                            Integer.parseInt(pageNum) - 1,
                            sort);
                }

            }
            case "share" -> {
                if (sectors.isEmpty() &&
                        (shareParameters == null || shareParameters.isEmpty()) &&
                        (allParameters == null || allParameters.isEmpty())) {
                    return findByShareIsNotEmpty(Integer.parseInt(pageNum) - 1, sort);
                } else {
                    return findShareBySectors(sectors,
                            Stream.concat(shareParameters.stream(),
                                            allParameters.stream())
                                    .collect(Collectors.toList()),
                            Integer.parseInt(pageNum) - 1,
                            sort);
                }
            }
            case "bond" -> {
                if (sectors.isEmpty() &&
                        (bondParameters == null || bondParameters.isEmpty()) &&
                        (allParameters == null || allParameters.isEmpty())) {
                    return findByBondIsNotEmpty(Integer.parseInt(pageNum) - 1, sort);
                } else {
                    return findBondBySectors(
                            sectors,
                            Stream.concat(
                                    bondParameters.stream(),
                                    allParameters.stream())
                            .collect(Collectors.toList()),
                            Integer.parseInt(pageNum) - 1,
                            sort);
                }
            }
            case "currency" -> {
                return findByCurrencyIsNotEmpty(Integer.parseInt(pageNum) - 1, sort);
            }
            default -> {
                return null;
            }
        }
    }
}
