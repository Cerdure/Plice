package com.project.team.plice.service.classes;

import com.project.team.plice.domain.inquire.Inquire;
import com.project.team.plice.domain.member.Member;
import com.project.team.plice.dto.inquire.InquireDto;
import com.project.team.plice.dto.utils.SearchUtils;
import com.project.team.plice.repository.inquire.InquireRepository;
import com.project.team.plice.service.interfaces.InquireService;
import com.project.team.plice.service.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class InquireServiceImpl implements InquireService {

    private final InquireRepository inquireRepository;
    private final MemberService memberService;

    @Transactional
    public void savePost(Authentication authentication, InquireDto inquireDto) {
        if (inquireDto.getId() == null) {
            inquireDto.setMember(memberService.findByPhone(authentication.getName()));
            inquireRepository.save(inquireDto.toEntity());
        } else {
            Inquire inquire = inquireRepository.findById(inquireDto.getId()).get();
            inquire.changeInquire(inquireDto.getContent(), inquireDto.getTitle(), inquireDto.getType());
            inquire.changeIsAnswered(inquireDto.getIsAnswered());
            inquireRepository.save(inquire);
        }
    }

    @Transactional
    public Page<Inquire> getInquireList(Authentication authentication, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 8, Sort.by("id").descending());
        return inquireRepository.findByMember(memberService.findByPhone(authentication.getName()), pageable);
    }

    @Transactional
    public void delete(Long inquireId, Authentication authentication) {
        Member member = memberService.findByPhone(authentication.getName());
        Inquire inquire = inquireRepository.findById(inquireId).get();
        if (inquire.getMember().getId() == member.getId()) {
            inquireRepository.delete(inquire);
        }
    }

    @Transactional
    public Inquire getInquireById(Long id) {
        return inquireRepository.findById(id).get();

    }

    @Override
    public Page<Inquire> findAllInquire(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 12, Sort.by("id").descending());
        return inquireRepository.findAll(pageable);
    }

    @Override
    public Inquire findInquire(Long id) {
        return inquireRepository.findById(id).get();
    }

    @Override
    public Page<Inquire> searchInquire(SearchUtils searchUtils, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 12, Sort.by("id").descending());
        String keyword = searchUtils.getKeyword();
        switch (searchUtils.getSearchBy()) {
            case "id":
                return inquireRepository
                        .findById(Long.parseLong(keyword), pageable);
            case "type":
                return inquireRepository
                        .findByTypeContainsIgnoreCase(keyword, pageable);
            case "title":
                return inquireRepository
                        .findByTitleContainsIgnoreCase(keyword, pageable);
            case "memberId":
                return inquireRepository.findByMemberId(Long.parseLong(keyword), pageable);
        }
        throw new NoSuchElementException("일치하는 검색 유형이 없습니다.");
    }


}
