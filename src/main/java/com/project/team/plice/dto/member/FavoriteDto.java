package com.project.team.plice.dto.member;

import com.project.team.plice.domain.data.ApartData;
import com.project.team.plice.domain.member.Favorite;
import com.project.team.plice.domain.member.Member;
import lombok.*;

@Data
public class FavoriteDto {

    private Long id;
    private Member member;
    private ApartData apartData;

    @Builder
    public FavoriteDto(Long id, Member member, ApartData apartData) {
        this.id = id;
        this.member = member;
        this.apartData = apartData;
    }

    public Favorite toEntity(){
        return Favorite.builder()
                .id(this.id)
                .member(this.member)
                .apartData(this.apartData)
                .build();
    }
}
