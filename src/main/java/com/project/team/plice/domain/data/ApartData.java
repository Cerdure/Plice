package com.project.team.plice.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Apart {

    @Id
    @Column(name = "apart_id")
    private Long id;
    private String address;
    private String name;
    private String legalCode;
    private String complexCode;

    @Builder
    public Apart(Long id, String address, String name, String legalCode, String complexCode) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.legalCode = legalCode;
        this.complexCode = complexCode;
    }
}
