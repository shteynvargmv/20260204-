package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.dto.request.FilterRequest;
import com.example.demo.dto.response.AssetResponse;
import com.example.demo.dto.response.AssetsResponse;
import com.example.demo.dto.response.FavoriteResponse;
import com.example.demo.dto.response.RefreshResponse;
import com.example.demo.entity.Favorite;
import com.example.demo.entity.Instrument;
import com.example.demo.entity.User;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.model.Currencies;
import com.example.demo.model.CurrencyData;
import com.example.demo.entity.CurrencySymbol;
import com.example.demo.service.*;
import com.example.demo.service.entservice.CurrencyService;
import com.example.demo.service.entservice.FavoriteService;
import com.example.demo.service.entservice.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/invest")
public class HomeController {

    public static final int ON_PAGE = 15;

    @Autowired
    @Qualifier("instrument")
    DBService instrumentService;
    @Autowired
    @Qualifier("tbank")
    BrokerApiService tbankApiService;
    @Autowired
    @Qualifier("simple")
    CacheService cacheService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    Currencies currencies;
    @Autowired
    UserService userService;
    @Autowired
    FavoriteService favoriteService;

    @PostMapping("/refresh/{uid}")
    public ResponseEntity<?> refresh(@PathVariable String uid,
                                     Model model,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {

        jwtUtil.setCookie(request, response);
        try {
            List<LastPriceDto> prices;
            AssetDto asset;
            Instrument instrumentOld = instrumentService.findFirstByUid(uid);
            Instrument instrumentNew = new Instrument();
            InstrumentDto dto = new InstrumentDto();
            if (instrumentOld != null) {
                if (instrumentOld.getShare() != null) {
                    dto = tbankApiService.getShareByUid(instrumentOld.getShare().getUid()).getBody().getInstrument();
                } else if (instrumentOld.getBond() != null) {
                    dto = tbankApiService.getBondByUid(instrumentOld.getBond().getUid()).getBody().getInstrument();
                } else if (instrumentOld.getCurrency() != null) {
                    dto = tbankApiService.getCurrencyByUid(instrumentOld.getCurrency().getUid()).getBody().getInstrument();
                }
                prices = tbankApiService.getLastPrices(uid).getBody().getLastPrices();
                asset = tbankApiService.getAssetById(dto.getAssetUid()).getBody().getAsset();
                instrumentNew = instrumentService.dtoToEntity(dto, prices, asset);
            }
            Instrument instrument = instrumentService.save(instrumentNew);
            if (instrument != null) {
                return ResponseEntity.ok().body(new RefreshResponse(
                        dto,
                        instrument.getUpdateDateLocalString(),
                        instrument.getPriceString()
                        ));
            }
            return ResponseEntity.badRequest().body(new RefreshResponse("Ошибка обновления"));

        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body(new RefreshResponse(e.getMessage()));
        }
    }

    @PostMapping("/favorite-add/{uid}")
    public ResponseEntity<FavoriteResponse> favoriteAdd(@PathVariable String uid,
                                     Model model,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {

        User user = userService.findByUsername(jwtUtil.getUsername(request));
        jwtUtil.setCookie(request, response);

        List<String> uids = favoriteService.findInstrumentsByUserId(user.getId())
                .stream()
                .map(Instrument::getUid)
                .collect(Collectors.toList());

        if (uids.contains(uid)){
            FavoriteResponse favoriteResponse = new FavoriteResponse(uids);
            jwtUtil.setFavorites(uid, response);
            return ResponseEntity.ok().body(favoriteResponse);
        }

        Favorite favorite = new Favorite(user, instrumentService.findFirstByUid(uid));
        favorite = favoriteService.save(favorite);
        if (favorite != null){
            uids.add(uid);
            FavoriteResponse favoriteResponse = new FavoriteResponse(uids);
            jwtUtil.setFavorites(uid, response);
            return ResponseEntity.ok().body(favoriteResponse);
        }

        return ResponseEntity.badRequest().body(new FavoriteResponse(
                uids,
                "При добавлениие в Избранное возникла ошибка"
        ));

    }

    @PostMapping("/favorite-del/{uid}")
    public ResponseEntity<FavoriteResponse> favoriteDel(@PathVariable String uid,
                                                        Model model,
                                                        HttpServletRequest request,
                                                        HttpServletResponse response) {

        User user = userService.findByUsername(jwtUtil.getUsername(request));
        jwtUtil.setCookie(request, response);

        List<String> uids = favoriteService.findInstrumentsByUserId(user.getId())
                .stream()
                .map(Instrument::getUid)
                .collect(Collectors.toList());

        if (!uids.contains(uid)){
            FavoriteResponse favoriteResponse = new FavoriteResponse(uids);
            jwtUtil.delFavorites(uid, response);
            return ResponseEntity.ok().body(favoriteResponse);
        }

        if (favoriteService.deleteByUserAndInstrument(user, instrumentService.findFirstByUid(uid))){
            uids.remove(uid);
            FavoriteResponse favoriteResponse = new FavoriteResponse(uids);
            jwtUtil.delFavorites(uid, response);
            return ResponseEntity.ok().body(favoriteResponse);
        }

        return ResponseEntity.badRequest().body(new FavoriteResponse(
                uids,
                "При удалении из Избранного возникла ошибка"
        ));
    }

    @PostMapping("/refresh/all")
    public ResponseEntity<?> refreshAll(Model model,
                                        HttpServletRequest request,
                                        HttpServletResponse response) {

        jwtUtil.setCookie(request, response);

        try {
            List<Instrument> instruments = new ArrayList<>();
            List<InstrumentDto> dtos;
            List<InstrumentDto> dtosAll = new ArrayList<>();
            List<LastPriceDto> prices;
            List<LastPriceDto> pricesAll = new ArrayList<>();
            List<AssetDto> assets = new ArrayList<>();

            List<CurrencyData> currencyDataList = currencies.getCurrencyDataList();
            List<CurrencySymbol> currencySymbolList = new ArrayList<>();
            if (currencyDataList != null && !currencyDataList.isEmpty()) {
                for (CurrencyData currencyData : currencyDataList) {
                    CurrencySymbol currencySymbol = new CurrencySymbol();
                    currencySymbol.setName(currencyData.getName());
                    currencySymbol.setSymbolNative(currencyData.getSymbol_native());
                    currencySymbol.setDecimalDigits(currencyData.getDecimal_digits());
                    currencySymbol.setRounding(currencyData.getRounding());
                    currencySymbol.setCode(currencyData.getCode());
                    currencySymbol.setNamePlural(currencyData.getName_plural());
                    currencySymbol.setSymb(currencyData.getSymbol());
                    currencySymbolList.add(currencySymbol);
                    currencySymbolList.forEach(x -> {
                        CurrencySymbol exist = currencyService.findByCode(x.getCode());
                        if (exist != null) {
                            x.setId(exist.getId());
                        }
                    });
                }
                currencyService.saveAll(currencySymbolList);
            }

//------------------------------------------------------------------------------//
            dtos = tbankApiService.getAllBonds().getBody().getInstruments();
            assets.addAll(tbankApiService.getAllAssetsBonds().getBody().getAssets());
            dtosAll.addAll(dtos);
            List<String> uids = new ArrayList<>();
            for (InstrumentDto dto : dtos) {
                uids.add(dto.getUid());
                if (uids.size() == 2999) {
                    prices = tbankApiService.getLastPrices(uids).getBody().getLastPrices();
                    uids = new ArrayList<>();
                    pricesAll.addAll(prices);
                }
            }
            if (!uids.isEmpty()) {
                prices = tbankApiService.getLastPrices(uids).getBody().getLastPrices();
                pricesAll.addAll(prices);
            }

//-------------------------------------------------------------------------------------//
            dtos = tbankApiService.getAllShares().getBody().getInstruments();
            assets.addAll(tbankApiService.getAllAssetsShares().getBody().getAssets());
            dtosAll.addAll(dtos);
            uids = new ArrayList<>();
            for (InstrumentDto dto : dtos) {
                uids.add(dto.getUid());
                if (uids.size() == 2999) {
                    prices = tbankApiService.getLastPrices(uids).getBody().getLastPrices();
                    uids = new ArrayList<>();
                    pricesAll.addAll(prices);
                }
            }
            if (!uids.isEmpty()) {
                prices = tbankApiService.getLastPrices(uids).getBody().getLastPrices();
                pricesAll.addAll(prices);
            }

//-------------------------------------------------------------------------------------//
            dtosAll.addAll(tbankApiService.getAllCurrencies().getBody().getInstruments());
            assets.addAll(tbankApiService.getAllAssetsCurrencies().getBody().getAssets());

            dtos = tbankApiService.getAllCurrencies().getBody().getInstruments();
            assets.addAll(tbankApiService.getAllAssetsCurrencies().getBody().getAssets());
            dtosAll.addAll(dtos);
            uids = dtos.stream()
                    .map(InstrumentDto::getUid)
                    .collect(Collectors.toList());
            prices = tbankApiService.getLastPrices(uids).getBody().getLastPrices();
            pricesAll.addAll(prices);

//------------------------------------------------------------------------------------//
            int i = 1;
            for (InstrumentDto dto : dtosAll) {
                Instrument instrument = instrumentService.dtoToEntity(dto, pricesAll, assets);
                if (instrument != null) {
                    instruments.add(instrument);
                }
            }

            if (!instruments.isEmpty()) {
                instrumentService.saveAll(instruments);
            }

            return ResponseEntity.ok().build();

        } catch (NullPointerException e) {
            return ResponseEntity.badRequest().body(new RefreshResponse(e.getMessage()));
        }
    }

    @GetMapping("/home")
    public String home(Model model,
                       HttpServletRequest request,
                       HttpServletResponse response) {

        jwtUtil.setCookie(request, response);

        model.addAttribute("showHeader", true);
        model.addAttribute("showFooter", true);
        model.addAttribute("showHome", true);

        return "main";
    }

    @GetMapping("/favorite")
    public String favorite(Model model,
                       HttpServletRequest request,
                       HttpServletResponse response) {

        User user = userService.findByUsername(jwtUtil.getUsername(request));
        jwtUtil.setCookie(request, response);
        List<Instrument> instruments = favoriteService.findInstrumentsByUserId(user.getId());
        jwtUtil.setFavorites(instruments,response);
        model.addAttribute("instruments", instruments);
        model.addAttribute("showHeader", true);
        model.addAttribute("showFooter", true);
        model.addAttribute("showFavorite", true);

        return "main";
    }


    @GetMapping("/sign-in")
    public String signIn(Model model,
                         HttpServletRequest request,
                         HttpServletResponse response) {
        jwtUtil.setCookie(request, response);
        return "sign-in";
    }

    @GetMapping("/sign-up")
    public String signUp(Model model,
                         HttpServletRequest request,
                         HttpServletResponse response) {
        jwtUtil.setCookie(request, response);
        return "sign-up";
    }

    @GetMapping("/log-out")
    public String logOut(Model model,
                         HttpServletRequest request,
                         HttpServletResponse response) {
        jwtUtil.setCookie(request, response);
        return "log-out";
    }

    @PostMapping("/catalog/filter")
    public ResponseEntity<?> filter(@RequestBody FilterRequest body,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {
        jwtUtil.setCookie(request, response);
        cacheService.setFilter(body.getFilter());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/catalog/{type}")
    public String catalog(HttpServletRequest request,
                          HttpServletResponse response,
                          @PathVariable String type,
                          @RequestParam(name = "page", defaultValue = "1") String pageNum,
                          @RequestParam(name = "sort", defaultValue = "") String sort,
                          Model model) {

        jwtUtil.setCookie(request, response);
        cacheService.setType(type);
        cacheService.setSortBy(sort);

        Page<Instrument> page = instrumentService.findInstruments(
                cacheService.getType(),
                cacheService.getFilter(),
                pageNum,
                cacheService.getSortBy());

        Map<String, String> bondParameters = new HashMap<>();
        bondParameters.put("amortizationFlag", "Без амортизации");
        bondParameters.put("noCallFlag", "Без оферты");
        bondParameters.put("floatingCouponFlag", "Фиксированный купон");
        bondParameters.put("perpetualFlag", "Бессрочная");
        bondParameters.put("subordinatedFlag", "Субординированная");
        bondParameters.put("couponEveryMonthFlag", "Ежемесячный купон");

        Map<String, String> shareParameters = new HashMap<>();
        shareParameters.put("divYieldFlag", "Дивиденты");
        shareParameters.put("liquidityFlag", "Ликвидная");

        Map<String, String> allParameters = new HashMap<>();
        allParameters.put("forQualInvestorFlag", "Неквалифицированный инвестор");

        Map<String, String> sectors = new HashMap<>();
        sectors.put("financial", "Финансовый сектор");
        sectors.put("consumer", "Потребительский сектор");
        sectors.put("energy", "Энергетика");
        sectors.put("telecom", "Телекоммуникации");
        sectors.put("municipal", "Муниципальный сектор");
        sectors.put("utilities", "Коммунальные услуги");
        sectors.put("materials", "Материалы");
        sectors.put("government", "Государственный сектор");
        sectors.put("industrials", "Промышленность");
        sectors.put("real_estate", "Недвижимость");
        sectors.put("it", "Информационные технологии");
        sectors.put("health_care", "Здравоохранение");
        sectors.put("ecomaterials", "Эко-материалы");
        sectors.put("green_energy", "Зеленая энергетика");
        sectors.put("electrocars", "Электромобили");
        sectors.put("green_buildings", "Зеленое строительство");
        sectors.put("other", "Другое");
        sectors.keySet().retainAll(instrumentService.findSectorsAll());

        if (page == null) {
            model.addAttribute("showHome", true);
            return "main";

        } else {
            List<Instrument> instruments = page.getContent();

            User user = userService.findByUsername(jwtUtil.getUsername(request));
            jwtUtil.setFavorites(favoriteService.findInstrumentsByUserId(user.getId()), response);

            model.addAttribute("showHeader", true);
            model.addAttribute("showFooter", true);
            model.addAttribute("showCatalog", true);
            model.addAttribute("instruments", instruments);
            model.addAttribute("pagesCount", page.getTotalPages());
            model.addAttribute("pageNum", Integer.parseInt(pageNum));
            model.addAttribute("onPage", ON_PAGE);
            model.addAttribute("instrumentsCount", page.getTotalElements());
            model.addAttribute("type", cacheService.getType());
            model.addAttribute("sort", cacheService.getSort());
            model.addAttribute("shareParameters", shareParameters);
            model.addAttribute("bondParameters", bondParameters);
            model.addAttribute("allParameters", allParameters);
            model.addAttribute("sectors", sectors);
            model.addAttribute("selectedSectors", cacheService.getFilter().getSelectedSectors());
            model.addAttribute("selectedShareParameters", cacheService.getFilter().getSelectedShareParameters());
            model.addAttribute("selectedBondParameters", cacheService.getFilter().getSelectedBondParameters());
            model.addAttribute("selectedAllParameters", cacheService.getFilter().getSelectedAllParameters());
            model.addAttribute("searchValue", cacheService.getFilter().getSearchValue());
            return "main";
        }
    }

    @GetMapping("/catalog-single/{uid}")
    public String catalogSingle(HttpServletRequest request,
                                HttpServletResponse response,
                                @PathVariable String uid,
                                Model model) {

        User user = userService.findByUsername(jwtUtil.getUsername(request));
        jwtUtil.setCookie(request, response);

        Instrument instrument = instrumentService.findFirstByUid(uid);

        if (instrument == null) {
            model.addAttribute("showHeader", true);
            model.addAttribute("showFooter", true);
            model.addAttribute("showHome", true);
            return "main";

        } else {
            if (instrument.getDescription() == null) {
                ResponseEntity<AssetResponse> resp = tbankApiService.getAssetById(instrument.getAssetUid());
                if (resp != null) {
                    AssetDto asset = resp.getBody().getAsset();
                    if (asset != null) {
                        instrument.setDescription(asset.getDescription());
                        instrument.setBrCodeName(asset.getBrCodeName());
                        instrument = instrumentService.save(instrument);
                    }
                }
                instrument.setFavorites(
                        favoriteService.findByUserIdAndInstrumentUid(
                                user.getId(),
                                instrument.getUid()));
            }
            jwtUtil.setFavorites(instrument, response);
            model.addAttribute("instrument", instrument);
            model.addAttribute("showHeader", true);
            model.addAttribute("showFooter", true);
            model.addAttribute("showCatalogSingle", true);

            return "main";
        }
    }

    @GetMapping("/contact")
    public String contact(HttpServletRequest request,
                          HttpServletResponse response,
                          Model model) {
        jwtUtil.setCookie(request, response);
        model.addAttribute("showHeader", true);
        model.addAttribute("showFooter", true);
        model.addAttribute("showContact", true);
        return "main";
    }

    @GetMapping("/logout/success")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        jwtUtil.setCookie(request, response);
        return "log-out";
    }
}
