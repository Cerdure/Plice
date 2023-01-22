package com.project.team.plice.controller;

import com.project.team.plice.domain.member.Member;
import com.project.team.plice.dto.inquire.InquireDto;
import com.project.team.plice.dto.member.MemberDto;
import com.project.team.plice.service.interfaces.FavoriteService;
import com.project.team.plice.service.interfaces.InquireService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final AdminService adminService;
    private final MemberService memberService;
    private final InquireService inquireService;
    private final FavoriteService favoriteService;

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
    public String inquiry(Authentication authentication,Model model){
        List<InquireDto> inquireDtoList = inquireService.getInquireList(authentication);
        model.addAttribute("inquireList", inquireDtoList);
        return "layout-content/my-page/inquiry";
    }
    @GetMapping("/delete")
    public String inquiry_delete(@RequestParam("inquireId") Long inquireId, Authentication authentication){
        inquireService.delete(inquireId, authentication);
        return "redirect:/inquiry";
    }

    @GetMapping("/inquiry_write")
    public String inquiry_id(@RequestParam("Id") Long id, Model model){
        if(id != 0){
            model.addAttribute("inquire",inquireService.getInquireById(id));
        }
        return "layout-content/my-page/inquiry_write";
    }
    @PostMapping("/inquiry_write")
    public String write(@ModelAttribute InquireDto inquireDto, Authentication authentication){
        inquireService.savePost(authentication,inquireDto);
        return "redirect:/inquiry";
    }

    @GetMapping("/watchlist")
    public String watchlist(HttpServletRequest request, Authentication authentication){
        adminService.logAccess(request, authentication);
        return "layout-content/my-page/watchlist";
    }

}