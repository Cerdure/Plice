package com.project.team.plice.controller;

import com.project.team.plice.dto.LoginDto;
import com.project.team.plice.service.LoginServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.validation.Valid;

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
