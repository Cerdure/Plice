package com.project.team.plice.service.classes;

import com.project.team.plice.domain.post.Post;
import com.project.team.plice.dto.post.PostDto;
import com.project.team.plice.repository.post.NoticeRepository;
import com.project.team.plice.repository.post.PostRepository;
import com.project.team.plice.service.interfaces.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final NoticeRepository NoticeRepository;

//    @Override
//    public List<Post> findAllPost() {
//        return postRepository.findAll();
//    }

    @Override
    public Page<Post> findAll(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() -1);
        pageable = PageRequest.of(page, 12, Sort.by("id").descending());
        return postRepository.findAll(pageable);
    }

    @Override
    public Page<Post> findAllPost(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 8, Sort.by("id").descending());
        return postRepository.findAll(pageable);
    }

    @Override
    public Post findPostById(Long id) {
        return postRepository.findById(id).get();
    }

    //  게시판 CRUD
    @Override /* 삽입 + 수정 */
    public void savePost(Post post) {
        postRepository.save(post);
    } // DB에 저장할때는 반환타입 의미없음. 로그찍기라도 하려면 String으로 하는데, 어차피 저장하면 끝이니까 그냥 void 해도 됨

    @Override
    public void deletePost(Post post) {

        postRepository.delete(post);
    }

    @Override
    public void updatePost(PostDto postDto) {
    }

//  **************************************** 검색 관련 ***************************************

    @Override
    public Page<Post> findByMemberId(Long id, Pageable pageable) {
        return postRepository.findById(id, pageable);
    }

    @Override
    public Page<Post> findByMemberNickname(String memberNickname, Pageable pageable) {
        return postRepository.findByMemberNicknameContainsIgnoreCase(memberNickname, pageable);
    }

    @Override
    public Page<Post> findByTitle(String title, Pageable pageable) {
        return postRepository.findByTitleContainingIgnoreCase(title, pageable);
    }

//  **************************************** 페이징 관련 ***************************************

    @Override
    public Post findPrevPost(Post post) {
        LocalDateTime regDate = post.getRegDate();
        List<Post> findResult = postRepository.findByRegDateBeforeOrderByRegDateDesc(regDate);
        findResult.forEach(e -> System.out.println("e.getId() = " + e.getId()));

        if(findResult == null || findResult.size() == 0){
            return post.builder().build();

        } else {
            return findResult.get(0);
        }
    }

    @Override
    public Post findNextPost(Post post) {
        LocalDateTime regDate = post.getRegDate();
        List<Post> findResult = postRepository.findByRegDateAfter(regDate);
        if(findResult == null || findResult.size() == 0){
            return post.builder().build();

        } else {
            return findResult.get(0);
        }
    }

    @Override
    public Post modify(Long id, PostDto postDto, Authentication authentication) throws Exception {
        return null;
    }

    @Override
    public void hitsPlus(Post post) {
        post.hitsPlus();
        postRepository.save(post);
    }

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id).get();
    }

    /*    @PostMapping("/post-detail")
    public String updatePost(@ModelAttribute PostDto postDto, Model model) { // controller에서 view로 넘어갈 때 값을 보내주는 역할이 Model
        Long id = postDto.getId(); *//* 서비스에서 로직 처리하게끔 분리하기*//*
        Post post = postService.findPostById(id);
        post.changeTitle(postDto.getTitle());
        post.changeContent(postDto.getContent());
        postService.savePost(post);
        model.addAttribute("post", post);
        return "redirect:/post-detail?id=" + id;
    }


    //    @Override
    public Post updatePost(Post post) { // controller에서 view로 넘어갈 때 값을 보내주는 역할이 Model
        Long id = post.getId();
        post = postRepository.findById(id).get();
        post.changeTitle(post.getTitle());
        post.changeContent(post.getContent());
        return postRepository.save(post);
    }*/

}
