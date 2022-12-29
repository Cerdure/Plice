package com.project.team.plice.domain.chat;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "chat_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatRoom_id")
    private ChatRoom chatRoom;

    @Size(max = 3000)
    private String content;

    private LocalDateTime regDate;

    @Builder
    public Chat(Long id, ChatRoom chatRoom, String content, LocalDateTime regDate) {
        this.id = id;
        this.chatRoom = chatRoom;
        this.content = content;
        this.regDate = regDate;
    }
}
