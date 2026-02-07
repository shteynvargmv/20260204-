package com.example.demo.controller;

import com.example.demo.dto.FilterRequest;
import com.example.demo.dto.InstrumentDto;
import com.example.demo.entity.Instrument;
import com.example.demo.services.CacheService;
import com.example.demo.services.DBService;
import com.example.demo.services.BrokerApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/home")
    public String home(Model model) {

//        List<Instrument> instruments = new ArrayList<>();
//        List<InstrumentDto> dtos = new ArrayList<>();
//        dtos.addAll(tbankApiService.getAllBonds().getBody().getInstruments());
//        dtos.addAll(tbankApiService.getAllShares().getBody().getInstruments());
//        dtos.addAll(tbankApiService.getAllCurrencies().getBody().getInstruments());
//        System.out.println("1");
//        for (InstrumentDto dto : dtos) {
//            Instrument instrument = instrumentService.dtoToEntity(dto);
//            System.out.println("2");
//            instruments.add(instrument);
//        }
//        System.out.println(instruments.size());
//        if (!instruments.isEmpty()) {
//            instrumentService.saveAll(instruments);
//        }

        model.addAttribute("showHeader", true);
        model.addAttribute("showFooter", true);
        model.addAttribute("showHome", true);

        return "main";
    }


    @GetMapping("/sign-in")
    public String signIn(Model model) {
        return "sign-in";
    }

    @GetMapping("/sign-up")
    public String signUp(Model model) {
        return "sign-up";
    }

    @GetMapping("/log-out")
    public String logOut(Model model) {
        return "log-out";
    }

    @PostMapping("/catalog/filter")
    public ResponseEntity<?> filter(@RequestBody FilterRequest body) {
        cacheService.setFilter(body.getFilter());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/catalog")
    public String emptyCatalog() {
        return "redirect:/invest/catalog/" + cacheService.getType();
    }

    @GetMapping("/catalog/{type}")
    public String catalog(@PathVariable String type,
                          @RequestParam(name = "page", defaultValue = "1") String pageNum,
                          @RequestParam(name = "sort", defaultValue = "") String sort,
                          Model model) {

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

        Map<String, String> allParameters = new HashMap<>();
        allParameters.put("forQualInvestorFlag", "Неквалифицированный инвестор");

        Map<String, String> shareParameters = new HashMap<>();
        shareParameters.put("divYieldFlag", "Дивиденты");
        shareParameters.put("liquidityFlag", "Ликвидная");

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

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("showHeader", true);
        model.addAttribute("showFooter", true);
        model.addAttribute("showContact", true);
        return "main";
    }
}
