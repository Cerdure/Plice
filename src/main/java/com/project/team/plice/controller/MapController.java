package com.project.team.plice.controller;

import com.project.team.plice.domain.TradeData;
import com.project.team.plice.dto.TradeDataDto;
import com.project.team.plice.service.MapServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping("/find-data/first")
    @ResponseBody
    public List<TradeDataDto> firstFindData(Model model) {
        List<TradeData> tradeDataList = mapService.tradeDataBetweenYearMonth("202211", "202212");
        System.out.println("tradeDataList.size() = " + tradeDataList.size());
        List<TradeDataDto> tradeDataDtoList = new ArrayList<>();
        for (TradeData tradeData : tradeDataList) {
            tradeDataDtoList.add(tradeData.toDto());
        }
        tradeDataDtoList.forEach(e -> e.addressResolver());
        tradeDataDtoList.forEach(e -> e.refineTradeYearMonth());
        System.out.println("tradeDataDtoList.size() = " + tradeDataDtoList.size());
        return tradeDataDtoList;
    }
}