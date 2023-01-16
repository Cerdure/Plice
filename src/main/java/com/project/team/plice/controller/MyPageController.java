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
public class MyPageController {

    private final AdminService adminService;

    @GetMapping("/my-page")
    public String map(HttpServletRequest request, Authentication authentication) {
        adminService.logAccess(request, authentication);
        return "layout-content/my-page/my-page";
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