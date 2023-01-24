package com.project.team.plice.service.classes;

import com.project.team.plice.domain.post.Post;
import com.project.team.plice.domain.post.Reply;
import com.project.team.plice.dto.post.ReplyDto;
import com.project.team.plice.repository.member.MemberRepository;
import com.project.team.plice.repository.post.ReplyRepository;
import com.project.team.plice.service.interfaces.PostService;
import com.project.team.plice.service.interfaces.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final PostService postService;
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<Reply> findAll() {
        return replyRepository.findAll();
    }

    @Override
    public void replySave(ReplyDto replyDto, Authentication authentication) {
        Post post = postService.findPostById(replyDto.getPostId());

        if (replyDto.getParentId() != null) {
            Reply parent = replyRepository.findById(replyDto.getParentId()).get();
            replyDto.setParent(parent);
            replyDto.setLevel(parent.getLevel() + 1);
            replyDto.setContent("<strong>@" + parent.getMember().getNickname() + "</strong> " + replyDto.getContent());
        }
        replyDto.setPost(post);
        replyDto.setMember(memberRepository.findByPhone(authentication.getName()).get());
        replyRepository.save(replyDto.toEntity());
    }

    @Override
    public void replyModify(ReplyDto replyDto) {
        Reply reply = replyRepository.findById(replyDto.getReplyId()).get();
        reply.changeContent(replyDto.getContent());

        if (reply.getParent() != null) {
            Reply parent = replyRepository.findById(reply.getParent().getId()).get();
            reply.changeContent("<strong>@" + parent.getMember().getNickname() + "</strong> " + replyDto.getContent());
        }
        replyRepository.save(reply);
    }

    @Override
    public void replyDelete(Long id) {
        Reply reply = replyRepository.findById(id).get();
        if (reply.getChildren() == null || reply.getChildren().size() == 0) {
            replyRepository.delete(reply);
        } else {
            reply.delete();
            replyRepository.save(reply);
        }
    }
}
