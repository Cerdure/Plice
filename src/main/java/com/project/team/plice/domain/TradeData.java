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

    public TradeDataDto toDto(){
        return TradeDataDto.builder()
                .id(this.id)
                .siGunGu(this.siGunGu)
                .mainNum(this.mainNum)
                .subNum(this.subNum)
                .name(this.name)
                .area(this.area)
                .tradeYearMonth(this.tradeYearMonth)
                .tradeDay(this.tradeDay)
                .price(this.price)
                .floor(this.floor)
                .buildYear(this.buildYear)
                .roadName(this.roadName)
                .tradeType(this.tradeType)
                .build();
    }
}
