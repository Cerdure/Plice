package com.project.team.plice.domain.data;


import com.project.team.plice.dto.data.TradeDataDto;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class TradeData {

    @Id
    @Column(name = "trade_data_id")
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

    @Builder
    public TradeData(Long id, double lng, double lat, String address, float area, Integer buildYear, Integer floor, Integer mainNum, String name, Integer price, Integer subNum, String tradeDay, String tradeType, String tradeYearMonth) {
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

    public TradeDataDto toDto() {
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
