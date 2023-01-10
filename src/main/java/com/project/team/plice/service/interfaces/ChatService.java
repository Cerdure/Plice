package com.project.team.plice.service.interfaces;

import com.project.team.plice.domain.chat.Chat;
import com.project.team.plice.domain.chat.ChatMessage;
import com.project.team.plice.domain.chat.ChatRoom;
import com.project.team.plice.domain.member.Member;
import com.project.team.plice.dto.chat.ChatRoomDto;
import com.project.team.plice.dto.data.ApartDataDto;

import java.util.List;

public interface ChatService {
    public List<ChatRoom> findChatRoomsByMember(Member member);
    public Chat chatSave(ChatMessage message);
    public List<ChatRoomDto> findChatRoomsByAddressOrName(String address, String name);
}
