package com.project.team.plice.dto.member;

import com.project.team.plice.domain.Apart;
import com.project.team.plice.domain.inquire.Inquire;
import com.project.team.plice.domain.member.Favorite;
import com.project.team.plice.domain.member.Member;
import com.project.team.plice.dto.ApartDto;
import lombok.*;

import javax.persistence.*;

@Data
public class FavoriteDto {

    private Long id;
    private Member member;
    private Apart apart;

    @Builder
    public FavoriteDto(Long id, Member member, Apart apart) {
        this.id = id;
        this.member = member;
        this.apart = apart;
    }

    public Favorite toEntity(){
        return Favorite.builder()
                .id(this.id)
                .member(this.member)
                .apart(this.apart)
                .build();
    }
}
