package com.project.team.plice.service.interfaces;

import com.project.team.plice.domain.post.Post;
import com.project.team.plice.dto.post.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface PostService {

//    public List<Post> findAllPost();

    public Page<Post> findAllPost(Pageable pageable);
    public Post findPostById(Long id);
    public void savePost(Post post);
    public void deletePost(Post post);
    public void updatePost(PostDto postDto);

//  ***************** 페이징 *****************

    public Page <Post> findByMemberId(Long id, Pageable pageable);

    public Page<Post> findByMemberNickname(String memberNickname, Pageable pageable);

    public Page <Post> findByTitle(String title, Pageable pageable);


//  public Page<Post> postList(Pageable pageable);

    public Page <Post> findAll(Pageable pageable);
    //    public Page <Post> findById(Long id, Pageable pageable);
    public Post findPrevPost(Post post);
    public Post findNextPost(Post post);
    public Post modify(Long id, PostDto postDto, Authentication authentication) throws Exception;
    public void hitsPlus(Post post);


//    시큐리티 관련
//    public Post modify(Long id, PostDto PostDto, Authentication authentication) throws Exception;

//    public Pageable noticePaging(Pageable pageable);

//    public Long create(PostDto postDto, Authentication authentication);
//    public void delete(Long inquireId, Authentication authentication) throws Exception;

    public Post findById(Long id);
}
