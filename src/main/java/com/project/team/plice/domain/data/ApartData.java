package com.project.team.plice.domain.data;

import com.project.team.plice.dto.data.ApartDataDto;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ApartData {

    @Id
    @Column(name = "apart_data_id")
    private Long id;
    private String address;
    private String name;
    private String legalCode;
    private String complexCode;

    @Builder
    public ApartData(Long id, String address, String name, String legalCode, String complexCode) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.legalCode = legalCode;
        this.complexCode = complexCode;
    }

    public ApartDataDto toDto() {
        return ApartDataDto.builder()
                .id(this.id)
                .address(this.address)
                .name(this.name)
                .legalCode(this.legalCode)
                .complexCode(this.complexCode)
                .build();
    }
}
