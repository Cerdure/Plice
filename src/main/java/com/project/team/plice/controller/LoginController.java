package com.project.team.plice.controller;

import com.project.team.plice.dto.LoginDto;
import com.project.team.plice.service.LoginServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginServiceImpl loginService;

    @GetMapping("/login")
    public String login(@Valid @ModelAttribute("loginDto") LoginDto loginDto, HttpServletRequest request, BindingResult bindingResult){
        String uri = request.getHeader("Referer");
        if (uri != null && !uri.contains("/login")) {
            request.getSession().setAttribute("prevPage", uri);
        }
        return "login";
    }

}
