package com.project.team.plice.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class LegalCode {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "legalCode_id")
    private Long id;

    private Integer legalCode;

    private String siDo;

    private String siGunGu;

    private String eupMyeonDong;

    private String dongLi;

    private LocalDateTime regDate;

    private LocalDateTime delDate;

    private Integer areaCode;

    @PrePersist
    public void prePersist() {
        this.regDate = this.regDate == null ? LocalDateTime.now() : this.regDate;
        this.areaCode = this.areaCode == null ? this.legalCode/100000 : this.areaCode;
    }

    @Builder
    public LegalCode(Long id, Integer legalCode, String siDo, String siGunGu, String eupMyeonDong, String dongLi, LocalDateTime regDate, LocalDateTime delDate, Integer areaCode) {
        this.id = id;
        this.legalCode = legalCode;
        this.siDo = siDo;
        this.siGunGu = siGunGu;
        this.eupMyeonDong = eupMyeonDong;
        this.dongLi = dongLi;
        this.regDate = regDate;
        this.delDate = delDate;
        this.areaCode = areaCode;
    }
}
