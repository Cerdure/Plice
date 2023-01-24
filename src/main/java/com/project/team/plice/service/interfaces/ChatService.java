package com.project.team.plice.service.interfaces;

import com.project.team.plice.domain.chat.Chat;
import com.project.team.plice.domain.chat.ChatRoom;
import com.project.team.plice.domain.chat.MemberChatRoom;
import com.project.team.plice.domain.member.Member;
import com.project.team.plice.dto.chat.ChatDto;
import com.project.team.plice.dto.chat.ChatRoomDto;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Map;

public interface ChatService {
    public List<ChatRoomDto> myRoomsResolver(Authentication authentication);
    public void setLastChat(ChatRoomDto chatRoomDtos);
    public void setMembers(List<ChatRoomDto> chatRoomDtos);
    public void chatReport(Long chatId, String reason, Authentication authentication);

    public Chat findChatById(Long chatId);
    public Integer findMemberCount(String roomId);
    public List<Chat> findChatsByRoomId(String roomId);
    public ChatRoom findChatRoomById(String roomId);
    public ChatRoom findChatRoomByChatId(Long chatId);
    public List<ChatRoom> findChatRoomsByMember(Member member);
    public List<Member> findMemberByChatRoom(ChatRoom chatRoom);
    public List<MemberChatRoom> findMemberChatRoomByRoom(ChatRoom chatRoom);
    public List<MemberChatRoom> findMemberChatRoomByMember(Member member);
    public List<ChatRoomDto> findChatRoomsByAddressOrName(String address, String name);
    public List<ChatRoom> findTop3ChatRooms();

    public Chat chatSave(ChatDto message, Member member);
    public void chatRoomSave(ChatRoom chatRoom);
    public void memberChatRoomSave(MemberChatRoom memberChatRoom);

    public Integer chatRoomJoin(String roomId, Authentication authentication) throws Exception;
    public boolean isJoined(Member member, ChatRoom chatRoom);
    public void chatRoomExit(Member member, String roomId);

    public Map<Integer,List<Chat>> chatsGroupByDay(String roomId);
    public Map<Integer,List<Chat>> chatsGroupByDay(Long chatId);

    public List<ChatRoomDto> highlightChatRooms(String inputVal);
    public Integer numberOfMyRooms(Authentication authentication);
    public Integer numberOfMembersOnChat();
}
