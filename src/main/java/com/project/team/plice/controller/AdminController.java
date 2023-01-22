package com.project.team.plice.controller;

import com.project.team.plice.domain.admin.AccessLog;
import com.project.team.plice.domain.admin.Report;
import com.project.team.plice.domain.enums.MemberRole;
import com.project.team.plice.domain.member.Member;
import com.project.team.plice.dto.admin.BlockDto;
import com.project.team.plice.dto.member.MemberDto;
import com.project.team.plice.dto.post.NoticeDto;
import com.project.team.plice.service.interfaces.AdminService;
import com.project.team.plice.service.interfaces.ChatService;
import com.project.team.plice.service.interfaces.MapService;
import com.project.team.plice.service.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;
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
//    private final PostService postService;

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
    public String adminDelete(@RequestParam("memberId") Long id) {
        adminService.deleteAdmin(id);
        return "redirect:/admin/admin-mng";
    }

    @GetMapping("/admin/member-mng")
    public String memberMng(Model model, Pageable pageable) {
        List<MemberRole> memberRoles = new ArrayList<>();
        memberRoles.add(MemberRole.USER);
        Page<Member> members = memberService.findByRoles(memberRoles, pageable);
        model.addAttribute("members", members);
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
    public String accessMngLog(Model model, Pageable pageable) {
        model.addAttribute("accessList", adminService.findAllAccessLog(pageable));
        return "layout-content/admin/access-mng-log";
    }

    @GetMapping("/admin/access-mng/member")
    public String accessMngBlacklistMember(Model model, Pageable pageable) {
        model.addAttribute("memberBlacklists", adminService.findAllMemberBlacklist(pageable));
        return "layout-content/admin/access-mng-member";
    }

    @GetMapping("/admin/access-mng/ip")
    public String accessMngBlacklistIp(Model model, Pageable pageable) {
        model.addAttribute("ipBlacklists", adminService.findAllIpBlacklist(pageable));
        return "layout-content/admin/access-mng-ip";
    }

    @GetMapping("/admin/chat-mng")
    public String chatMng(Model model, Pageable pageable) {
        Page<Report> reports = adminService.findAllReport(pageable);
        model.addAttribute("reports", reports);
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
    public String postMngStory(Model model, Pageable pageable) {
        model.addAttribute("posts", adminService.findAllPost(pageable));
        return "layout-content/admin/post-mng-story";
    }

    @GetMapping("/admin/post-mng/story/delete")
    public String deleteStory(@RequestParam("id") Long id) {
        adminService.deleteStory(id);
        return "redirect:/admin/post-mng/story";
    }

    @GetMapping("/admin/post-mng/notice")
    public String postMngNotice(Model model, Pageable pageable) {
        model.addAttribute("notices", adminService.findAllNotice(pageable));
        return "layout-content/admin/post-mng-notice";
    }

    @PostMapping("/admin/post-mng/notice")
    public String createNotice(@ModelAttribute NoticeDto noticeDto, Authentication authentication) {
        adminService.saveNotice(noticeDto, authentication);
        return "redirect:/admin/post-mng/notice";
    }

    @GetMapping("/admin/post-mng/notice/delete")
    public String deleteNotice(@RequestParam("id") Long id) {
        adminService.deleteNotice(id);
        return "redirect:/admin/post-mng/notice";
    }

    @GetMapping("/admin/block-check")
    @ResponseBody
    public Boolean blockCheck(@ModelAttribute BlockDto blockDto) {
        return adminService.blockCheck(blockDto);
    }

    @GetMapping("/admin/block")
    public String block(@ModelAttribute BlockDto blockDto, Model model) {
        switch (blockDto.getBlockType()) {
            case "ip":
                adminService.ipBlock(blockDto.getIp(), blockDto.getDate(), blockDto.getReason());
                break;
            case "member":
                adminService.memberBlock(blockDto.getId(), blockDto.getDate(), blockDto.getReason());
                break;
        }
        switch (blockDto.getPageType()) {
            case "member":
                return "redirect:/admin/member-mng";
            case "accessMember":
                return "redirect:/admin/access-mng/member";
            case "accessIp":
                return "redirect:/admin/access-mng/ip";
            case "chat":
                model.addAttribute("blockComplete", true);
                return "redirect:/admin/chat-mng";
            default:
                return "redirect:/admin/access-mng/log";
        }
    }

    @GetMapping("/admin/block-cancel")
    public String blockCancel(@ModelAttribute BlockDto blockDto) {
        switch (blockDto.getBlockType()) {
            case "ip":
                adminService.ipBlockCancel(blockDto.getIp());
                break;
            case "member":
                adminService.memberBlockCancel(blockDto.getId());
                break;
        }
        switch (blockDto.getPageType()) {
            case "member":
                return "redirect:/admin/member-mng";
            case "accessMember":
                return "redirect:/admin/access-mng/member";
            case "accessIp":
                return "redirect:/admin/access-mng/ip";
            default:
                return "redirect:/admin/access-mng/log";
        }
    }
}



