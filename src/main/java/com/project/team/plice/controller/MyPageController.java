package com.project.team.plice.controller;

import com.project.team.plice.domain.member.Member;
import com.project.team.plice.dto.member.MemberDto;
import com.project.team.plice.service.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.project.team.plice.service.interfaces.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final AdminService adminService;
    private final MemberService memberService;

    @GetMapping("/my-page")
    public String map(HttpServletRequest request, Authentication authentication, Model model) {
        adminService.logAccess(request, authentication);
        String phone = authentication.getName();
        Member member = memberService.findByPhone(phone);
        model.addAttribute("member", member);

        return "layout-content/my-page/my-page";
    }

    @PostMapping("/my-page")
    public String update(@ModelAttribute MemberDto memberDto, Authentication authentication){
        memberService.update(authentication, memberDto);
        return "redirect:/my-page";
    }

    @GetMapping("/withdrawal")
    public String delete(Authentication authentication) {
        memberService.delete(authentication);
        return "layout-content/my-page/withdrawal";

    }

    @GetMapping("/inquiry")
    public String inquiry(HttpServletRequest request, Authentication authentication){
        adminService.logAccess(request, authentication);
        return "layout-content/my-page/inquiry";
    }

    @GetMapping("/inquiry_write")
    public String inquiry_write(){return "layout-content/my-page/inquiry_write";}

    @GetMapping("/watchlist")
    public String watchlist(HttpServletRequest request, Authentication authentication){
        adminService.logAccess(request, authentication);
        return "layout-content/my-page/watchlist";
    }
}