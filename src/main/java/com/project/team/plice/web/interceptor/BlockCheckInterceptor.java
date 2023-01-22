package com.project.team.plice.web.interceptor;

import com.project.team.plice.domain.admin.Blacklist;
import com.project.team.plice.repository.admin.BlacklistRepository;
import com.project.team.plice.repository.admin.IPRepository;
import com.project.team.plice.service.interfaces.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BlockCheckInterceptor implements HandlerInterceptor {

    private final IPRepository ipRepository;
    private final BlacklistRepository blacklistRepository;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object arg2) throws Exception {
        Blacklist blacklist = blacklistRepository.findByIp(ipRepository.findByIp(req.getRemoteAddr()));
        if(blacklist != null){
            String expDate = blacklist.getExpDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
            String alertText = "'관리자에 의해 차단된 IP입니다.\\n"
                    + "차단 사유 : " + blacklist.getReason() + "\\n"
                    + "차단 기간 : " + expDate + "까지'";
            res.setContentType("text/html; charset=UTF-8");
            PrintWriter out = res.getWriter();
            out.println("<script>alert(" + alertText + ");history.go(-1);</script>");
            out.flush();
            return false;
        }
        return true;
    }
}
