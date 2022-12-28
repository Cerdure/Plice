package com.project.team.plice.dto;

import com.project.team.plice.domain.Apart;
import lombok.Builder;
import lombok.Data;

@Data
public class ApartDto {

    private Long id;
    private String siDo;
    private String siGunGu;
    private String dongEupMyeon;
    private String li;
    private String name;
    private Integer legalCode;
    private Integer complexCode;

    @Builder
    public ApartDto(Long id, String siDo, String siGunGu, String dongEupMyeon, String li, String name, Integer legalCode, Integer complexCode) {
        this.id = id;
        this.siDo = siDo;
        this.siGunGu = siGunGu;
        this.dongEupMyeon = dongEupMyeon;
        this.li = li;
        this.name = name;
        this.legalCode = legalCode;
        this.complexCode = complexCode;
    }

    public Apart toEntity(){
        return Apart.builder()
                .id(this.id)
                .siDo(this.siDo)
                .siGunGu(this.siGunGu)
                .dongEupMyeon(this.dongEupMyeon)
                .li(this.li)
                .name(this.name)
                .legalCode(this.legalCode)
                .complexCode(this.complexCode)
                .build();
    }
}
