package com.project.team.plice.domain.admin;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchKeyword {

    @Id
    @GeneratedValue
    @Column(name = "search_keyword_id")
    private Long id;

    private String keyword;

    private Integer count;

    @PrePersist
    public void prePersist() {
        this.count = this.count == null ? 1 : this.count;
    }

    @Builder
    public SearchKeyword(Long id, String keyword, Integer count) {
        this.id = id;
        this.keyword = keyword;
        this.count = count;
    }

    public void countPlus() {
        this.count++;
    }
}
