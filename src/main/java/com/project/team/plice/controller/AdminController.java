package com.project.team.plice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminController {

//    private final AdminServiceImpl adminService;

    @GetMapping("/admin")
    public String login(){
        return "admin";
    }

}
