package com.project.team.plice.domain.chat;

import com.project.team.plice.domain.data.ApartData;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ChatRoom {

    @Id
    @Column(name = "chat_room_id")
    private String id;

    @OneToOne
    @JoinColumn(name = "apart_id")
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

}
