package com.project.team.plice.service.interfaces;

import com.project.team.plice.domain.admin.AccessLog;
import com.project.team.plice.domain.admin.Blacklist;
import com.project.team.plice.domain.admin.Report;
import com.project.team.plice.domain.admin.SearchKeyword;
import com.project.team.plice.dto.admin.BlockDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AdminService {
    public void logAccess(HttpServletRequest request, Authentication authentication);
    public void initLogAccess();
    public Map<LocalDate, Long> logCountByDay(int range, String page);
    public Map<LocalDate, Long> logCountByMonth(String page);
    public Map<LocalDate, Long> logCountByYear(String page);
    public Map<String, Map<LocalDate, Long>> pageCountByDay(int range);
    public Map<String, Map<LocalDate, Long>> pageCountByMonth();
    public Map<String, Map<LocalDate, Long>> pageCountByYear();
    public Map<LocalDate, Long> memberCountByDay();
    public Boolean blockCheck(BlockDto blockDto);
    public void memberBlock(Long id, int date, String reason);
    public void memberBlockCancel(Long id);
    public void ipBlock(String ip, int date, String reason);
    public void ipBlockCancel(String ip);
    public Page<Blacklist> findAllMemberBlacklist(Pageable pageable);
    public Page<Blacklist> findAllIpBlacklist(Pageable pageable);
    public Page<AccessLog> findAllAccessLog(Pageable pageable);
    public Blacklist findBlacklistByIp(String ip);
    public Page<Report> findAllReport(Pageable pageable);
    public void reportHandler(Long reportId, boolean state);
}
