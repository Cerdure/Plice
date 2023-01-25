package com.project.team.plice.service.interfaces;

import com.project.team.plice.domain.admin.AccessLog;
import com.project.team.plice.domain.admin.AdminTeam;
import com.project.team.plice.domain.admin.Blacklist;
import com.project.team.plice.domain.admin.Report;
import com.project.team.plice.domain.member.Member;
import com.project.team.plice.dto.admin.BlockDto;
import com.project.team.plice.dto.inquire.AnswerDto;
import com.project.team.plice.dto.member.MemberDto;
import com.project.team.plice.dto.post.NoticeDto;
import com.project.team.plice.dto.utils.SearchUtils;
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
    public Page<AccessLog> findAllAccessLog(Pageable pageable);
    public Page<AccessLog> searchAccessLog(SearchUtils searchUtils, Pageable pageable);
    public AccessLog findLastAccess(Member member);

    public Map<LocalDate, Long> logCountByDay(int range, String page);
    public Map<LocalDate, Long> logCountByMonth(String page);
    public Map<LocalDate, Long> logCountByYear(String page);
    public Map<String, Map<LocalDate, Long>> pageCountByDay(int range);
    public Map<String, Map<LocalDate, Long>> pageCountByMonth();
    public Map<String, Map<LocalDate, Long>> pageCountByYear();
    public Map<LocalDate, Long> memberCountByDay();

    public boolean authorityCheck(Authentication authentication, String page);
    public Boolean blockCheck(BlockDto blockDto);
    public void memberBlock(Long id, int date, String reason);
    public void memberBlockCancel(Long id);
    public void ipBlock(String ip, int date, String reason);
    public void ipBlockCancel(String ip);

    public Page<Blacklist> findAllMemberBlacklist(Pageable pageable);
    public Page<Blacklist> findAllIpBlacklist(Pageable pageable);
    public Page<Blacklist> searchMemberBlacklist(SearchUtils searchUtils, Pageable pageable);
    public Page<Blacklist> searchIpBlacklist(SearchUtils searchUtils, Pageable pageable);

    public Page<Report> findAllReport(Pageable pageable);
    public Page<Report> searchReport(SearchUtils searchUtils, Pageable pageable);
    public void reportHandler(Long reportId, boolean state);

    public List<Member> findAllAdmin();
    public List<AdminTeam> findAllTeam();
    public void createAdmin(MemberDto memberDto);
    public void updateAdmin(MemberDto memberDto);
    public void deleteAdmin(Long id);
    public void createTeam(String teamName);
    public void deleteTeam(Long id);

    public void saveNotice(NoticeDto noticeDto, Authentication authentication);
    public void deleteNotice(Long id);

    public void saveAnswer(AnswerDto answerDto, Authentication authentication);
    public void modifyAnswer(AnswerDto answerDto, Authentication authentication);
}
