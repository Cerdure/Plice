package com.project.team.plice.service;

import com.project.team.plice.domain.TradeData;
import com.project.team.plice.repository.TradeDataRepository;
import com.project.team.plice.service.interfaces.MapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MapServiceImpl implements MapService {

    private final TradeDataRepository tradeDataRepository;

    @Override
    public List<TradeData> tradeDataBetweenYearMonth(String startYearMonth, String endYearMonth) {
        return tradeDataRepository.findByTradeYearMonthBetween(startYearMonth, String.valueOf(Integer.parseInt(endYearMonth)+1));
    }
}
