package com.project.team.plice.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class TradeDataDto {
    private Long id;
    private String siGunGu;
    private String mainNum;
    private String subNum;
    private String name;
    private String area;
    private String tradeYearMonth;
    private String tradeDay;
    private String price;
    private String floor;
    private String buildYear;
    private String roadName;
    private String tradeType;
    private String address;

    @Builder
    public TradeDataDto(Long id, String siGunGu, String mainNum, String subNum, String name, String area, String tradeYearMonth, String tradeDay, String price, String floor, String buildYear, String roadName, String tradeType, String address) {
        this.id = id;
        this.siGunGu = siGunGu;
        this.mainNum = mainNum;
        this.subNum = subNum;
        this.name = name;
        this.area = area;
        this.tradeYearMonth = tradeYearMonth;
        this.tradeDay = tradeDay;
        this.price = price;
        this.floor = floor;
        this.buildYear = buildYear;
        this.roadName = roadName;
        this.tradeType = tradeType;
        this.address = address;
    }

    public void addressResolver(){
        this.address = this.siGunGu.substring(0, this.siGunGu.lastIndexOf(" ")) + " " + this.roadName;
    }
    public void refineTradeYearMonth(){
        this.tradeYearMonth = this.tradeYearMonth.substring(0,6);
    }
}