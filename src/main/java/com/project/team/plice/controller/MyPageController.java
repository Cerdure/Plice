package com.project.team.plice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MyPageController {

    @GetMapping("/my-page")
    public String map() {
        return "my-page";
    }

    @GetMapping("/inquiry")
    public String inquiry(){return "inquiry";}
    @GetMapping("/inquiry_write")
    public String inquiry_write(){return "inquiry_write";}
    @GetMapping("/watchlist")
    public String watchlist(){return "watchlist";}
}