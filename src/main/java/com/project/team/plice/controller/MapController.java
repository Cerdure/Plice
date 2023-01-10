package com.project.team.plice.controller;

import com.project.team.plice.domain.TradeData;
import com.project.team.plice.dto.TradeDataDto;
import com.project.team.plice.service.MapServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MapController {

    private final MapServiceImpl mapService;

    @GetMapping("/map")
    public String map() {
        return "map";
    }

    @GetMapping("/find-data")
    @ResponseBody
    public List<TradeDataDto> firstFindData(@RequestParam(name = "neLng") String neLng,
                                            @RequestParam(name = "neLat") String neLat,
                                            @RequestParam(name = "swLng") String swLng,
                                            @RequestParam(name = "swLat") String swLat, Model model) {

        System.out.println("neLng = " + neLng);
        System.out.println("neLat = " + neLat);
        System.out.println("swLng = " + swLng);
        System.out.println("swLat = " + swLat);

        List<TradeData> tradeDataList = mapService.findTradeDataInBounds(swLng, neLng, neLat, swLat);
        System.out.println("tradeDataList.size() = " + tradeDataList.size());
        List<TradeDataDto> tradeDataDtoList = new ArrayList<>();
        for (TradeData tradeData : tradeDataList) {
            tradeDataDtoList.add(tradeData.toDto());
        }
        tradeDataDtoList.forEach(e -> e.refineTradeYearMonth());
        System.out.println("tradeDataDtoList.size() = " + tradeDataDtoList.size());
        return tradeDataDtoList;
    }
}