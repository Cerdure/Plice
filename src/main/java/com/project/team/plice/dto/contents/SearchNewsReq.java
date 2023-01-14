package com.project.team.plice.dto.contents;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Data
@NoArgsConstructor
public class SearchNewsReq {
    private String query;
    private Integer display;
    private Integer start;
    private String sort;

    @Builder
    public SearchNewsReq(String query, Integer display, Integer start, String sort) {
        this.query = query;
        this.display = display;
        this.start = start;
        this.sort = sort;
    }

    public MultiValueMap<String, String> toMultiValueMap(){
        var map = new LinkedMultiValueMap<String, String>();
        map.add("query", query);
        map.add("display", String.valueOf(display));
        map.add("start", String.valueOf(start));
        map.add("sort", sort);
        return map;
    }
}
