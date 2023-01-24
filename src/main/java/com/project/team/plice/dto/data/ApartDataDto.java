package com.project.team.plice.dto.data;

import com.project.team.plice.domain.data.ApartData;
import lombok.Builder;
import lombok.Data;

@Data
public class ApartDataDto {

    private Long id;
    private String address;
    private String name;
    private String highlightName;
    private String legalCode;
    private String complexCode;

    @Builder
    public ApartDataDto(Long id, String address, String name, String legalCode, String complexCode) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.legalCode = legalCode;
        this.complexCode = complexCode;
    }

    public ApartData toEntity() {
        return ApartData.builder()
                .id(this.id)
                .address(this.address)
                .name(this.name)
                .legalCode(this.legalCode)
                .complexCode(this.complexCode)
                .build();
    }

    public void coincidenceHighlight(String inputVal) {
        inputVal = inputVal.toUpperCase();
        int startIndex = this.name.toUpperCase().indexOf(inputVal);
        int endIndex = startIndex + inputVal.length();
        String coincidenceStr = "<strong>" + this.name.substring(startIndex, endIndex) + "</strong>";
        String prevStr = this.name.substring(0, startIndex);
        String nextStr = this.name.substring(endIndex);
        this.highlightName = prevStr + coincidenceStr + nextStr;
    }
}
