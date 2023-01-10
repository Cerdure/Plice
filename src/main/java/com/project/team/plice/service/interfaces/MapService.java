package com.project.team.plice.service.interfaces;

import com.project.team.plice.domain.TradeData;

import java.util.List;

public interface MapService {

    public List<TradeData> findTradeDataInBounds(String startLng, String endLng, String startLat, String endLat);

}
