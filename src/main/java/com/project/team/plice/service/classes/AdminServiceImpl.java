package com.project.team.plice.service.classes;

import com.project.team.plice.domain.admin.*;
import com.project.team.plice.domain.enums.MemberRole;
import com.project.team.plice.domain.member.Member;
import com.project.team.plice.domain.post.Notice;
import com.project.team.plice.domain.post.Post;
import com.project.team.plice.dto.admin.BlockDto;
import com.project.team.plice.dto.member.MemberDto;
import com.project.team.plice.dto.post.NoticeDto;
import com.project.team.plice.repository.admin.*;
import com.project.team.plice.repository.member.MemberRepository;
import com.project.team.plice.repository.post.NoticeRepository;
import com.project.team.plice.repository.post.PostRepository;
import com.project.team.plice.service.interfaces.AdminService;
import com.project.team.plice.service.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final AccessLogRepository accessLogRepository;
    private final BlacklistRepository blacklistRepository;
    private final IPRepository ipRepository;
    private final ReportRepository reportRepository;
    private final AdminTeamRepository adminTeamRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final PostRepository postRepository;
    private final NoticeRepository noticeRepository;

    @Override
    public void logAccess(HttpServletRequest request, Authentication authentication) {
        if(authentication != null){
            String reqIp = request.getRemoteAddr();
            if(ipRepository.findByIp(reqIp) == null){
                ipRepository.save(IP.builder().ip(reqIp).build());
            }
            IP ip = ipRepository.findByIp(reqIp);
            accessLogRepository.save(
                    AccessLog.builder()
                            .member(memberService.findByPhone(authentication.getName()))
                            .uri(request.getRequestURI())
                            .ip(ip)
                            .build()
            );
        }
    }

    @Override
    public void initLogAccess() {
        List<AccessLog> logs = accessLogRepository.findByRegDateAfter(LocalDateTime.now().minusHours(23));
        if(logs.size() < 10){
            IP ip = ipRepository.findByIp("127.0.0.1");
            AccessLog[] accessLogs = new AccessLog[(int)(Math.random()*9+1)];
            for(int i=0; i<accessLogs.length; i++){
                int randomNum = (int)(Math.random()*5+1);
                switch (randomNum){
                    case 1: accessLogs[i] = AccessLog.builder().uri("/home").ip(ip)
                            .member(memberService.findByPhone("01012345678")).build(); break;
                    case 2: accessLogs[i] = AccessLog.builder().uri("/map").ip(ip)
                            .member(memberService.findByPhone("01012345678")).build(); break;
                    case 3: accessLogs[i] = AccessLog.builder().uri("/chat").ip(ip)
                            .member(memberService.findByPhone("01012345678")).build(); break;
                    case 4: accessLogs[i] = AccessLog.builder().uri("/post").ip(ip)
                            .member(memberService.findByPhone("01012345678")).build(); break;
                    case 5: accessLogs[i] = AccessLog.builder().uri("/contents").ip(ip)
                            .member(memberService.findByPhone("01012345678")).build(); break;
                }
            }
            for (AccessLog accessLog : accessLogs) {
                accessLogRepository.save(accessLog);
            }
        }
    }

    @Override
    public boolean authorityCheck(Authentication authentication, String page) {
        Member member = memberRepository.findByPhone(authentication.getName()).get();
        Authority authority = member.getAuthority();
        switch (page) {
            case "admin": return authority.getAdminMng();
            case "member": return authority.getMemberMng();
            case "chat": return authority.getChatMng();
            case "post": return authority.getPostMng();
            case "inquiry": return authority.getInquiryMng();
        }
        return false;
    }

    @Override
    public Map<LocalDate, Long> logCountByDay(int range, String page) {
        LocalDateTime startDateTime = LocalDateTime.of(
                LocalDateTime.now().minusDays(range).toLocalDate(),
                LocalTime.of(0,0,0));
        List<AccessLog> accessLogs = accessLogRepository.findByRegDateAfter(startDateTime);
        Map<LocalDate, Long> result = new TreeMap<>();
        List<LocalDate> days = new ArrayList<>();

        accessLogs.forEach(accessLog -> {
            LocalDate regDateDay = accessLog.getRegDate().toLocalDate();
            if(!days.contains(regDateDay)) days.add(regDateDay);
        });

        days.forEach(day -> {
            result.put(day, accessLogs.stream()
                            .filter(accessLog ->
                                    accessLog.getRegDate().toLocalDate().equals(day) && accessLog.getUri().contains(page))
                            .count()
            );
        });
        return result;
    }

    @Override
    public Map<LocalDate, Long> logCountByMonth(String page) {
        LocalDate localDate = LocalDate.now().minusMonths(5);
        LocalDateTime startDateTime = LocalDateTime.of(
                LocalDate.of(localDate.getYear(), localDate.getMonth(),1),
                LocalTime.of(0,0,0));
        List<AccessLog> accessLogs = accessLogRepository.findByRegDateAfter(startDateTime);
        Map<LocalDate, Long> result = new TreeMap<>();
        List<LocalDate> months = new ArrayList<>();

        accessLogs.forEach(accessLog -> {
            LocalDateTime regDate = accessLog.getRegDate();
            LocalDate regDateMonth = LocalDate.of(regDate.getYear(),regDate.getMonthValue(),1);
            if(!months.contains(regDateMonth)) months.add(regDateMonth);
        });

        months.forEach(month -> {
            result.put(month, accessLogs.stream()
                            .filter(accessLog -> {
                                LocalDateTime regDate = accessLog.getRegDate();
                                LocalDate regDateMonth = LocalDate.of(regDate.getYear(),regDate.getMonthValue(),1);
                                return regDateMonth.equals(month) && accessLog.getUri().contains(page);})
                            .count());
        });
        return result;
    }

    @Override
    public Map<LocalDate, Long> logCountByYear(String page) {
        LocalDate localDate = LocalDate.now().minusYears(3);
        LocalDateTime startDateTime = LocalDateTime.of(
                LocalDate.of(localDate.getYear(), 1,1),
                LocalTime.of(0,0,0));
        List<AccessLog> accessLogs = accessLogRepository.findByRegDateAfter(startDateTime);
        Map<LocalDate, Long> result = new TreeMap<>();
        List<LocalDate> years = new ArrayList<>();

        accessLogs.forEach(accessLog -> {
            LocalDateTime regDate = accessLog.getRegDate();
            LocalDate regDateYear = LocalDate.of(regDate.getYear(),1,1);
            if(!years.contains(regDateYear)) years.add(regDateYear);
        });

        years.forEach(year -> {
            result.put(year, accessLogs.stream()
                            .filter(accessLog -> {
                                LocalDateTime regDate = accessLog.getRegDate();
                                LocalDate regDateYear = LocalDate.of(regDate.getYear(),1,1);
                                return regDateYear.equals(year) && accessLog.getUri().contains(page);})
                            .count());
        });
        return result;
    }

    @Override
    public Map<String, Map<LocalDate, Long>> pageCountByDay(int range) {
        Map<String, Map<LocalDate, Long>> result = new HashMap<>();
        result.put("home", logCountByDay(range, "home"));
        result.put("map", logCountByDay(range,"map"));
        result.put("chat", logCountByDay(range,"chat"));
        result.put("post", logCountByDay(range, "post"));
        result.put("contents", logCountByDay(range, "contents"));
        return result;
    }

    @Override
    public Map<String, Map<LocalDate, Long>> pageCountByMonth() {
        Map<String, Map<LocalDate, Long>> result = new HashMap<>();
        result.put("home", logCountByMonth("home"));
        result.put("map", logCountByMonth("map"));
        result.put("chat", logCountByMonth("chat"));
        result.put("post", logCountByMonth("post"));
        result.put("contents", logCountByMonth("contents"));
        return result;
    }

    @Override
    public Map<String, Map<LocalDate, Long>> pageCountByYear() {
        Map<String, Map<LocalDate, Long>> result = new HashMap<>();
        result.put("home", logCountByYear("home"));
        result.put("map", logCountByYear("map"));
        result.put("chat", logCountByYear("chat"));
        result.put("post", logCountByYear("post"));
        result.put("contents", logCountByYear("contents"));
        return result;
    }

    @Override
    public Map<LocalDate, Long> memberCountByDay() {
        Map<LocalDate, Long> result = new TreeMap<>();
        for(int i=30; i>=0; i--){
            LocalDate day = LocalDate.now().minusDays(i);
            Long count = memberRepository.countByRegDate(day);
            result.put(day, count);
        }
        return result;
    }

    @Override
    public Boolean blockCheck(BlockDto blockDto) {
        boolean result = false;
        switch (blockDto.getBlockType()) {
            case "ip":
                result = blacklistRepository.findByIp(ipRepository.findByIp(blockDto.getIp())) == null;
                break;
            case "member":
                result = blacklistRepository.findByMember(memberService.findById(blockDto.getId())) == null;
        }
        return result;
    }

    @Override
    public void memberBlock(Long id, int date, String reason) {
        Member member = memberService.findById(id);
        LocalDateTime expDate = LocalDateTime.now();

        switch (date) {
            case 1: expDate = LocalDateTime.now().plusMonths(1); break;
            case 2: expDate = LocalDateTime.now().plusMonths(6); break;
            case 3: expDate = LocalDateTime.now().plusYears(1); break;
            case 4: expDate = LocalDateTime.now().plusYears(3); break;
            case 5: expDate = LocalDateTime.now().plusYears(999); break;
        }
        blacklistRepository.save(Blacklist.builder().member(member).expDate(expDate).reason(reason).build());
    }

    @Override
    public void memberBlockCancel(Long id) {
        blacklistRepository.delete(blacklistRepository.findByMember(memberService.findById(id)));
    }

    @Override
    public void ipBlock(String ip, int date, String reason) {
        LocalDateTime expDate = LocalDateTime.now();
        switch (date) {
            case 1: expDate = LocalDateTime.now().plusMonths(1); break;
            case 2: expDate = LocalDateTime.now().plusMonths(6); break;
            case 3: expDate = LocalDateTime.now().plusYears(1); break;
            case 4: expDate = LocalDateTime.now().plusYears(3); break;
            case 5: expDate = LocalDateTime.now().plusYears(999); break;
        }
        blacklistRepository.save(Blacklist.builder().ip(ipRepository.findByIp(ip)).expDate(expDate).reason(reason).build());
    }

    @Override
    public void ipBlockCancel(String ip) {
        blacklistRepository.delete(blacklistRepository.findByIp(ipRepository.findByIp(ip)));
    }

    @Override
    public Page<Blacklist> findAllMemberBlacklist(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable= PageRequest.of(page,12, Sort.by("regDate").descending());
        return blacklistRepository.findAllByMemberNotNullOrderByRegDateDesc(pageable);
    }

    @Override
    public Page<Blacklist> findAllIpBlacklist(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable= PageRequest.of(page,12, Sort.by("regDate").descending());
        return blacklistRepository.findAllByIpNotNullOrderByRegDateDesc(pageable);
    }


    @Override
    public Page<AccessLog> findAllAccessLog(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable= PageRequest.of(page,12, Sort.by("regDate").descending());
        return accessLogRepository.findAllByOrderByRegDateDesc(pageable);
    }

    @Override
    public Blacklist findBlacklistByIp(String ip) {
        return blacklistRepository.findByIp(ipRepository.findByIp(ip));
    }

    @Override
    public Page<Report> findAllReport(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable= PageRequest.of(page,12, Sort.by("regDate").descending());
        return reportRepository.findAllByOrderByRegDateDesc(pageable);
    }

    @Override
    public void reportHandler(Long reportId, boolean state) {
        Report report = reportRepository.findById(reportId).get();
        report.changeComplete(state);
        reportRepository.save(report);
    }

    @Override
    public List<Member> findAllAdmin() {
        List<MemberRole> roles = new ArrayList<>();
        roles.add(MemberRole.ADMIN);
        roles.add(MemberRole.SUPER_ADMIN);
        return memberService.findByRoles(roles);
    }

    @Override
    public AccessLog findLastAccess(Member member) {
        return accessLogRepository.findTopByMemberOrderByRegDateDesc(member);
    }

    @Override
    public List<AdminTeam> findAllTeam() {
        return adminTeamRepository.findAll();
    }

    @Override
    public void createAdmin(MemberDto memberDto) {
        Member admin = memberDto.createMember(passwordEncoder);
        admin.grantRole(MemberRole.ADMIN);
        AdminTeam adminTeam = adminTeamRepository.findById(memberDto.getTeamNumber()).get();
        admin.updateAdminTeam(adminTeam);
        memberRepository.save(admin);
        authorityRepository.save(Authority.builder()
                .member(admin)
                .adminMng(false)
                .memberMng(false)
                .chatMng(false)
                .postMng(false)
                .inquiryMng(false)
                .build());
    }

    @Override
    public void updateAdmin(MemberDto memberDto) {
        Member admin = memberRepository.findById(memberDto.getId()).get();
        admin.updateAdminTeam(adminTeamRepository.findById(memberDto.getTeamNumber()).get());
        memberRepository.save(admin);
        Authority authority = authorityRepository.findByMember(admin);
        authority.updateAuthorities(memberDto.getAuthorities());
        authorityRepository.save(authority);
    }

    @Override
    public void deleteAdmin(Long id) {
        memberService.delete(id);
    }

    @Override
    public void createTeam(String teamName) {
        adminTeamRepository.save(AdminTeam.builder().name(teamName).build());
    }

    @Override
    public Page<Post> findAllPost(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable= PageRequest.of(page,12, Sort.by("regDate").descending());
        return postRepository.findAll(pageable);
    }

    @Override
    public Page<Notice> findAllNotice(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable= PageRequest.of(page,12, Sort.by("regDate").descending());
        return noticeRepository.findAll(pageable);
    }

    @Override
    public void saveNotice(NoticeDto noticeDto, Authentication authentication) {
        Member member = memberService.findMember(authentication);
        noticeDto.setMember(member);
        noticeDto.setMemberNickname(member.getNickname());
        Notice notice = noticeDto.toEntity();
        noticeRepository.save(notice);
    }

    @Override
    public void deleteStory(Long id) {
        postRepository.delete(postRepository.findById(id).get());
    }

    @Override
    public void deleteNotice(Long id) {
        noticeRepository.delete(noticeRepository.findById(id).get());
    }
}