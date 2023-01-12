package com.project.team.plice.repository.chat;

import com.project.team.plice.domain.chat.ChatRoom;
import com.project.team.plice.domain.chat.MemberChatRoom;
import com.project.team.plice.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberChatRoomRepository extends JpaRepository<MemberChatRoom, Long> {
    public List<MemberChatRoom> findByMember(Member member);
    public List<MemberChatRoom> findByChatRoom(ChatRoom chatRoom);
}
