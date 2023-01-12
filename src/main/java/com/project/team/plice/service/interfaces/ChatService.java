package com.project.team.plice.service.interfaces;

import com.project.team.plice.domain.chat.Chat;
import com.project.team.plice.dto.chat.ChatDto;
import com.project.team.plice.domain.chat.ChatRoom;
import com.project.team.plice.domain.chat.MemberChatRoom;
import com.project.team.plice.domain.member.Member;
import com.project.team.plice.dto.chat.ChatRoomDto;

import java.util.List;

public interface ChatService {
    public List<Chat> findChatsByRoomId(String roomId);
    public ChatRoom findChatRoomById(String roomId);
    public List<ChatRoom> findChatRoomsByMember(Member member);
    public List<MemberChatRoom> findMemberChatRoomByRoom(ChatRoom chatRoom);
    public List<MemberChatRoom> findMemberChatRoomByMember(Member member);
    public List<ChatRoomDto> findChatRoomsByAddressOrName(String address, String name);
    public Chat chatSave(ChatDto message, Member member);
    public void chatRoomSave(ChatRoom chatRoom);
    public void memberChatRoomSave(MemberChatRoom memberChatRoom);
    public String chatRoomJoin(Member member, String roomId) throws Exception;
    public boolean isJoined(Member member, ChatRoom chatRoom);
    public void chatRoomExit(Member member, String roomId);
    public Integer findMemberCount(String roomId);
}
