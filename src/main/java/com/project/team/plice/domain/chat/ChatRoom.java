package com.project.team.plice.domain.chat;

import com.project.team.plice.domain.data.ApartData;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class ChatRoom {

    @Id @GeneratedValue
    @Column(name = "chat_room_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "apart_id")
    private ApartData apartData;

    @OneToMany(mappedBy = "chatRoom")
    private List<Chat> chats;

    private Integer memberCount;

    private Integer chatCount;

    private LocalDateTime regDate;

    @Builder
    public ChatRoom(Long id, ApartData apartData, List<Chat> chats, Integer memberCount, Integer chatCount, LocalDateTime regDate) {
        this.id = id;
        this.apartData = apartData;
        this.chats = chats;
        this.memberCount = memberCount;
        this.chatCount = chatCount;
        this.regDate = regDate;
    }
}
