package com.project.team.plice.dto.chat;

import com.project.team.plice.domain.chat.Chat;
import com.project.team.plice.domain.chat.ChatRoom;
import com.project.team.plice.domain.member.Member;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatDto {

    private Long id;
    private ChatRoom chatRoom;
    private Member member;
    private String content;
    private LocalDateTime regDate;

    @Builder
    public ChatDto(Long id, ChatRoom chatRoom, Member member, String content, LocalDateTime regDate) {
        this.id = id;
        this.chatRoom = chatRoom;
        this.member = member;
        this.content = content;
        this.regDate = regDate;
    }

    public Chat toEntity(){
        return Chat.builder()
                .id(this.id)
                .chatRoom(this.chatRoom)
                .member(this.member)
                .content(this.content)
                .regDate(this.regDate)
                .build();
    }
}
