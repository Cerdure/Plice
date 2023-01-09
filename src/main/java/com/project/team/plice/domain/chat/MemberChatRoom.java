package com.project.team.plice.domain.chat;

import com.project.team.plice.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class MemberChatRoom {

    @Id @GeneratedValue
    @Column(name = "member_chat_room_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    private LocalDateTime regDate;

    @PrePersist
    public void create() {
        this.regDate = this.regDate == null ? LocalDateTime.now() : this.regDate;
    }

    @Builder
    public MemberChatRoom(Long id, Member member, ChatRoom chatRoom, LocalDateTime regDate) {
        this.id = id;
        this.member = member;
        this.chatRoom = chatRoom;
        this.regDate = regDate;
    }
}
