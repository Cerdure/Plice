package com.project.team.plice.service.interfaces;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface AdminService {
    public void logAccess(HttpServletRequest request, Authentication authentication);
}
