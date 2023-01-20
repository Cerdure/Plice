package com.project.team.plice.controller;

import com.project.team.plice.domain.post.Notice;
import com.project.team.plice.dto.post.NoticeDto;
import com.project.team.plice.service.classes.NoticeServiceImpl;
import com.project.team.plice.service.classes.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeServiceImpl noticeService;
    private final PostServiceImpl postService;

    @GetMapping("/post-notice")
    public String notice(@ModelAttribute("NoticeDto") NoticeDto noticeDto, Model model, Pageable pageable) {
        Page<Notice> notices = noticeService.findAll(pageable);
        model.addAttribute("notices", notices);
        return "layout-content/post/post-notice";
    }

    @PostMapping("/post-notice")
    public String saveNotice(@ModelAttribute Notice notice, Model model, Pageable pageable, Authentication authentication) throws Exception {
        noticeService.saveNotice(notice);
        Page<Notice> notices = noticeService.findAllNotice(Pageable.unpaged());
        model.addAttribute("notices", notices);
        return "redirect:/post-notice";
    }

    @GetMapping("/notice-detail")
    public String noticeDetail(@RequestParam("id") Long id, Model model) {
        Notice notice = noticeService.findNoticeById(id);
        noticeService.hitsPlus(notice);
        Notice prev = noticeService.findPrevNotice(notice);
        Notice next = noticeService.findNextNotice(notice);
        model.addAttribute("notice", notice);
        model.addAttribute("prevNotice", prev);
        model.addAttribute("nextNotice", next);
        return "layout-content/post/notice-detail";
    }

    @PostMapping("/notice-detail")
    public String updateNotice(@ModelAttribute NoticeDto noticeDto, Model model) {
        Notice notice = noticeService.findNoticeById(noticeDto.getId());
        notice.changeTitle(noticeDto.getTitle());
        notice.changeContent(noticeDto.getContent());
        noticeService.saveNotice(notice);
        model.addAttribute("notice", notice);
        return "layout-content/post/notice-detail";
    }

    @GetMapping("/notice-delete")
    public String deleteNotice(@RequestParam("id") Long id, Model model) {
        Notice notice = noticeService.findNoticeById(id);
        noticeService.deleteNotice(notice);
        Page<Notice> notices = noticeService.findAllNotice(Pageable.unpaged());
        model.addAttribute("notices", notices);
        return "redirect:/post-notice";
    }
}