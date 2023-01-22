package com.project.team.plice.service.classes;

import com.project.team.plice.domain.post.Notice;
import com.project.team.plice.dto.post.NoticeDto;
import com.project.team.plice.repository.post.NoticeRepository;
import com.project.team.plice.service.interfaces.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final MemberServiceImpl memberService;

//    @Override
//    public List<Notice> findAllNotice() {
//        return noticeRepository.findAll();
//    }

    public Page<Notice> findAll(Pageable pageable){
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 12, Sort.by("id").descending());
        return noticeRepository.findAll(pageable);
    }

    @Override
    public Page<Notice> findAllNotice(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 8, Sort.by("id").descending());
        return noticeRepository.findAll(pageable);
    }

    @Override
    public Notice findNoticeById(Long id) {
        return noticeRepository.findById(id).get();
    }

    public void saveNotice(Notice notice) {
        noticeRepository.save(notice);
    }

    @Override
    public void deleteNotice(Notice notice) {
        noticeRepository.delete(notice);
    }


    @Override
    public Page<Notice> findByTitle(String title, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Notice> findByMemberNickname(String memberNickname, Pageable pageable) {
        return null;
    }

    @Override
    public Notice findPrevNotice(Notice notice) {
        LocalDateTime regDate = notice.getRegDate();
        List<Notice> findResult = noticeRepository.findByRegDateBeforeOrderByRegDateDesc(regDate);
        findResult.forEach(e -> System.out.println("e.getId() = " + e.getId()));

        if(findResult == null || findResult.size() == 0){
            return notice.builder().build();

        } else {
            return findResult.get(0);
        }
    }

    @Override
    public Notice findNextNotice(Notice notice) {
        LocalDateTime regDate = notice.getRegDate();
        List<Notice> findResult = noticeRepository.findByRegDateAfter(regDate);
        if(findResult == null || findResult.size() == 0){
            return notice.builder().build();

        } else {
            return findResult.get(0);
        }
    }

    @Override
    public Notice modify(Long id, NoticeDto NoticeDto, Authentication authentication) throws Exception {
        return null;
    }

    @Override
    public void hitsPlus(Notice notice) {
        notice.hitsPlus();
        noticeRepository.save(notice);
    }

}
