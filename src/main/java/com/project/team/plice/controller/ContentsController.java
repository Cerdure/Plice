package com.project.team.plice.controller;

import com.project.team.plice.domain.news.NewsEntity;
import com.project.team.plice.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ContentsController {

    private final SearchService searchService;

    @GetMapping("/contents")
    public String contents(Model model) {
        List<NewsEntity> search_result = searchService.search("아파트 분양");
        model.addAttribute("search_result", search_result);
        return "contents";
    }


    @GetMapping("/contents/search")
    public String search(@RequestParam("keyword") String keyword, Model model) throws Exception{
        List<NewsEntity> search_result = searchService.search(keyword);
        model.addAttribute("search_result", search_result);
        return "contents :: #news-box";
    }

}