package com.project.team.plice.controller;

import com.project.team.plice.dto.member.MemberDto;
import com.project.team.plice.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class JoinController {

    private final MemberServiceImpl memberService;

    @GetMapping("/sign-up")
    public String signUpForm(){
        return "sign-up";
    }

    @GetMapping("/join")
    public String joinForm(){
        return "join";
    }

    @GetMapping("/term-service")
    public String termServiceForm(){
        return "term-service";
    }

    @GetMapping("/use-personal")
    public String usePersonalForm(){
        return "use-personal";
    }

    @GetMapping("/marketing")
    public String marketingForm(){
        return "marketing";
    }

    @GetMapping("/term-of-service")
    public String termOfServiceForm(){
        return "term-of-service";
    }
}
