package com.project.team.plice.service.classes;

import com.project.team.plice.domain.data.ApartData;
import com.project.team.plice.domain.enums.MemberRole;
import com.project.team.plice.domain.member.Favorite;
import com.project.team.plice.domain.member.Member;
import com.project.team.plice.domain.post.Post;
import com.project.team.plice.domain.post.Reply;
import com.project.team.plice.dto.data.ApartDataDto;
import com.project.team.plice.dto.post.ReplyDto;
import com.project.team.plice.repository.data.ApartDataRepository;
import com.project.team.plice.repository.member.FavoriteRepository;
import com.project.team.plice.repository.member.MemberRepository;
import com.project.team.plice.repository.post.PostRepository;
import com.project.team.plice.repository.post.ReplyRepository;
import com.project.team.plice.service.interfaces.FavoriteService;
import com.project.team.plice.service.interfaces.MemberService;
import com.project.team.plice.service.interfaces.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final ApartDataRepository apartDataRepository;
    private final MemberService memberService;

    @Override
    public void favoriteSave(String apartName, Authentication authentication) {
        ApartData apart = apartDataRepository.findByNameContains(apartName);
        favoriteRepository.save(Favorite.builder().apartData(apart).member(memberService.findMember(authentication)).build());
    }


}
