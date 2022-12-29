package com.project.team.plice.dto.chat;

import com.project.team.plice.domain.chat.Chat;
import com.project.team.plice.domain.chat.ChatRoom;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatDto {

    private Long id;
    private ChatRoom chatRoom;
    private String content;
    private LocalDateTime regDate;

    @Builder
    public ChatDto(Long id, ChatRoom chatRoom, String content, LocalDateTime regDate) {
        this.id = id;
        this.chatRoom = chatRoom;
        this.content = content;
        this.regDate = regDate;
    }

    public Chat toEntity(){
        return Chat.builder()
                .id(this.id)
                .chatRoom(this.chatRoom)
                .content(this.content)
                .regDate(this.regDate)
                .build();
    }
}
