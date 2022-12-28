package com.project.team.plice.dto.chat;

import com.project.team.plice.domain.Apart;
import com.project.team.plice.domain.chat.ChatRoom;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatRoomDto {

    private Long id;
    private Apart apart;
    private Integer memberCount;
    private Integer chatCount;
    private LocalDateTime regDate;

    @Builder
    public ChatRoomDto(Long id, Apart apart, Integer memberCount, Integer chatCount, LocalDateTime regDate) {
        this.id = id;
        this.apart = apart;
        this.memberCount = memberCount;
        this.chatCount = chatCount;
        this.regDate = regDate;
    }

    public ChatRoom toEntity(){
        return ChatRoom.builder()
                .id(this.id)
                .apart(this.apart)
                .memberCount(this.memberCount)
                .chatCount(this.chatCount)
                .regDate(this.regDate)
                .build();
    }
}
