package com.project.team.plice.dto.data;

import lombok.Builder;
import lombok.Data;

@Data
public class TradeDataDto {
    private Long id;
    private double lng;
    private double lat;
    private String address;
    private float area;
    private Integer buildYear;
    private Integer floor;
    private Integer mainNum;
    private String name;
    private Integer price;
    private Integer subNum;
    private String tradeDay;
    private String tradeType;
    private String tradeYearMonth;
    private String southWestLat;
    private String southWestLng;
    private String northEastLat;
    private String northEastLng;

    @Builder
    public TradeDataDto(Long id, double lng, double lat, String address, float area, Integer buildYear, Integer floor, Integer mainNum, String name, Integer price, Integer subNum, String tradeDay, String tradeType, String tradeYearMonth, String southWestLat, String southWestLng, String northEastLat, String northEastLng) {
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
        this.southWestLat = southWestLat;
        this.southWestLng = southWestLng;
        this.northEastLat = northEastLat;
        this.northEastLng = northEastLng;
    }
}