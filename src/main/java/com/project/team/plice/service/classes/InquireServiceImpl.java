package com.project.team.plice.service.classes;

import com.project.team.plice.domain.inquire.Inquire;
import com.project.team.plice.domain.member.Member;
import com.project.team.plice.dto.inquire.InquireDto;
import com.project.team.plice.repository.inquire.InquireRepository;
import com.project.team.plice.service.interfaces.InquireService;
import com.project.team.plice.service.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InquireServiceImpl implements InquireService {

    private final InquireRepository inquireRepository;
    private final MemberService memberService;

    @Transactional
    public void savePost(Authentication authentication,InquireDto inquireDto) {
        if(inquireDto.getId() == null){
            inquireDto.setMember(memberService.findByPhone(authentication.getName()));
            inquireRepository.save(inquireDto.toEntity());
        } else {
            Inquire inquire = inquireRepository.findById(inquireDto.getId()).get();
            inquire.changeInquire(inquireDto.getContent(),inquireDto.getTitle(),inquireDto.getType());
            inquire.changeIsAnswered(inquireDto.getIsAnswered());
            inquireRepository.save(inquire);
        }

    }

    @Transactional
    public List<InquireDto> getInquireList(Authentication authentication) {
        Member member = memberService.findByPhone(authentication.getName());
        List<Inquire> inquires = inquireRepository.findByMember(member);
        List<InquireDto> inquireDtoList = new ArrayList<>();

        for (Inquire inquire : inquires) {
            InquireDto inquireDto = InquireDto.builder()
                    .id(inquire.getId())
                    .title(inquire.getTitle())
                    .regDate(inquire.getRegDate())
                    .content(inquire.getContent())
                    .type(inquire.getType())
                    .build();
            inquireDtoList.add(inquireDto);

        }
        return inquireDtoList;
    }

    @Transactional
    public void delete(Long inquireId, Authentication authentication){
        Member member = memberService.findByPhone(authentication.getName());
        Inquire inquire = inquireRepository.findById(inquireId).get();
        if(inquire.getMember().getId() == member.getId()){
            inquireRepository.delete(inquire);
        }
    }
    @Transactional
    public Inquire getInquireById(Long id) {
        return inquireRepository.findById(id).get();

    }


}
