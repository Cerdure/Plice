package com.project.team.plice.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class TradeDataDto {
    private Long id;
    private String lng;
    private String lat;
    private String address;
    private String area;
    private String buildYear;
    private String floor;
    private String mainNum;
    private String name;
    private String price;
    private String subNum;
    private String tradeDay;
    private String tradeType;
    private String tradeYearMonth;

    private String southWestLat;
    private String southWestLng;
    private String northEastLat;
    private String northEastLng;

    @Builder
    public TradeDataDto(Long id, String lng, String lat, String address, String area, String buildYear, String floor, String mainNum, String name, String price, String subNum, String tradeDay, String tradeType, String tradeYearMonth) {
        this.id = id;
        this.lng = lng;
        this.lat = lat;
        this.address = address;
        this.area = area;
        this.buildYear = buildYear;
        this.floor = floor;
        this.mainNum = mainNum;
        this.name = name;
        this.price = price;
        this.subNum = subNum;
        this.tradeDay = tradeDay;
        this.tradeType = tradeType;
        this.tradeYearMonth = tradeYearMonth;
    }
}