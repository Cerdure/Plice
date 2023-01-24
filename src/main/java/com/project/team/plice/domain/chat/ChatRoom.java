package com.project.team.plice.domain.chat;

import com.project.team.plice.domain.data.ApartData;
import com.project.team.plice.dto.chat.ChatRoomDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {

    @Id
    @Column(name = "chat_room_id")
    private String id;

    @OneToOne
    @JoinColumn(name = "apart_data_id")
    private ApartData apartData;

    @OneToMany(mappedBy = "chatRoom")
    private List<Chat> chats;

    private Integer memberCount;

    private Integer chatCount;

    private LocalDateTime regDate;

    @PrePersist
    public void create() {
        this.id = this.id == null ? UUID.randomUUID().toString() : this.id;
        this.memberCount = this.memberCount == null ? 0 : this.memberCount;
        this.chatCount = this.chatCount == null ? 0 : this.chatCount;
        this.regDate = this.regDate == null ? LocalDateTime.now() : this.regDate;
    }

    @Builder
    public ChatRoom(String id, ApartData apartData, List<Chat> chats, Integer memberCount, Integer chatCount, LocalDateTime regDate) {
        this.id = id;
        this.apartData = apartData;
        this.chats = chats;
        this.memberCount = memberCount;
        this.chatCount = chatCount;
        this.regDate = regDate;
    }

    public ChatRoomDto toDto() {
        return ChatRoomDto.builder()
                .id(this.id)
                .apartData(this.apartData)
                .chats(this.chats)
                .chatCount(this.chatCount)
                .memberCount(this.memberCount)
                .regDate(this.regDate)
                .build();
    }

    public void memberCountPlus() {
        this.memberCount++;
    }

    public void memberCountMinus() {
        this.memberCount--;
    }

    public void changeMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }

    public void chatCountPlus() {
        this.chatCount++;
    }

    public void chatCountMinus() {
        this.chatCount--;
    }

    public void changechatCount(Integer chatCount) {
        this.chatCount = chatCount;
    }
}
