package com.project.team.plice.service.interfaces;

import com.project.team.plice.domain.post.Notice;
import com.project.team.plice.domain.post.Post;
import com.project.team.plice.dto.post.NoticeDto;
import com.project.team.plice.dto.post.PostDto;
import com.project.team.plice.dto.utils.SearchUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface PostService {
    public Page<Post> findAllPost(Pageable pageable);
    public Post findPostById(Long id);
    public Post findPrevPost(Post post);
    public Post findNextPost(Post post);
    public Page<Post> searchPost(SearchUtils searchUtils, Pageable pageable);
    public void savePost(PostDto postDto, Authentication authentication);
    public void updatePost(PostDto postDto);
    public void deletePost(Long id);
    public void postHitsPlus(Long id);

    public Page <Notice> findAllNotice(Pageable pageable);
    public Notice findNoticeById(Long id);
    public Page<Notice> searchNotice(SearchUtils searchUtils, Pageable pageable);
    public void saveNotice(NoticeDto noticeDto, Authentication authentication);
    public void updateNotice(NoticeDto noticeDto);
    public void deleteNotice(Long id);
    public Notice findPrevNotice(Notice notice);
    public Notice findNextNotice(Notice notice);
    public void noticeHitsPlus(Long id);
    public List<Notice> findLastNotices();
}
