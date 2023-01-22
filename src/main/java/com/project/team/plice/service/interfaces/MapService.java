package com.project.team.plice.service.interfaces;

import com.project.team.plice.domain.admin.SearchKeyword;
import com.project.team.plice.domain.data.TradeData;
import com.project.team.plice.dto.data.AddressDataDto;
import com.project.team.plice.dto.data.ApartDataDto;

import java.util.List;

public interface MapService {

    public List<TradeData> findTradeData();
    public List<TradeData> findTradeData(Double neLng, Double neLat, Double swLng, Double swLat);
    public List<TradeData> findAllTradeDataOrderByPriceDesc();
    public List<TradeData> findAllTradeDataOrderByPriceAsc();
    public TradeData findTradeDataByAddress(String address);
    public List<AddressDataDto> findAddressDataByAddress(String val);
    public List<ApartDataDto> findApartByAddressOrName(String address, String name);
    public void saveSearchKeyword(String searchKeyword);
    public List<SearchKeyword> searchKeywordTop10();
}
