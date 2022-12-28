package com.project.team.plice.domain.chat;

import com.project.team.plice.domain.Apart;
import com.project.team.plice.domain.inquire.Answer;
import com.project.team.plice.domain.inquire.Inquire;
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
    private Apart apart;

    @OneToMany(mappedBy = "chatRoom")
    private List<Chat> chats;

    private Integer memberCount;

    private Integer chatCount;

    private LocalDateTime regDate;

    @Builder
    public ChatRoom(Long id, Apart apart, List<Chat> chats, Integer memberCount, Integer chatCount, LocalDateTime regDate) {
        this.id = id;
        this.apart = apart;
        this.chats = chats;
        this.memberCount = memberCount;
        this.chatCount = chatCount;
        this.regDate = regDate;
    }
}
