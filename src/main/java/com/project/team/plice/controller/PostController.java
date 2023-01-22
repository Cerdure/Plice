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
public class PostController {

    private final AdminService adminService;

    @GetMapping("/post")
    public String map(HttpServletRequest request, Authentication authentication) {
        adminService.logAccess(request, authentication);
        return "layout-content/post/post";
    }

    @GetMapping("/post/story-detail")
    public String storyDetail(HttpServletRequest request, Authentication authentication) {
        return "layout-content/post/story-detail";
    }

    @GetMapping("/post/notice-detail")
    public String noticeDetail(HttpServletRequest request, Authentication authentication) {
        return "layout-content/post/notice-detail";
    }
}