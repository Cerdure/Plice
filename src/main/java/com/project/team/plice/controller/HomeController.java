package com.project.team.plice.controller;

import com.project.team.plice.service.interfaces.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final AdminService adminService;

    @GetMapping(value = {"/", "/home"})
    public String home(HttpServletRequest request, Authentication authentication) {
        adminService.logAccess(request, authentication);
        return "layout-content/home/home";
    }

}