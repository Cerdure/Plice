package com.project.team.plice.dto.utils;

import com.project.team.plice.domain.data.TradeData;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DataUtil {
    private Integer tradeCount;
    private TradeData tradeMax;
    private TradeData tradeMin;
}
