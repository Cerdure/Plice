package com.project.team.plice.service;

import com.project.team.plice.domain.chat.Chat;
import com.project.team.plice.domain.chat.ChatMessage;
import com.project.team.plice.domain.chat.ChatRoom;
import com.project.team.plice.domain.chat.MemberChatRoom;
import com.project.team.plice.domain.member.Member;
import com.project.team.plice.repository.chat.ChatRepository;
import com.project.team.plice.repository.chat.ChatRoomRepository;
import com.project.team.plice.repository.chat.MemberChatRoomRepository;
import com.project.team.plice.repository.member.MemberRepository;
import com.project.team.plice.service.interfaces.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final MemberRepository memberRepository;
    private final MemberChatRoomRepository memberChatRoomRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;

    @Override
    public List<ChatRoom> findChatRoomsByMember(Member member) {
        return memberChatRoomRepository.findByMember(member).stream().map(e -> e.getChatRoom()).collect(Collectors.toList());
    }

    @Override
    public Chat chatSave(ChatMessage message) {
        Chat chat = Chat.builder()
                .chatRoom(chatRoomRepository.findById(message.getChatRoomId()).get())
                .member(memberRepository.findByPhone(message.getPhone()).get())
                .content(message.getMessage())
                .regDate(LocalDateTime.now())
                .build();
        chatRepository.save(chat);
        return chat;
    }


}
