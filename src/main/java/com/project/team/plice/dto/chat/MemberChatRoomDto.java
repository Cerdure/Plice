package com.project.team.plice.dto.chat;

import com.project.team.plice.domain.chat.ChatRoom;
import com.project.team.plice.domain.chat.MemberChatRoom;
import com.project.team.plice.domain.member.Member;
import lombok.Builder;
import lombok.Data;

@Data
public class MemberChatRoomDto {

    private Long id;
    private Member member;
    private ChatRoom chatRoom;

    @Builder
    public MemberChatRoomDto(Long id, Member member, ChatRoom chatRoom) {
        this.id = id;
        this.member = member;
        this.chatRoom = chatRoom;
    }

    public MemberChatRoom toEntity() {
        return MemberChatRoom.builder()
                .id(this.id)
                .member(this.member)
                .chatRoom(this.chatRoom)
                .build();
    }
}
