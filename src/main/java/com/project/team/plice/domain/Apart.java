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

    private String siDo;

    private String siGunGu;

    private String dongEupMyeon;

    private String li;

    private String name;

    private Integer legalCode;

    private Integer complexCode;

    @Builder
    public Apart(Long id, String siDo, String siGunGu, String dongEupMyeon, String li, String name, Integer legalCode, Integer complexCode) {
        this.id = id;
        this.siDo = siDo;
        this.siGunGu = siGunGu;
        this.dongEupMyeon = dongEupMyeon;
        this.li = li;
        this.name = name;
        this.legalCode = legalCode;
        this.complexCode = complexCode;
    }
}
