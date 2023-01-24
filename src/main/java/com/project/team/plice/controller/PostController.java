package com.project.team.plice.controller;

import com.project.team.plice.domain.post.Notice;
import com.project.team.plice.domain.post.Post;
import com.project.team.plice.dto.post.NoticeDto;
import com.project.team.plice.dto.post.PostDto;
import com.project.team.plice.dto.post.ReplyDto;
import com.project.team.plice.service.classes.PostServiceImpl;
import com.project.team.plice.service.classes.ReplyServiceImpl;
import com.project.team.plice.service.interfaces.AdminService;
import com.project.team.plice.utils.DataUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PostController {

    private final AdminService adminService;
    private final PostServiceImpl postService;
    private final ReplyServiceImpl replyService;

    @GetMapping("/post/story")
    public String post(@ModelAttribute DataUtil dataUtil, HttpServletRequest request, Authentication authentication, Model model, Pageable pageable) {
        adminService.logAccess(request, authentication);
        if (dataUtil.getKeyword() == null) {
            Page<Post> posts = postService.findAllPost(pageable);
            model.addAttribute("posts", posts);
        } else {
            Page<Post> posts = postService.searchPost(dataUtil, pageable);
            model.addAttribute("posts", posts);
            model.addAttribute("dataUtil", dataUtil);
        }
        return "layout-content/post/post-story";
    }

    @PostMapping("/post/story")
    public String savePost(@ModelAttribute PostDto postDto, Authentication authentication) {
        postService.savePost(postDto, authentication);
        return "redirect:/post/story";
    }

    @GetMapping("/post/story/detail")
    public String storyDetail(@RequestParam("id") Long id, Model model) {
        postService.postHitsPlus(id);
        Post post = postService.findPostById(id);
        Post prev = postService.findPrevPost(post);
        Post next = postService.findNextPost(post);
        model.addAttribute("post", post);
        model.addAttribute("prevPost", prev);
        model.addAttribute("nextPost", next);
        return "layout-content/post/story-detail";
    }

    @PostMapping("/post/story/modify")
    public String updatePost(@ModelAttribute PostDto postDto) {
        postService.updatePost(postDto);
        return "redirect:/post/story/detail?id=" + postDto.getId();
    }

    @GetMapping("/post/story/delete")
    public String deletePost(@RequestParam("id") Long id) {
        postService.deletePost(id);
        return "redirect:/post/story";
    }

    @PostMapping("/post/reply/save")
    public String saveReply(@ModelAttribute ReplyDto replyDto, Authentication authentication) {
        replyService.replySave(replyDto, authentication);
        return "redirect:/post/story/detail?id=" + replyDto.getPostId();
    }

    @PostMapping("/post/reply/modify")
    public String modifyReply(@ModelAttribute ReplyDto replyDto) {
        replyService.replyModify(replyDto);
        return "redirect:/post/story/detail?id=" + replyDto.getPostId();
    }

    @GetMapping("/post/reply/delete")
    @ResponseBody
    public Boolean deleteReply(@RequestParam("id") Long id) {
        replyService.replyDelete(id);
        return true;
    }

    @GetMapping("/post/notice")
    public String notice(@ModelAttribute DataUtil dataUtil, HttpServletRequest request, Authentication authentication, Model model, Pageable pageable) {
        adminService.logAccess(request, authentication);
        if (dataUtil.getKeyword() == null) {
            Page<Notice> notices = postService.findAllNotice(pageable);
            model.addAttribute("notices", notices);
        } else {
            Page<Notice> notices = postService.searchNotice(dataUtil, pageable);
            model.addAttribute("notices", notices);
            model.addAttribute("dataUtil", dataUtil);
        }
        return "layout-content/post/post-notice";
    }

    @PostMapping("/post/notice")
    public String saveNotice(@ModelAttribute NoticeDto noticeDto, Authentication authentication) {
        postService.saveNotice(noticeDto, authentication);
        return "redirect:/post/notice";
    }

    @GetMapping("/post/notice/detail")
    public String noticeDetail(@RequestParam("id") Long id, Model model) {
        postService.noticeHitsPlus(id);
        Notice notice = postService.findNoticeById(id);
        Notice prev = postService.findPrevNotice(notice);
        Notice next = postService.findNextNotice(notice);
        model.addAttribute("notice", notice);
        model.addAttribute("prevNotice", prev);
        model.addAttribute("nextNotice", next);
        return "layout-content/post/notice-detail";
    }

    @PostMapping("/post/notice/modify")
    public String updateNotice(@ModelAttribute NoticeDto noticeDto) {
        postService.updateNotice(noticeDto);
        return "redirect:/post/notice/detail?id=" + noticeDto.getId();
    }

    @GetMapping("/post/notice/delete")
    public String deleteNotice(@RequestParam("id") Long id) {
        postService.deleteNotice(id);
        return "redirect:/post/notice";
    }

}