package com.project.team.plice.dto.utils;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SearchParamUtil {
    private String keyword;
    private Integer page;
    private Integer totalPage;
    private String sort;

    @Builder
    public SearchParamUtil(String keyword, Integer page, Integer totalPage, String sort) {
        this.keyword = keyword;
        this.page = page;
        this.totalPage = totalPage;
        this.sort = sort;
    }
}
