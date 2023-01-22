package com.project.team.plice.controller;

import com.project.team.plice.domain.post.Post;
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

    private final PostServiceImpl postService;
    private final ReplyServiceImpl replyService;

    @GetMapping("/post-story")
    public String post(@ModelAttribute("PostDto") PostDto postDto, Model model, Pageable pageable) { // controller에서 view로 넘어갈 때 값을 보내주는 역할이 Model
        Page<Post> posts = postService.findAll(pageable);
        model.addAttribute("posts", posts);
        return "layout-content/post/post-story"; //post.html 으로 retrun 해서 페이지 이동을 하려면, 반환타입이 무조건 String?
    }

    @PostMapping("/post-story") // post 페이지로 매핑해서 넘겨줌, 포스트 방식이라 PostMapping <-> GetMapping
    public String savePost(@ModelAttribute Post post, Model model, Pageable pageable, Authentication authentication) throws Exception { // ModelAttribute == view단에서 controller로 넘길 때, 객체에 데이터 담아서 전달
        postService.savePost(post);
        Page<Post> posts = postService.findAllPost(pageable);
        model.addAttribute("posts", posts);
        return "redirect:/post-story"; // 페이지로 리턴. post.html
    }

    @GetMapping("/story-detail")
    public String storyDetail(@RequestParam("id") Long id, Model model) { // controller에서 view로 넘어갈 때 값을 보내주는 역할이 Model
        Post posts = postService.findPostById(id);
        postService.hitsPlus(posts);
        Post prev = postService.findPrevPost(posts);
        Post next = postService.findNextPost(posts);
        model.addAttribute("posts", posts);
        model.addAttribute("prevPost",prev);
        model.addAttribute("nextPost",next);
        return "layout-content/post/story-detail";
    }

/*    @PostMapping("/post-detail")
    public String updatePost(@ModelAttribute PostDto postDto, Model model) { // controller에서 view로 넘어갈 때 값을 보내주는 역할이 Model
        Post post = postService.findPostById(postDto.getId());
        post.changeTitle(postDto.getTitle());
        post.changeContent(postDto.getContent());
        postService.savePost(post);
        model.addAttribute("post", post);
        return "layout-content/post/post-detail";
    }*/

    @PostMapping("/story-detail")
    public String updatePost(@ModelAttribute PostDto postDto, Model model) { // controller에서 view로 넘어갈 때 값을 보내주는 역할이 Model
        Long id = postDto.getId(); /* 서비스에서 로직 처리하게끔 분리하기*/
        Post post = postService.findPostById(id);
        post.changeTitle(postDto.getTitle());
        post.changeContent(postDto.getContent());
        postService.savePost(post);
        model.addAttribute("post", post);
        return "redirect:/story-detail?id=" + id;
    }

    @GetMapping("/post-delete")
    public String deletePost(@RequestParam("id") Long id, Model model, Pageable pageable) {
        /* 삭제하는 부분*/
        Post post = postService.findPostById(id);
        postService.deletePost(post);
        /* 다시 페이지 돌아갈 때, 화면에 표시할 게시글들 정보 가져오기 */
        Page<Post> posts = postService.findAllPost(pageable);
        model.addAttribute("post", post);
        /* 리턴을 목록 페이지로 */
        return "redirect:/post-story";
    }
//  *********************************************** 검색 ****************************************************

    @GetMapping("/post-search")
    public String findPost(@ModelAttribute PostDto postDto, Model model, Pageable pageable) throws Exception{
        Page<Post> posts;
        if(postDto.getSearchBy() == 1){
            posts = postService.findByTitle(postDto.getInput(), pageable);
        } else {
            posts = postService.findByMemberNickname(postDto.getInput(), pageable);
        }
        DataUtil dataUtil = new DataUtil();
        dataUtil.setSearchBy(postDto.getSearchBy());
        dataUtil.setInput(postDto.getInput());
        model.addAttribute("dataUtil", dataUtil);
        model.addAttribute("posts",posts);
        return "redirect:/post-story";
    }

//  *********************************************** 댓글 ****************************************************

    @PostMapping("/reply-create")
    public String replyCreate(@PathVariable("id") Long id, @ModelAttribute ReplyDto replyDto, Authentication authentication, Model model){
        replyService.replySave(replyDto, id, authentication);
        Post post = postService.findById(id);
        System.out.println("post.getReplies().size() = " + post.getReplies().size());
        Post prev = postService.findPrevPost(post);
        Post next = postService.findNextPost(post);
        model.addAttribute("post", post);
        model.addAttribute("prevPost",prev);
        model.addAttribute("nextPost",next);
        return "/layout-content/post/story-detail";
    }

    @PostMapping("/reply-modify")
    public String replyModify(@PathVariable("id") Long id, @ModelAttribute ReplyDto replyDto, Model model){
        replyService.replyModify(replyDto);
        Post post = postService.findById(id);
        Post prev = postService.findPrevPost(post);
        Post next = postService.findNextPost(post);
        model.addAttribute("post", post);
        model.addAttribute("prevPost",prev);
        model.addAttribute("nextPost",next);
        return "layout-content/post/story-detail";
    }

    @PostMapping("/reply-delete")
    public String replyDelete(@PathVariable("inquireId") Long id, @ModelAttribute ReplyDto replyDto, Model model){
        replyService.replyDelete(replyDto);
        Post post = postService.findById(id);
        System.out.println("inquire.getReplies().size() = " + post.getReplies().size());
        Post prev = postService.findPrevPost(post);
        Post next = postService.findNextPost(post);
        model.addAttribute("post", post);
        model.addAttribute("prevPost",prev);
        model.addAttribute("nextPost",next);
        return "layout-content/post/story-detail";
    }

}