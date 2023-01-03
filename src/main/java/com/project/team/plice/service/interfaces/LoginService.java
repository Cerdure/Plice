package com.project.team.plice.service.interfaces;

import com.project.team.plice.domain.member.Member;

public interface LoginService {
    public Member login(String phone, String pw);
}
