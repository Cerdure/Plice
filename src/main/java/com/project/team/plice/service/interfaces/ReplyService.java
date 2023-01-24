package com.project.team.plice.service.interfaces;

import com.project.team.plice.domain.post.Reply;
import com.project.team.plice.dto.post.ReplyDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ReplyService {
    public List<Reply> findAll();
    public void replySave(ReplyDto replyDto, Authentication authentication);
    public void replyModify(ReplyDto replyDto);
    public void replyDelete(Long id);
}
