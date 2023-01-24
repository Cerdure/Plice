package com.project.team.plice.dto.utils;

import lombok.Builder;
import lombok.Data;

@Data
public class SearchUtils {
    private String keyword;
    private String searchBy;
    private Integer page;
    private Integer totalPage;
    private String sort;

    @Builder
    public SearchUtils(String keyword, String searchBy, Integer page, Integer totalPage, String sort) {
        this.keyword = keyword;
        this.searchBy = searchBy;
        this.page = page;
        this.totalPage = totalPage;
        this.sort = sort;
    }
}
