package com.project.team.plice.controller;

import com.project.team.plice.domain.inquire.Inquire;
import com.project.team.plice.domain.member.Member;
import com.project.team.plice.dto.inquire.InquireDto;
import com.project.team.plice.dto.member.MemberDto;
import com.project.team.plice.service.interfaces.AdminService;
import com.project.team.plice.service.interfaces.InquireService;
import com.project.team.plice.service.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final AdminService adminService;
    private final MemberService memberService;
    private final InquireService inquireService;

    @GetMapping("/my-page")
    public String map(HttpServletRequest request, Authentication authentication, Model model) {
        adminService.logAccess(request, authentication);
        String phone = authentication.getName();
        Member member = memberService.findByPhone(phone);
        model.addAttribute("member", member);

        return "layout-content/my-page/my-page";
    }

    @PostMapping("/my-page")
    @ResponseBody
    public Boolean update(@ModelAttribute MemberDto memberDto, Authentication authentication) {
        memberService.update(authentication, memberDto);
        return true;
    }

    @GetMapping("/leave")
    @ResponseBody
    public Boolean delete(Authentication authentication) {
        memberService.delete(authentication);
        return true;
    }

    @GetMapping("/leave/after")
    public String delete(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.invalidate();
        SecurityContextHolder.getContext().setAuthentication(null);
        return "redirect:/home";
    }

    @GetMapping("/inquiry")
    public String inquiry(Authentication authentication, Model model, Pageable pageable) {
        Page<Inquire> inquires = inquireService.getInquireList(authentication, pageable);
        model.addAttribute("inquiries", inquires);
        return "layout-content/my-page/inquiry";
    }

    @GetMapping("/delete")
    public String inquiry_delete(@RequestParam("inquireId") Long inquireId, Authentication authentication) {
        inquireService.delete(inquireId, authentication);
        return "redirect:/inquiry";
    }

    @GetMapping("/inquiry_write")
    public String inquiry_id(@RequestParam("Id") Long id, Model model) {
        if (id != 0) {
            model.addAttribute("inquire", inquireService.getInquireById(id));
        }
        return "layout-content/my-page/inquiry_write";
    }

    @PostMapping("/inquiry_write")
    public String write(@ModelAttribute InquireDto inquireDto, Authentication authentication) {
        inquireService.savePost(authentication, inquireDto);
        return "redirect:/inquiry";
    }

    @GetMapping("/watchlist")
    public String watchlist(HttpServletRequest request, Authentication authentication) {
        adminService.logAccess(request, authentication);
        return "layout-content/my-page/watchlist";
    }

}