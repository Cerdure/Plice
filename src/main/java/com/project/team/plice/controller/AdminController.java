package com.project.team.plice.controller;

import com.project.team.plice.domain.admin.Report;
import com.project.team.plice.domain.enums.MemberRole;
import com.project.team.plice.domain.member.Member;
import com.project.team.plice.dto.admin.BlockDto;
import com.project.team.plice.dto.member.MemberDto;
import com.project.team.plice.service.interfaces.AdminService;
import com.project.team.plice.service.interfaces.ChatService;
import com.project.team.plice.service.interfaces.MapService;
import com.project.team.plice.service.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final MemberService memberService;
    private final MapService mapService;
    private final ChatService chatService;
//    private final PostService postService;

    @GetMapping(value = {"/admin", "/admin/site-chart"})
    public String siteChart(){
        adminService.initLogAccess();
        return "layout-content/admin/site-chart";
    }

    @GetMapping("/admin/logCount/daily")
    @ResponseBody
    public Object logCountDaily(){
        return adminService.pageCountByDay(0);
    }

    @GetMapping("/admin/logCount/day")
    @ResponseBody
    public Object logCountDay(@RequestParam("countType") String type){
        switch (type) {
            case "total": return adminService.logCountByDay(6,"");
            case "page": return adminService.pageCountByDay(6);
            default: return null;
        }
    }

    @GetMapping("/admin/logCount/month")
    @ResponseBody
    public Object logCountMonth(@RequestParam("countType") String type){
        switch (type) {
            case "total": return adminService.logCountByMonth("");
            case "page": return adminService.pageCountByMonth();
            default: return null;
        }
    }

    @GetMapping("/admin/logCount/year")
    @ResponseBody
    public Object logCountYear(@RequestParam("countType") String type){
        switch (type) {
            case "total": return adminService.logCountByYear("");
            case "page": return adminService.pageCountByYear();
            default: return null;
        }
    }

    @GetMapping("/admin/memberCount")
    @ResponseBody
    public Object memberCount(){
         return adminService.memberCountByDay();
    }

    @GetMapping("/admin/keywords")
    @ResponseBody
    public Object keywords(){
        return mapService.searchKeywordTop10();
    }

    @GetMapping("/admin/admin-mng")
    public String adminMng(){
        return "layout-content/admin/admin-mng";
    }

    @GetMapping("/admin/member-mng")
    public String memberMng(Model model, Pageable pageable){
        Page<Member> members = memberService.findByRole(MemberRole.USER, pageable);
        model.addAttribute("members", members);
        return "layout-content/admin/member-mng";
    }

    @GetMapping("/admin/member-mod")
    public String memberMod(@ModelAttribute MemberDto memberDto){
        memberService.update(memberDto);
        return "redirect:/admin/member-mng";
    }

    @GetMapping("/admin/member-del")
    public String memberDel(@RequestParam("id") Long id){
        memberService.delete(id);
        return "redirect:/admin/member-mng";
    }

    @GetMapping("/admin/access-mng/log")
    public String accessMngLog(Model model, Pageable pageable){
        model.addAttribute("accessList", adminService.findAllAccessLog(pageable));
        return "layout-content/admin/access-mng-log";
    }

    @GetMapping("/admin/access-mng/member")
    public String accessMngBlacklistMember(Model model, Pageable pageable){
        model.addAttribute("memberBlacklists", adminService.findAllMemberBlacklist(pageable));
        return "layout-content/admin/access-mng-member";
    }

    @GetMapping("/admin/access-mng/ip")
    public String accessMngBlacklistIp(Model model, Pageable pageable){
        model.addAttribute("ipBlacklists", adminService.findAllIpBlacklist(pageable));
        return "layout-content/admin/access-mng-ip";
    }

    @GetMapping("/admin/chat-mng")
    public String chatMng(Model model, Pageable pageable){
        Page<Report> reports = adminService.findAllReport(pageable);
        model.addAttribute("reports", reports);
        return "layout-content/admin/chat-mng";
    }

    @GetMapping("/admin/chat-view")
    public String chatMng(@RequestParam("chatId") Long chatId, Model model, Pageable pageable){
        model.addAttribute("reportedChat", chatService.findChatById(chatId));
        model.addAttribute("chatRoom", chatService.findChatRoomByChatId(chatId));
        model.addAttribute("chatsMap", chatService.chatsGroupByDay(chatId));
        return "layout-content/admin/chat-mng :: #chat";
    }

    @GetMapping("/admin/report/state")
    public String reportStateSet(@RequestParam("reportId") Long id,
                                 @RequestParam("state") boolean state,
                                 Pageable pageable, Model model){
        adminService.reportHandler(id, state);
        Page<Report> reports = adminService.findAllReport(pageable);
        model.addAttribute("reports", reports);
        return "layout-content/admin/chat-mng :: .contents";
    }

    @GetMapping("/admin/post-mng")
    public String postMng(){
        return "layout-content/admin/post-mng";
    }

    @GetMapping("/admin/block-check")
    @ResponseBody
    public Boolean blockCheck(@ModelAttribute BlockDto blockDto){
        return adminService.blockCheck(blockDto);
    }

    @GetMapping("/admin/block")
    public String block(@ModelAttribute BlockDto blockDto, Model model){
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
    public String blockCancel(@ModelAttribute BlockDto blockDto){
        switch (blockDto.getBlockType()){
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



