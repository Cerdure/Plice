package com.project.team.plice.controller;

import com.project.team.plice.dto.contents.ArticleDto;
import com.project.team.plice.dto.utils.SearchParamUtil;
import com.project.team.plice.service.interfaces.AdminService;
import com.project.team.plice.service.interfaces.ContentsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ContentsController {

    private final AdminService adminService;
    private final ContentsService contentsService;

    @GetMapping("/contents")
    public String contents(HttpServletRequest request, Authentication authentication, Model model) {
        adminService.logAccess(request, authentication);
        SearchParamUtil searchParams = SearchParamUtil.builder()
                .keyword("아파트 매매")
                .page(1)
                .totalPage(4)
                .sort("sim")   // sim, date
                .build();
        List<ArticleDto> searchResults = contentsService.search(searchParams);
        model.addAttribute("searchResults", searchResults);
        return "layout-content/contents/contents";
    }


    @GetMapping("/contents/search")
    public String search(@ModelAttribute SearchParamUtil searchParams, Model model) throws Exception {
        searchParams.setTotalPage(4);
        searchParams.setSort("sim");
        List<ArticleDto> searchResults = contentsService.search(searchParams);
        model.addAttribute("searchResults", searchResults);
        return "layout-content/contents/contents :: #articles";
    }

}