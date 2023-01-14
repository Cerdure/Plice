package com.project.team.plice.controller;

import com.project.team.plice.domain.member.Member;
import com.project.team.plice.dto.member.MemberDto;
import com.project.team.plice.service.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final MemberService memberService;

    @GetMapping("/my-page")
    public String View(Authentication authentication, Model model) {
        String phone = authentication.getName();
        Member member = memberService.findByPhone(phone);
        model.addAttribute("member",member);
        return "my-page";
    }
    @PostMapping("/update")
    public String update(@ModelAttribute MemberDto memberDto, Authentication authentication){
        memberService.update(authentication, memberDto);
        return "redirect:/my-page";
    }


    @GetMapping("/inquiry")
    public String inquiry(){return "inquiry";}
    @GetMapping("/inquiry_write")
    public String inquiry_write(){return "inquiry_write";}
    @GetMapping("/watchlist")
    public String watchlist(){return "watchlist";}
}