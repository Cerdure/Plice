package com.project.team.plice.service;

import com.project.team.plice.domain.chat.Chat;
import com.project.team.plice.dto.chat.ChatDto;
import com.project.team.plice.domain.chat.ChatRoom;
import com.project.team.plice.domain.chat.MemberChatRoom;
import com.project.team.plice.domain.member.Member;
import com.project.team.plice.dto.chat.ChatRoomDto;
import com.project.team.plice.repository.chat.ChatRepository;
import com.project.team.plice.repository.chat.ChatRoomRepository;
import com.project.team.plice.repository.chat.MemberChatRoomRepository;
import com.project.team.plice.repository.data.ApartDataRepository;
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
    private final ApartDataRepository apartDataRepository;

    @Override
    public List<Chat> findChatsByRoomId(String roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).get();
        return chatRepository.findByChatRoom(chatRoom);
    }

    @Override
    public ChatRoom findChatRoomById(String roomId) {
        return chatRoomRepository.findById(roomId).get();
    }

    @Override
    public List<ChatRoom> findChatRoomsByMember(Member member) {
        return memberChatRoomRepository.findByMember(member).stream().map(e -> e.getChatRoom()).collect(Collectors.toList());
    }

    @Override
    public List<MemberChatRoom> findMemberChatRoomByRoom(ChatRoom chatRoom) {
        return memberChatRoomRepository.findByChatRoom(chatRoom);
    }

    @Override
    public List<MemberChatRoom> findMemberChatRoomByMember(Member member) {
        return memberChatRoomRepository.findByMember(member);
    }


    @Override
    public Chat chatSave(ChatDto message, Member member) {
        Chat chat = Chat.builder()
                .chatRoom(chatRoomRepository.findById(message.getChatRoomId()).get())
                .member(member)
                .content(message.getMessage())
                .regDate(LocalDateTime.now())
                .type(message.getType().toString())
                .build();
        chatRepository.save(chat);
        return chat;
    }

    @Override
    public void chatRoomSave(ChatRoom chatRoom) {
        chatRoomRepository.save(chatRoom);
    }

    @Override
    public void memberChatRoomSave(MemberChatRoom memberChatRoom) {
        memberChatRoomRepository.save(memberChatRoom);
    }

    @Override
    public String chatRoomJoin(Member member, String roomId) throws Exception {
        ChatRoom chatRoom = findChatRoomById(roomId);
        if(!isJoined(member, chatRoom)){
            chatRoom.memberCountPlus();
            MemberChatRoom memberChatRoom = MemberChatRoom.builder().chatRoom(chatRoom).member(member).build();
            chatRoomSave(chatRoom);
            memberChatRoomSave(memberChatRoom);
            return chatRoom.getMemberCount().toString();
        }
        throw new Exception("isJoined");
    }

    @Override
    public boolean isJoined(Member member, ChatRoom chatRoom) {
        List<MemberChatRoom> memberChatRooms = findMemberChatRoomByRoom(chatRoom);
        if(memberChatRooms.stream().filter(mcr -> mcr.getMember().getPhone().equals(member.getPhone())).count() == 0){
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void chatRoomExit(Member member, String roomId) {
        System.out.println("chatRoomExit  roomId = " + roomId);
        ChatRoom chatRoom = findChatRoomById(roomId);
        chatRoom.memberCountMinus();
        chatRoomRepository.save(chatRoom);
        List<MemberChatRoom> memberChatRooms = findMemberChatRoomByMember(member);
        memberChatRooms.forEach(m -> {
            if(m.getChatRoom().getId().equals(roomId)){
                memberChatRoomRepository.delete(m);
            }
        });
    }

    @Override
    public Integer findMemberCount(String roomId) {
        return chatRoomRepository.findById(roomId).get().getMemberCount();
    }

    @Override
    public List<ChatRoomDto> findChatRoomsByAddressOrName(String address, String name) {
        return apartDataRepository.findByAddressContainingIgnoreCaseAndNameContainingIgnoreCase(address, name)
                .stream().map(apartData -> apartData.toDto()).collect(Collectors.toList())
                .stream().map(apartDataDto -> chatRoomRepository.findByApartDataId(apartDataDto.getId()))
                .map(chatRoom -> chatRoom.toDto()).collect(Collectors.toList());
    }

}
