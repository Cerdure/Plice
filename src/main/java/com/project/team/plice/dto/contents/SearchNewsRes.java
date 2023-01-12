package com.project.team.plice.dto.contents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchNewsRes {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<SearchLocalItem> items;

    @Data
    public static class SearchLocalItem{
        private String title;
        private String link;
        private String description;
        private String bloggername;
        private String bloogerLink;
    }
}
