package com.project.team.plice.controller;

import com.project.team.plice.dto.member.MemberDto;
import com.project.team.plice.service.interfaces.AdminService;
import com.project.team.plice.service.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class JoinController {

    private final AdminService adminService;
    private final MemberService memberService;

    @GetMapping("/sign-up")
    public String signUpForm(HttpServletRequest request, Authentication authentication){
        adminService.logAccess(request, authentication);
        return "sign-up";
    }

    @GetMapping("/join")
    public String joinForm(HttpServletRequest request, Authentication authentication){
        adminService.logAccess(request, authentication);
        return "join";
    }

    @GetMapping("/join-success")
    public String joinSuccess(HttpServletRequest request, Authentication authentication) {
        adminService.logAccess(request, authentication);
        return "join-success";
    }

    @PostMapping("/join")
    public String joinMember(@ModelAttribute MemberDto memberDto){
        memberService.join(memberDto);
        return "redirect:/login";
    }

    @GetMapping("/term-service")
    public String termServiceForm(HttpServletRequest request, Authentication authentication){
        adminService.logAccess(request, authentication);
        return "term-service";
    }

    @GetMapping("/use-personal")
    public String usePersonalForm(HttpServletRequest request, Authentication authentication){
        adminService.logAccess(request, authentication);
        return "use-personal";
    }

    @GetMapping("/marketing")
    public String marketingForm(HttpServletRequest request, Authentication authentication){
        adminService.logAccess(request, authentication);
        return "marketing";
    }

    @GetMapping("/term-of-service")
    public String termOfServiceForm(HttpServletRequest request, Authentication authentication){
        adminService.logAccess(request, authentication);
        return "term-of-service";
    }
}
