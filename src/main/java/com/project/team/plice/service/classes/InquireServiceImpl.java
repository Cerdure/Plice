package com.project.team.plice.service.classes;

import com.project.team.plice.repository.inquire.InquireRepository;
import com.project.team.plice.service.interfaces.InquireService;
import com.project.team.plice.service.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InquireServiceImpl implements InquireService {

    private final InquireRepository inquireRepository;
    private final MemberService memberService;

}
