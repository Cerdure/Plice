package com.project.team.plice.dto.contents;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class ArticleDto {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String link;
    private String description;
    private Integer totalPage;
    private Integer page;
    private String date;

    @Builder
    public ArticleDto(Long id, String title, String link, String description, Integer totalPage, Integer page, String date) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.description = description;
        this.totalPage = totalPage;
        this.page = page;
        this.date = date;
    }
}
