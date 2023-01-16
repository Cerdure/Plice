package com.project.team.plice.controller;

import com.project.team.plice.domain.enums.MemberRole;
import com.project.team.plice.domain.member.Member;
import com.project.team.plice.dto.member.MemberDto;
import com.project.team.plice.service.interfaces.AdminService;
import com.project.team.plice.service.interfaces.ChatService;
import com.project.team.plice.service.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final MemberService memberService;
    private final ChatService chatService;
//    private final PostService postService;

    @GetMapping(value = {"/admin", "/admin/site-chart"})
    public String siteChart(){
        return "layout-content/admin/site-chart";
    }

    @GetMapping("/admin/admin-mng")
    public String adminMng(){
        return "layout-content/admin/admin-mng";
    }

    @GetMapping("/admin/member-mng")
    public String memberMng(Model model){
        List<Member> members = memberService.findByRole(MemberRole.USER);
        model.addAttribute("members", members);
        return "layout-content/admin/member-mng";
    }

    @GetMapping("/admin/member-mod")
    public String memberMod(@ModelAttribute MemberDto memberDto, Model model){
        memberService.update(memberDto);
        return "redirect:/admin/member-mng";
    }

    @GetMapping("/admin/member-del")
    public String membeDel(Model model){
        List<Member> members = memberService.findByRole(MemberRole.USER);
        model.addAttribute("members", members);
        return "layout-content/admin/member-mng";
    }


    @GetMapping("/admin/chat-mng")
    public String chatMng(){
        return "layout-content/admin/chat-mng";
    }

    @GetMapping("/admin/post-mng")
    public String postMng(){
        return "layout-content/admin/post-mng";
    }
}
