package com.project.team.plice.domain.news;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor
@Data
@Entity
public class NewsEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String link;
    private String description;

    public NewsEntity(String title, String link, String description){
        this.title = title;
        this.link = link;
        this.description = description;
    }
}
