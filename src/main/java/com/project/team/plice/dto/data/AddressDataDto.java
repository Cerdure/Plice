package com.project.team.plice.dto;

import com.project.team.plice.domain.LegalCode;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LegalCodeDto {

    private Long id;
    private Integer legalCode;
    private String siDo;
    private String siGunGu;
    private String eupMyeonDong;
    private String dongLi;
    private LocalDateTime regDate;
    private LocalDateTime delDate;
    private Integer areaCode;

    @Builder
    public LegalCodeDto(Long id, Integer legalCode, String siDo, String siGunGu, String eupMyeonDong, String dongLi, LocalDateTime regDate, LocalDateTime delDate, Integer areaCode) {
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

    public LegalCode toEntity(){
        return LegalCode.builder()
                .id(this.id)
                .legalCode(this.legalCode)
                .siDo(this.siDo)
                .siGunGu(this.siGunGu)
                .eupMyeonDong(this.eupMyeonDong)
                .dongLi(this.dongLi)
                .regDate(this.regDate)
                .delDate(this.delDate)
                .areaCode(this.areaCode)
                .build();
    }
}
