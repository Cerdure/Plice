package com.project.team.plice.dto;

import com.project.team.plice.domain.Apart;
import lombok.Builder;
import lombok.Data;

@Data
public class ApartDto {

    private Long id;
    private String address;
    private String name;
    private String legalCode;
    private String complexCode;

    @Builder
    public ApartDto(Long id, String address, String name, String legalCode, String complexCode) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.legalCode = legalCode;
        this.complexCode = complexCode;
    }

    public Apart toEntity(){
        return Apart.builder()
                .id(this.id)
                .address(this.address)
                .name(this.name)
                .legalCode(this.legalCode)
                .complexCode(this.complexCode)
                .build();
    }
}
