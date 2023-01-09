package com.project.team.plice.dto.chat;

import com.project.team.plice.domain.chat.ChatRoom;
import com.project.team.plice.domain.data.ApartData;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatRoomDto {

    private String id;
    private ApartData apartData;
    private Integer memberCount;
    private Integer chatCount;
    private LocalDateTime regDate;

    @Builder
    public ChatRoomDto(String id, ApartData apartData, Integer memberCount, Integer chatCount, LocalDateTime regDate) {
        this.id = id;
        this.apartData = apartData;
        this.memberCount = memberCount;
        this.chatCount = chatCount;
        this.regDate = regDate;
    }

    public ChatRoom toEntity(){
        return ChatRoom.builder()
                .id(this.id)
                .apartData(this.apartData)
                .memberCount(this.memberCount)
                .chatCount(this.chatCount)
                .regDate(this.regDate)
                .build();
    }
}
