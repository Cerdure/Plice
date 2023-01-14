package com.project.team.plice.dto;

import com.project.team.plice.domain.data.TradeData;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DataUtils {
    private Integer tradeCount;
    private TradeData tradeMax;
    private TradeData tradeMin;
}
