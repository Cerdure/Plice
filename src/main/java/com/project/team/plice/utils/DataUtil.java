package com.project.team.plice.utils;


import lombok.Data;

import java.time.LocalDate;

@Data
public class DataUtil {
    private String name;
    private String sortOrder;
    private Integer categoryId;
    private Integer searchBy;
    private String input;
    private Long memberId;
    private LocalDate now;
    private LocalDate oneMonthAgo;
    private LocalDate threeMonthAgo;
    private LocalDate sixMonthAgo;
    private LocalDate oneYearAgo;

    public DataUtil() {
        this.now = LocalDate.now();
        this.oneMonthAgo = LocalDate.now().minusMonths(1);
        this.threeMonthAgo = LocalDate.now().minusMonths(3);
        this.sixMonthAgo = LocalDate.now().minusMonths(6);
        this.oneYearAgo = LocalDate.now().minusYears(1);
    }
}
