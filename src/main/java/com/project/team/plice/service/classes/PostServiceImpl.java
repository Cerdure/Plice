package com.project.team.plice.service.classes;

import com.project.team.plice.domain.member.Member;
import com.project.team.plice.domain.post.Notice;
import com.project.team.plice.domain.post.Post;
import com.project.team.plice.dto.post.NoticeDto;
import com.project.team.plice.dto.post.PostDto;
import com.project.team.plice.repository.post.NoticeRepository;
import com.project.team.plice.repository.post.PostRepository;
import com.project.team.plice.service.interfaces.MemberService;
import com.project.team.plice.service.interfaces.PostService;
import com.project.team.plice.dto.utils.SearchUtils;
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
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final NoticeRepository noticeRepository;
    private final MemberService memberService;

    @Override
    public Page<Post> findAllPost(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 12, Sort.by("id").descending());
        return postRepository.findAll(pageable);
    }

    @Override
    public Post findPostById(Long id) {
        return postRepository.findById(id).get();
    }

    @Override
    public Post findPrevPost(Post post) {
        LocalDateTime regDate = post.getRegDate();
        List<Post> findResult = postRepository.findByRegDateBeforeOrderByRegDateDesc(regDate);
        if (findResult == null || findResult.size() == 0) {
            return post.builder().build();
        } else {
            return findResult.get(0);
        }
    }

    @Override
    public Post findNextPost(Post post) {
        LocalDateTime regDate = post.getRegDate();
        List<Post> findResult = postRepository.findByRegDateAfter(regDate);
        if (findResult == null || findResult.size() == 0) {
            return post.builder().build();

        } else {
            return findResult.get(0);
        }
    }

    @Override
    public Page<Post> searchPost(SearchUtils searchUtils, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 12, Sort.by("id").descending());

        String keyword = searchUtils.getKeyword();

        switch (searchUtils.getSearchBy()) {
            case "id":
                return postRepository.findById(Long.parseLong(keyword), pageable);
            case "nickname":
                return postRepository.findByMemberNicknameContainsIgnoreCase(keyword, pageable);
            case "title":
                return postRepository.findByTitleContainingIgnoreCase(keyword, pageable);
            case "content":
                return postRepository.findByContentContainingIgnoreCase(keyword, pageable);

            default: throw new NoSuchElementException("일치하는 검색 유형이 없습니다.");
        }
    }

    @Override
    public void savePost(PostDto postDto, Authentication authentication) {
        Member member = memberService.findMember(authentication);
        postDto.setMember(member);
        postDto.setMemberNickname(member.getNickname());
        postRepository.save(postDto.toEntity());
    }

    @Override
    public void updatePost(PostDto postDto) {
        Post post = postRepository.findById(postDto.getId()).get();
        post.changeTitle(postDto.getTitle());
        post.changeContent(postDto.getContent());
        postRepository.save(post);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.delete(postRepository.findById(id).get());
    }

    @Override
    public void postHitsPlus(Long id) {
        Post post = postRepository.findById(id).get();
        post.hitsPlus();
        postRepository.save(post);
    }


    @Override
    public Page<Notice> findAllNotice(Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 12, Sort.by("id").descending());
        return noticeRepository.findAll(pageable);
    }

    @Override
    public Notice findNoticeById(Long id) {
        return noticeRepository.findById(id).get();
    }

    @Override
    public Page<Notice> searchNotice(SearchUtils searchUtils, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 12, Sort.by("id").descending());
        String keyword = searchUtils.getKeyword();
        switch (searchUtils.getSearchBy()) {
            case "id":
                return noticeRepository.findById(Long.parseLong(keyword), pageable);
            case "nickname":
                return noticeRepository.findByMemberNicknameContainsIgnoreCase(keyword, pageable);
            case "title":
                return noticeRepository.findByTitleContainingIgnoreCase(keyword, pageable);
            case "content":
                return noticeRepository.findByContentContainingIgnoreCase(keyword, pageable);
        }
        throw new NoSuchElementException("일치하는 검색 유형이 없습니다.");
    }

    @Override
    public Notice findPrevNotice(Notice notice) {
        LocalDateTime regDate = notice.getRegDate();
        List<Notice> findResult = noticeRepository.findByRegDateBeforeOrderByRegDateDesc(regDate);
        if (findResult == null || findResult.size() == 0) {
            return notice.builder().build();
        } else {
            return findResult.get(0);
        }
    }

    @Override
    public Notice findNextNotice(Notice notice) {
        LocalDateTime regDate = notice.getRegDate();
        List<Notice> findResult = noticeRepository.findByRegDateAfter(regDate);
        if (findResult == null || findResult.size() == 0) {
            return notice.builder().build();

        } else {
            return findResult.get(0);
        }
    }

    @Override
    public void saveNotice(NoticeDto noticeDto, Authentication authentication) {
        Member member = memberService.findMember(authentication);
        noticeDto.setMember(member);
        noticeDto.setMemberNickname(member.getNickname());
        noticeRepository.save(noticeDto.toEntity());
    }

    @Override
    public void updateNotice(NoticeDto noticeDto) {
        Notice notice = noticeRepository.findById(noticeDto.getId()).get();
        notice.changeTitle(noticeDto.getTitle());
        notice.changeContent(noticeDto.getContent());
        noticeRepository.save(notice);
    }

    @Override
    public void deleteNotice(Long id) {
        noticeRepository.delete(noticeRepository.findById(id).get());
    }

    @Override
    public void noticeHitsPlus(Long id) {
        Notice notice = noticeRepository.findById(id).get();
        notice.hitsPlus();
        noticeRepository.save(notice);
    }

    @Override
    public List<Notice> findLastNotices() {
        return noticeRepository.findTop5ByOrderByRegDateDesc();
    }

}
