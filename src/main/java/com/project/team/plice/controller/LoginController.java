package com.project.team.plice.controller;

import com.project.team.plice.domain.admin.Blacklist;
import com.project.team.plice.domain.member.Member;
import com.project.team.plice.service.interfaces.AdminService;
import com.project.team.plice.service.interfaces.MemberService;
import com.project.team.plice.utils.ScriptUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final AdminService adminService;
    private final MemberService memberService;

    @GetMapping("/login")
    public String login(HttpServletRequest request, Authentication authentication){
        adminService.logAccess(request, authentication);
        String uri = request.getHeader("Referer");
        if (uri != null && !uri.contains("/login")) {
            request.getSession().setAttribute("prevPage", uri);
        }
        return "layout-content/login/login";
    }

    @GetMapping("/login/block-check")
    public String blockCheck(@RequestParam("prevPage") String prevPage, HttpServletRequest request , Authentication authentication, Model model) throws Exception{
        Member member = memberService.findMember(authentication);
        Blacklist blacklist = member.getBlacklist();
        if(blacklist != null){
            String expDate = blacklist.getExpDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
            String alertText = "관리자에 의해 차단된 아이디입니다.\\n"
                    + "차단 사유 : " + blacklist.getReason() + "\\n"
                    + "차단 기간 : " + expDate + "까지";
            model.addAttribute("alertText", alertText);
            HttpSession session = request.getSession(false);
            session.invalidate();
            SecurityContextHolder.getContext().setAuthentication(null);
            return "layout-content/login/login";
        } else {
            return "redirect:" + prevPage;
        }
    }

    @GetMapping("/login-check")
    @ResponseBody
    public Boolean logincheck(HttpServletRequest request, Authentication authentication){
        adminService.logAccess(request, authentication);
        String uri = request.getHeader("Referer");
        if (uri != null && !uri.contains("/login")) {
            request.getSession().setAttribute("prevPage", uri);
        }
        boolean result = true;
        return result;
    }

    @GetMapping("/login/pwd-find")
    @ResponseBody
    public String findPwd(@RequestParam("findPwd") String findPwd) {
        System.out.println("findPwd = " + findPwd);
        return memberService.checkPhone(findPwd);
    }

    @GetMapping("/login/update")
    public String pwUpdate(@RequestParam("phone") String phone, Model model) {
        System.out.println("phone = " + phone);
        model.addAttribute("phone", phone);
        return "layout-content/login/pw-reset";
    }

    @PostMapping("/login/pass-update")
    public String passUpdate(@RequestParam("phone") String phone, @RequestParam("pw") String pw) {
        memberService.update(phone, pw);
        return "layout-content/login/login";
    }

    @GetMapping("/login/check")
    @ResponseBody
    public String idCheck(@RequestParam("idInput") String idInput) {
        System.out.println("idInput = " + idInput);
        return memberService.checkPhone(idInput);
    }

    @GetMapping("/login/send-message")
    @ResponseBody
    public String sendMessage(@RequestParam("phone") String phone) {
        return memberService.certifiedPhoneNumber(phone);
    }
}
