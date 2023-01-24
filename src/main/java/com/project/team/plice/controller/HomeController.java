package com.project.team.plice.controller;

import com.project.team.plice.dto.utils.SearchUtils;
import com.project.team.plice.service.interfaces.AdminService;
import com.project.team.plice.service.interfaces.ChatService;
import com.project.team.plice.service.interfaces.ContentsService;
import com.project.team.plice.service.interfaces.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final AdminService adminService;
    private final PostService postService;
    private final ChatService chatService;
    private final ContentsService contentsService;

    @GetMapping(value = {"/", "/home"})
    public String home(HttpServletRequest request, Authentication authentication, Model model) {
        adminService.logAccess(request, authentication);
        SearchUtils searchParams = SearchUtils.builder()
                .keyword("아파트 매매").page(1).totalPage(4).sort("sim").build();
        model.addAttribute("chatRooms", chatService.findTop3ChatRooms());
        model.addAttribute("articles", contentsService.search(searchParams));
        model.addAttribute("notices", postService.findLastNotices());
        return "layout-content/home/home";
    }

}