package com.project.team.plice.controller;

import com.project.team.plice.dto.Params;
import com.project.team.plice.dto.TradeData;
import com.project.team.plice.service.MapServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MapController {

    private final MapServiceImpl mapService;

    @GetMapping("/map")
    public String map() {
        return "map";
    }

    @PostMapping("/map/trade-search")
    @ResponseBody
    public List<TradeData> callAPI(@ModelAttribute Params params) throws Exception {
        log.info(params.toString());
        return mapService.tradeDataSearch(params);
    }
}