package com.example.demo.controller;

import com.example.demo.dto.FilterRequest;
import com.example.demo.dto.InstrumentDto;
import com.example.demo.dto.LastPriceDto;
import com.example.demo.entity.Instrument;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.model.CurrencySymbol;
import com.example.demo.service.CacheService;
import com.example.demo.service.DBService;
import com.example.demo.service.BrokerApiService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @GetMapping("/home")
    public String home(Model model,
                       HttpServletRequest request,
                       HttpServletResponse response) {

//        List<Instrument> instruments = new ArrayList<>();
//        List<InstrumentDto> dtos = new ArrayList<>();
//        List<InstrumentDto> dtosAll = new ArrayList<>();
//        List<LastPriceDto> pricesAll = new ArrayList<>();
//        List<LastPriceDto> prices = new ArrayList<>();
//
////------------------------------------------------------------------------------//
//        dtos = tbankApiService.getAllBonds().getBody().getInstruments();
//        dtosAll.addAll(dtos);
//        List<String> uids = new ArrayList<>();
//        for (InstrumentDto dto : dtos) {
//            uids.add(dto.getUid());
//            if (uids.size() == 2999){
//                prices = tbankApiService.getLastPrices(uids).getBody().getLastPrices();
//                uids = new ArrayList<>();
//                pricesAll.addAll(prices);
//            }
//        }
//        if (!uids.isEmpty()){
//            prices = tbankApiService.getLastPrices(uids).getBody().getLastPrices();
//            pricesAll.addAll(prices);
//        }
//
////-------------------------------------------------------------------------------------//
//        dtos = tbankApiService.getAllShares().getBody().getInstruments();
//        dtosAll.addAll(dtos);
//        uids = new ArrayList<>();
//        for (InstrumentDto dto : dtos) {
//            uids.add(dto.getUid());
//            if (uids.size() == 2999){
//                prices = tbankApiService.getLastPrices(uids).getBody().getLastPrices();
//                uids = new ArrayList<>();
//                pricesAll.addAll(prices);
//            }
//        }
//        if (!uids.isEmpty()){
//            prices = tbankApiService.getLastPrices(uids).getBody().getLastPrices();
//            pricesAll.addAll(prices);
//        }
//
////-------------------------------------------------------------------------------------//
//        dtosAll.addAll(tbankApiService.getAllCurrencies().getBody().getInstruments());
//
////------------------------------------------------------------------------------------//
//        for (InstrumentDto dto : dtosAll) {
//            Instrument instrument = instrumentService.dtoToEntity(dto, pricesAll);
//            instruments.add(instrument);
//        }
//
//        if (!instruments.isEmpty()) {
//            instrumentService.saveAll(instruments);
//        }

        jwtUtil.setCookie(request,response);

        model.addAttribute("showHeader", true);
        model.addAttribute("showFooter", true);
        model.addAttribute("showHome", true);

        return "main";
    }


    @GetMapping("/sign-in")
    public String signIn(Model model,
                         HttpServletRequest request,
                         HttpServletResponse response) {
        jwtUtil.setCookie(request,response);
        return "sign-in";
    }

    @GetMapping("/sign-up")
    public String signUp(Model model,
                         HttpServletRequest request,
                         HttpServletResponse response) {
        jwtUtil.setCookie(request,response);
        return "sign-up";
    }

    @GetMapping("/log-out")
    public String logOut(Model model,
                         HttpServletRequest request,
                         HttpServletResponse response) {
        jwtUtil.setCookie(request,response);
        return "log-out";
    }

    @PostMapping("/catalog/filter")
    public ResponseEntity<?> filter(@RequestBody FilterRequest body,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {
        jwtUtil.setCookie(request,response);
        cacheService.setFilter(body.getFilter());
        return ResponseEntity.ok().build();
    }

//    @GetMapping("/catalog")
//    public String emptyCatalog(HttpServletRequest request,
//                               HttpServletResponse response) {
//        jwtUtil.setCookie(request,response);
//        return "redirect:/invest/catalog/" + cacheService.getType();
//    }

    @GetMapping("/catalog/{type}")
    public String catalog(HttpServletRequest request,
                          HttpServletResponse response,
                          @PathVariable String type,
                          @RequestParam(name = "page", defaultValue = "1") String pageNum,
                          @RequestParam(name = "sort", defaultValue = "") String sort,
                          Model model) {

        jwtUtil.setCookie(request,response);
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

        if (page == null) {
            model.addAttribute("showHome", true);
            return "main";

        } else {
            model.addAttribute("showHeader", true);
            model.addAttribute("showFooter", true);
            model.addAttribute("showCatalog", true);
            model.addAttribute("instruments", page.getContent());
            model.addAttribute("pagesCount", page.getTotalPages());
            model.addAttribute("pageNum", Integer.parseInt(pageNum));
            model.addAttribute("onPage", ON_PAGE);
            model.addAttribute("instrumentsCount", page.getTotalElements());
            model.addAttribute("type", cacheService.getType());
            model.addAttribute("sort", cacheService.getSort());
            model.addAttribute("shareParameters", shareParameters);
            model.addAttribute("bondParameters", bondParameters);
            model.addAttribute("allParameters", allParameters);
            model.addAttribute("sectors", instrumentService.findSectorsAll());
            model.addAttribute("selectedSectors", cacheService.getFilter().getSelectedSectors());
            model.addAttribute("selectedShareParameters", cacheService.getFilter().getSelectedShareParameters());
            model.addAttribute("selectedBondParameters", cacheService.getFilter().getSelectedBondParameters());
            model.addAttribute("selectedAllParameters", cacheService.getFilter().getSelectedAllParameters());
            return "main";
        }
    }
    @GetMapping("/catalog-single/{uid}")
    public String catalogSingle(HttpServletRequest request,
                          HttpServletResponse response,
                          @PathVariable String uid,
                          Model model) {

        jwtUtil.setCookie(request,response);

        Instrument instrument = instrumentService.findFirstByUid(uid);

        if (instrument == null) {
            model.addAttribute("showHeader", true);
            model.addAttribute("showFooter", true);
            model.addAttribute("showHome", true);
            return "main";

        } else {
            model.addAttribute("showHeader", true);
            model.addAttribute("showFooter", true);
            model.addAttribute("showCatalogSingle", true);
            model.addAttribute("instrument", instrument);
            return "main";
        }
    }

    @GetMapping("/contact")
    public String contact(HttpServletRequest request,
                          HttpServletResponse response,
                          Model model) {
        jwtUtil.setCookie(request,response);
        model.addAttribute("showHeader", true);
        model.addAttribute("showFooter", true);
        model.addAttribute("showContact", true);
        return "main";
    }

    @GetMapping("/logout/success")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        jwtUtil.setCookie(request,response);
        return "log-out";
    }
}
