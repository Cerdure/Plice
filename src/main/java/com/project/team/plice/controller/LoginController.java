package com.project.team.plice.controller;

import com.project.team.plice.service.interfaces.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final AdminService adminService;

    @GetMapping("/login")
    public String login(HttpServletRequest request, Authentication authentication){
        adminService.logAccess(request, authentication);
        String uri = request.getHeader("Referer");
        if (uri != null && !uri.contains("/login")) {
            request.getSession().setAttribute("prevPage", uri);
        }
        return "layout-content/login/login";
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
}
