package com.project.team.plice.domain.chat;

import com.project.team.plice.domain.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat {

    @Id @GeneratedValue
    @Column(name = "chat_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatRoom_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Size(max = 3000)
    private String content;

    private LocalDateTime regDate;

    private String type;

    @PrePersist
    public void create() {
        this.regDate = this.regDate == null ? LocalDateTime.now() : this.regDate;
    }

    @Builder
    public Chat(Long id, ChatRoom chatRoom, Member member, String content, LocalDateTime regDate, String type) {
        this.id = id;
        this.chatRoom = chatRoom;
        this.member = member;
        this.content = content;
        this.regDate = regDate;
        this.type = type;
    }
}
