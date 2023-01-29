package com.project.team.plice.controller;

import com.project.team.plice.domain.admin.AccessLog;
import com.project.team.plice.domain.admin.Report;
import com.project.team.plice.domain.enums.MemberRole;
import com.project.team.plice.domain.member.Member;
import com.project.team.plice.dto.admin.BlockDto;
import com.project.team.plice.dto.inquire.AnswerDto;
import com.project.team.plice.dto.member.MemberDto;
import com.project.team.plice.dto.post.NoticeDto;
import com.project.team.plice.service.interfaces.*;
import com.project.team.plice.dto.utils.SearchUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final MemberService memberService;
    private final MapService mapService;
    private final ChatService chatService;
    private final PostService postService;
    private final InquireService inquireService;

    @GetMapping(value = {"/admin/authority-check"})
    @ResponseBody
    public Boolean authorityCheck(@RequestParam("page") String page, HttpServletRequest request, Authentication authentication) {
        return adminService.authorityCheck(authentication, page);
    }

    @GetMapping(value = {"/admin", "/admin/site-chart"})
    public String siteChart() {
        adminService.initLogAccess();
        return "layout-content/admin/site-chart";
    }

    @GetMapping("/admin/logCount/daily")
    @ResponseBody
    public Object logCountDaily() {
        return adminService.pageCountByDay(0);
    }

    @GetMapping("/admin/logCount/day")
    @ResponseBody
    public Object logCountDay(@RequestParam("countType") String type) {
        switch (type) {
            case "total":
                return adminService.logCountByDay(6, "");
            case "page":
                return adminService.pageCountByDay(6);
            default:
                return null;
        }
    }

    @GetMapping("/admin/logCount/month")
    @ResponseBody
    public Object logCountMonth(@RequestParam("countType") String type) {
        switch (type) {
            case "total":
                return adminService.logCountByMonth("");
            case "page":
                return adminService.pageCountByMonth();
            default:
                return null;
        }
    }

    @GetMapping("/admin/logCount/year")
    @ResponseBody
    public Object logCountYear(@RequestParam("countType") String type) {
        switch (type) {
            case "total":
                return adminService.logCountByYear("");
            case "page":
                return adminService.pageCountByYear();
            default:
                return null;
        }
    }

    @GetMapping("/admin/memberCount")
    @ResponseBody
    public Object memberCount() {
        return adminService.memberCountByDay();
    }

    @GetMapping("/admin/keywords")
    @ResponseBody
    public Object keywords() {
        return mapService.searchKeywordTop10();
    }

    @GetMapping("/admin/admin-mng")
    public String adminMng(Model model) {
        List<Member> admins = adminService.findAllAdmin();
        model.addAttribute("admins", admins);
        model.addAttribute("lastAccess", adminService.findLastAccess(admins.get(0)));
        model.addAttribute("teams", adminService.findAllTeam());
        return "layout-content/admin/admin-mng";
    }

    @PostMapping("/admin/admin-mng/admin-create")
    public String adminCreate(@ModelAttribute MemberDto memberDto) {
        adminService.createAdmin(memberDto);
        return "redirect:/admin/admin-mng";
    }

    @GetMapping("/admin/admin-mng/team-create")
    public boolean teamCreate(@RequestParam("teamName") String teamName) {
        adminService.createTeam(teamName);
        return true;
    }

    @GetMapping("/admin/admin-mng/detail")
    public String adminMng(@RequestParam("memberId") Long id, Model model) {
        Member admin = memberService.findById(id);
        model.addAttribute("admins", adminService.findAllAdmin());
        model.addAttribute("adminDetail", admin);
        model.addAttribute("teams", adminService.findAllTeam());
        model.addAttribute("lastAccess", adminService.findLastAccess(admin));
        return "layout-content/admin/admin-mng :: .accounts-wrapper";
    }

    @PostMapping("/admin/admin-mng/update")
    public String adminUpdate(@ModelAttribute MemberDto memberDto) {
        adminService.updateAdmin(memberDto);
        return "redirect:/admin/admin-mng/detail?memberId=" + memberDto.getId();
    }

    @GetMapping("/admin/admin-mng/delete")
    @ResponseBody
    public Boolean adminDelete(@RequestParam("memberId") Long id) {
        adminService.deleteAdmin(id);
        return true;
    }

    @GetMapping("/admin/admin-mng/delete/team")
    @ResponseBody
    public Boolean adminTeamDelete(@RequestParam("teamId") Long id) {
        adminService.deleteTeam(id);
        return true;
    }

    @GetMapping("/admin/member-mng")
    public String memberMng(@ModelAttribute SearchUtils searchUtils, Model model, Pageable pageable) {
        if (searchUtils.getKeyword() == null) {
            List<MemberRole> memberRoles = new ArrayList<>();
            memberRoles.add(MemberRole.USER);
            model.addAttribute("members", memberService.findByRoles(memberRoles, pageable));
        } else {
            List<MemberRole> memberRoles = new ArrayList<>();
            memberRoles.add(MemberRole.USER);
            model.addAttribute("members", memberService.searchMember(searchUtils, memberRoles, pageable));
            model.addAttribute("searchUtils", searchUtils);
        }
        return "layout-content/admin/member-mng";
    }

    @GetMapping("/admin/member-mod")
    public String memberMod(@ModelAttribute MemberDto memberDto) {
        memberService.update(memberDto);
        return "redirect:/admin/member-mng";
    }

    @GetMapping("/admin/member-del")
    public String memberDel(@RequestParam("id") Long id) {
        memberService.delete(id);
        return "redirect:/admin/member-mng";
    }

    @GetMapping("/admin/access-mng/log")
    public String accessMngLog(@ModelAttribute SearchUtils searchUtils, Model model, Pageable pageable) {
        if (searchUtils.getKeyword() == null) {
            model.addAttribute("accessList", adminService.findAllAccessLog(pageable));
        } else {
            Page<AccessLog> accessLogs = adminService.searchAccessLog(searchUtils, pageable);
            model.addAttribute("accessList", accessLogs);
            model.addAttribute("searchUtils", searchUtils);
        }
        return "layout-content/admin/access-mng-log";
    }

    @GetMapping("/admin/access-mng/member")
    public String accessMngBlacklistMember(@ModelAttribute SearchUtils searchUtils, Model model, Pageable pageable) {
        if (searchUtils.getKeyword() == null) {
            model.addAttribute("memberBlacklists", adminService.findAllMemberBlacklist(pageable));
        } else {
            model.addAttribute("memberBlacklists", adminService.searchMemberBlacklist(searchUtils, pageable));
            model.addAttribute("searchUtils", searchUtils);
        }
        return "layout-content/admin/access-mng-member";
    }

    @GetMapping("/admin/access-mng/ip")
    public String accessMngBlacklistIp(@ModelAttribute SearchUtils searchUtils, Model model, Pageable pageable) {
        if (searchUtils.getKeyword() == null) {
            model.addAttribute("ipBlacklists", adminService.findAllIpBlacklist(pageable));
        } else {
            model.addAttribute("ipBlacklists", adminService.searchIpBlacklist(searchUtils, pageable));
            model.addAttribute("searchUtils", searchUtils);
        }
        return "layout-content/admin/access-mng-ip";
    }

    @GetMapping("/admin/chat-mng")
    public String chatMng(@ModelAttribute SearchUtils searchUtils, Model model, Pageable pageable) {
        if (searchUtils.getKeyword() == null) {
            model.addAttribute("reports", adminService.findAllReport(pageable));
        } else {
            model.addAttribute("reports", adminService.searchReport(searchUtils, pageable));
            model.addAttribute("searchUtils", searchUtils);
        }
        return "layout-content/admin/chat-mng";
    }

    @GetMapping("/admin/chat-view")
    public String chatMng(@RequestParam("chatId") Long chatId, Model model, Pageable pageable) {
        model.addAttribute("reportedChat", chatService.findChatById(chatId));
        model.addAttribute("chatRoom", chatService.findChatRoomByChatId(chatId));
        model.addAttribute("chatsMap", chatService.chatsGroupByDay(chatId));
        return "layout-content/admin/chat-mng :: #chat";
    }

    @GetMapping("/admin/report/state")
    public String reportStateSet(@RequestParam("reportId") Long id,
                                 @RequestParam("state") boolean state,
                                 Pageable pageable, Model model) {
        adminService.reportHandler(id, state);
        Page<Report> reports = adminService.findAllReport(pageable);
        model.addAttribute("reports", reports);
        return "layout-content/admin/chat-mng :: .contents";
    }

    @GetMapping("/admin/post-mng/story")
    public String postMngStory(@ModelAttribute SearchUtils searchUtils, Model model, Pageable pageable) {
        if (searchUtils.getKeyword() == null) {
            model.addAttribute("posts", postService.findAllPost(pageable));
        } else {
            model.addAttribute("posts", postService.searchPost(searchUtils, pageable));
            model.addAttribute("searchUtils", searchUtils);
        }
        return "layout-content/admin/post-mng-story";
    }

    @GetMapping("/admin/post-mng/story/detail")
    public String postMngStoryDetail(@RequestParam("id") Long id, Model model) {
        model.addAttribute("post", postService.findPostById(id));
        return "layout-content/admin/post-mng-story :: #post";
    }

    @GetMapping("/admin/post-mng/story/delete")
    @ResponseBody
    public Boolean deleteStory(@RequestParam("id") Long id) {
        postService.deletePost(id);
        return true;
    }

    @GetMapping("/admin/post-mng/notice")
    public String postMngNotice(@ModelAttribute SearchUtils searchUtils, Model model, Pageable pageable) {
        if (searchUtils.getKeyword() == null) {
            model.addAttribute("notices", postService.findAllNotice(pageable));
        } else {
            model.addAttribute("notices", postService.searchNotice(searchUtils, pageable));
            model.addAttribute("searchUtils", searchUtils);
        }
        return "layout-content/admin/post-mng-notice";
    }

    @GetMapping("/admin/post-mng/notice/detail")
    public String postMngNoticeDetail(@RequestParam("id") Long id, Model model) {
        model.addAttribute("notice", postService.findNoticeById(id));
        return "layout-content/admin/post-mng-notice :: #post";
    }

    @PostMapping("/admin/post-mng/notice")
    public String createNotice(@ModelAttribute NoticeDto noticeDto, Authentication authentication) {
        adminService.saveNotice(noticeDto, authentication);
        return "redirect:/admin/post-mng/notice";
    }

    @GetMapping("/admin/post-mng/notice/modify")
    public String loadNotice(@RequestParam("id") Long id, Model model) {
        model.addAttribute("notice", postService.findNoticeById(id));
        return "layout-content/admin/post-mng-notice :: .notice-write-wrapper";
    }

    @PostMapping("/admin/post-mng/notice/modify")
    @ResponseBody
    public Boolean modifyNotice(@ModelAttribute NoticeDto noticeDto, Model model) {
        postService.updateNotice(noticeDto);
        return true;
    }

    @GetMapping("/admin/post-mng/notice/delete")
    @ResponseBody
    public Boolean deleteNotice(@RequestParam("id") Long id) {
        adminService.deleteNotice(id);
        return true;
    }

    @GetMapping("/admin/inquiry-mng")
    public String inquiryMng(@ModelAttribute SearchUtils searchUtils, Model model, Pageable pageable) {
        if (searchUtils.getKeyword() == null) {
            model.addAttribute("inquiries", inquireService.findAllInquire(pageable));
        } else {
            model.addAttribute("inquiries", inquireService.searchInquire(searchUtils, pageable));
            model.addAttribute("searchUtils", searchUtils);
        }
        return "layout-content/admin/inquiry-mng";
    }

    @GetMapping("/admin/inquiry-mng/detail")
    public String inquiryMngDetail(@RequestParam("id") Long id, Model model) {
        model.addAttribute("inquiry", inquireService.findInquire(id));
        return "layout-content/admin/inquiry-mng :: .window-wrapper";
    }

    @PostMapping("/admin/inquiry-mng/answer")
    @ResponseBody
    public Boolean saveAnswer(@ModelAttribute AnswerDto answerDto, Authentication authentication) {
        adminService.saveAnswer(answerDto, authentication);
        return true;
    }

    @PostMapping("/admin/inquiry-mng/answer/modify")
    @ResponseBody
    public Boolean modifyAnswer(@ModelAttribute AnswerDto answerDto, Authentication authentication) {
        adminService.modifyAnswer(answerDto, authentication);
        return true;
    }

    @GetMapping("/admin/block-check")
    @ResponseBody
    public Boolean blockCheck(@ModelAttribute BlockDto blockDto) {
        return adminService.blockCheck(blockDto);
    }

    @GetMapping("/admin/block")
    @ResponseBody
    public Boolean block(@ModelAttribute BlockDto blockDto, Model model) {
        switch (blockDto.getBlockType()) {
            case "ip":
                adminService.ipBlock(blockDto.getIp(), blockDto.getDate(), blockDto.getReason());
                break;
            case "member":
                adminService.memberBlock(blockDto.getId(), blockDto.getDate(), blockDto.getReason());
                break;
        }
        return true;
    }

    @GetMapping("/admin/block-cancel")
    @ResponseBody
    public Boolean blockCancel(@ModelAttribute BlockDto blockDto) {
        switch (blockDto.getBlockType()) {
            case "ip":
                adminService.ipBlockCancel(blockDto.getIp());
                break;
            case "member":
                adminService.memberBlockCancel(blockDto.getId());
                break;
        }
        return true;
    }
}



