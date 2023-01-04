package com.project.team.plice.domain;


import com.project.team.plice.dto.TradeDataDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class TradeData {

    @Id
    @Column(name = "tradeData_id")
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



    public TradeDataDto toDto(){
        return TradeDataDto.builder()
                .id(this.id)
                .lng(this.lng)
                .lat(this.lat)
                .address(this.address)
                .area(this.area)
                .buildYear(this.buildYear)
                .floor(this.floor)
                .mainNum(this.mainNum)
                .name(this.name)
                .price(this.price)
                .subNum(this.subNum)
                .tradeDay(this.tradeDay)
                .tradeType(this.tradeType)
                .tradeYearMonth(this.tradeYearMonth)
                .build();
    }
}
