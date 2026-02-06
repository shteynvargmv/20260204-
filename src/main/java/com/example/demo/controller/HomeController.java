package com.example.demo.controller;

import com.example.demo.dto.InstrumentDto;
import com.example.demo.entity.Instrument;
import com.example.demo.services.DBService;
import com.example.demo.services.BrokerApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/invest")
public class HomeController {

    public static final int ON_PAGE = 12;

    @Autowired
    @Qualifier("instrument")
    DBService instrumentService;

    @Autowired
    @Qualifier("tbank")
    BrokerApiService tbankApiService;

    @GetMapping("/home")
    public String home(Model model) {

//          List<Instrument> instruments = new ArrayList<>();
//            List<InstrumentDto> dtos = new ArrayList<>();
////            dtos.addAll(tbankApiService.getAllBonds().getBody().getInstruments());
////            dtos.addAll(tbankApiService.getAllShares().getBody().getInstruments());
//            dtos.addAll(tbankApiService.getAllCurrencies().getBody().getInstruments());
//        System.out.println("1");
//            for (InstrumentDto dto : dtos) {
//                Instrument instrument = instrumentService.dtoToEntity(dto);
//                System.out.println("2");
//                instruments.add(instrument);
//            }
//        System.out.println(instruments.size());
//            if (!instruments.isEmpty()) {
//                instrumentService.saveAll(instruments);
//            }

//        return ResponseEntity.ok().body(instruments);
//        entity.addAttribute("pageName", "Home page");
//        entity.addAttribute("showFooter", false);
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

    @GetMapping("/catalog/{type}")
    public String catalog(@PathVariable String type,
                          @RequestParam(name = "page", defaultValue = "1") String pageNum,
                          @RequestParam(name = "sort", defaultValue = "") String sort,
                          Model model) {

        model.addAttribute("type", type);
        model.addAttribute("showHeader", true);
        model.addAttribute("showFooter", true);

        List<Instrument> instruments = new ArrayList<>();
        int pagesCount = 0;
        long instrumentsCount = 0;
        switch (type){
            case "all" -> {
                Page<Instrument> page = instrumentService.findAll(Integer.parseInt(pageNum) - 1, sort);
                instruments = page.getContent();
                pagesCount = page.getTotalPages();
                instrumentsCount = page.getTotalElements();
            }
            case "share" -> {
                Page<Instrument> page = instrumentService.findByShareIsNotEmpty(Integer.parseInt(pageNum)-1, sort);
                instruments = page.getContent();
                pagesCount = page.getTotalPages();
                instrumentsCount = page.getTotalElements();
            }
            case "bond" -> {
                Page<Instrument> page = instrumentService.findByBondIsNotEmpty(Integer.parseInt(pageNum)-1, sort);
                instruments = page.getContent();
                pagesCount = page.getTotalPages();
                instrumentsCount = page.getTotalElements();
            }
            case "currency" -> {
                Page<Instrument> page = instrumentService.findByCurrencyIsNotEmpty(Integer.parseInt(pageNum)-1, sort);
                instruments = page.getContent();
                pagesCount = page.getTotalPages();
                instrumentsCount = page.getTotalElements();
            }

            default -> {
                model.addAttribute("showHome", true);
                return "main";
            }
        }
        model.addAttribute("showCatalog", true);
        model.addAttribute("instruments", instruments);
        model.addAttribute("pagesCount", pagesCount);
        model.addAttribute("instrumentsCount", instrumentsCount);
        model.addAttribute("pageNum", Integer.parseInt(pageNum));
        model.addAttribute("on_page", ON_PAGE);
        model.addAttribute("sort", instrumentService.getSortBy());
        return "main";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("showHeader", true);
        model.addAttribute("showFooter", true);
        model.addAttribute("showContact", true);
        return "main";
    }
}
