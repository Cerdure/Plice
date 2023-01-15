package com.project.team.plice.service.classes;

import com.project.team.plice.domain.admin.AccessLog;
import com.project.team.plice.domain.member.Member;
import com.project.team.plice.repository.admin.AccessLogRepository;
import com.project.team.plice.service.interfaces.AdminService;
import com.project.team.plice.service.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AccessLogRepository accessLogRepository;
    private final MemberService memberService;

    @Override
    public void logAccess(HttpServletRequest request, Authentication authentication) {
        if(authentication != null){
            accessLogRepository.save(
                    AccessLog.builder()
                            .member(memberService.findByPhone(authentication.getName()))
                            .uri(request.getRequestURI())
                            .ip(request.getRemoteAddr())
                            .build()
            );
        }
    }
}
