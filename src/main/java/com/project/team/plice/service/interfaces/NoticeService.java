package com.project.team.plice.service.interfaces;

import com.project.team.plice.domain.post.Notice;
import com.project.team.plice.dto.post.NoticeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface NoticeService {

//    public List<Notice> findAllNotice();

    public Page<Notice> findAll(Pageable pageable);

    public Page<Notice> findAllNotice(Pageable pageable);
    public Notice findNoticeById(Long Id);

    public void saveNotice(Notice notice);

    public void deleteNotice(Notice notice);


    public Page <Notice> findByTitle(String title, Pageable pageable);
    //    public Page <Notice> findById(Long id, Pageable pageable);
    public Page<Notice> findByMemberNickname(String memberNickname, Pageable pageable);
    public Notice findPrevNotice(Notice notice);
    public Notice findNextNotice(Notice notice);
    public Notice modify(Long id, NoticeDto NoticeDto, Authentication authentication) throws Exception;
    public void hitsPlus(Notice notice);

}
