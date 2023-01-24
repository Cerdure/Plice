package com.project.team.plice.dto.data;

import com.project.team.plice.domain.data.AddressData;
import lombok.Builder;
import lombok.Data;

@Data
public class AddressDataDto {

    private Long id;
    private String legalCode;
    private String address;
    private String highlightAddress;

    @Builder
    public AddressDataDto(Long id, String legalCode, String address) {
        this.id = id;
        this.legalCode = legalCode;
        this.address = address;
    }

    public AddressData toEntity() {
        return AddressData.builder()
                .id(this.id)
                .legalCode(this.legalCode)
                .address(this.address)
                .build();
    }

    public void coincidenceHighlight(String inputVal) {
        int startIndex = this.address.indexOf(inputVal);
        int endIndex = startIndex + inputVal.length();
        String coincidenceStr = "<strong>" + this.address.substring(startIndex, endIndex) + "</strong>";
        String prevStr = this.address.substring(0, startIndex);
        String nextStr = this.address.substring(endIndex);
        this.highlightAddress = prevStr + coincidenceStr + nextStr;
    }
}
