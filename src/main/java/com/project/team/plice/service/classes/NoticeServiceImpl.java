package com.project.team.plice.service.classes;

import com.project.team.plice.repository.post.NoticeRepository;
import com.project.team.plice.service.interfaces.MemberService;
import com.project.team.plice.service.interfaces.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final MemberService memberService;

}
