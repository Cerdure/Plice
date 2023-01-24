package com.project.team.plice.domain.data;

import com.project.team.plice.dto.data.AddressDataDto;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class AddressData {

    @Id
    @Column(name = "address_data_id")
    private Long id;
    private String address;
    private String legalCode;

    @Builder
    public AddressData(Long id, String address, String legalCode) {
        this.id = id;
        this.address = address;
        this.legalCode = legalCode;
    }

    public AddressDataDto toDto() {
        return AddressDataDto.builder()
                .id(this.id)
                .legalCode(this.legalCode)
                .address(this.address)
                .build();
    }
}
