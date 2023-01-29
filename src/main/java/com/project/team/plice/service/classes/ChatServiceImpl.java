package com.project.team.plice.service.classes;

import com.project.team.plice.domain.admin.Report;
import com.project.team.plice.domain.chat.Chat;
import com.project.team.plice.domain.chat.ChatRoom;
import com.project.team.plice.domain.chat.MemberChatRoom;
import com.project.team.plice.domain.member.Member;
import com.project.team.plice.dto.chat.ChatDto;
import com.project.team.plice.dto.chat.ChatRoomDto;
import com.project.team.plice.repository.admin.ReportRepository;
import com.project.team.plice.repository.chat.ChatRepository;
import com.project.team.plice.repository.chat.ChatRoomRepository;
import com.project.team.plice.repository.chat.MemberChatRoomRepository;
import com.project.team.plice.repository.data.ApartDataRepository;
import com.project.team.plice.service.interfaces.ChatService;
import com.project.team.plice.service.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final MemberService memberService;
    private final MemberChatRoomRepository memberChatRoomRepository;
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ApartDataRepository apartDataRepository;
    private final ReportRepository reportRepository;

    @Override
    public List<ChatRoomDto> myRoomsResolver(Authentication authentication) {
        List<ChatRoom> chatRooms = findChatRoomsByMember(memberService.findMember(authentication));
        List<ChatRoomDto> chatRoomDtos = chatRooms.stream().map(chatRoom -> chatRoom.toDto())
                .collect(Collectors.toList());
        chatRoomDtos.forEach(chatRoomDto -> setLastChat(chatRoomDto));
        setMembers(chatRoomDtos);
        return chatRoomDtos;
    }

    @Override
    public void setLastChat(ChatRoomDto chatRoomDto) {
        List<Chat> chats = chatRoomDto.getChats();
        if (chats.size() != 0) {
            int i = chats.size() - 1;
            while (true) {
                if (i < 0) {
                    chatRoomDto.setLastChat(Chat.builder().content("아직 채팅이 없습니다.").build());
                    break;
                } else if (!chats.get(i).getType().equals("INFO")) {
                    chatRoomDto.setLastChat(chats.get(i));
                    break;
                }
                i--;
            }
        }
    }

    @Override
    public void setMembers(List<ChatRoomDto> chatRoomDtos) {
        chatRoomDtos.forEach(chatRoomDto -> {
            List<Member> members = findMemberByChatRoom(chatRoomDto.toEntity());
            chatRoomDto.setMembers(members);
        });
    }

    @Override
    public Chat findChatById(Long chatId) {
        return chatRepository.findById(chatId).get();
    }

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
    public ChatRoom findChatRoomByChatId(Long chatId) {
        return chatRepository.findById(chatId).get().getChatRoom();
    }

    @Override
    public List<ChatRoom> findChatRoomsByMember(Member member) {
        return memberChatRoomRepository.findByMember(member).stream().map(e -> e.getChatRoom()).collect(Collectors.toList());
    }

    @Override
    public List<Member> findMemberByChatRoom(ChatRoom chatRoom) {
        return memberChatRoomRepository.findByChatRoom(chatRoom).stream().map(e -> e.getMember()).collect(Collectors.toList());
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
        ChatRoom chatRoom = chatRoomRepository.findById(message.getChatRoomId()).get();
        Chat chat = Chat.builder()
                .chatRoom(chatRoom)
                .member(member)
                .content(message.getMessage())
                .regDate(LocalDateTime.now())
                .type(message.getType().toString())
                .build();
        if (message.getType().equals(TrayIcon.MessageType.NONE)) {
            chatRoom.chatCountPlus();
            chatRoomRepository.save(chatRoom);
        }
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
    public Integer chatRoomJoin(String roomId, Authentication authentication) throws Exception {
        Member member = memberService.findMember(authentication);
        ChatRoom chatRoom = findChatRoomById(roomId);
        if (!isJoined(member, chatRoom)) {
            chatRoom.memberCountPlus();
            MemberChatRoom memberChatRoom = MemberChatRoom.builder().chatRoom(chatRoom).member(member).build();
            chatRoomSave(chatRoom);
            memberChatRoomSave(memberChatRoom);
            return chatRoom.getMemberCount();
        }
        throw new Exception("isJoined");
    }

    @Override
    public boolean isJoined(Member member, ChatRoom chatRoom) {
        List<MemberChatRoom> memberChatRooms = findMemberChatRoomByRoom(chatRoom);
        if (memberChatRooms.stream().filter(mcr -> mcr.getMember().getPhone().equals(member.getPhone())).count() == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void chatRoomExit(Member member, String roomId) {
        ChatRoom chatRoom = findChatRoomById(roomId);
        chatRoom.memberCountMinus();
        chatRoomRepository.save(chatRoom);
        List<MemberChatRoom> memberChatRooms = findMemberChatRoomByMember(member);
        memberChatRooms.forEach(m -> {
            if (m.getChatRoom().getId().equals(roomId)) {
                memberChatRoomRepository.delete(m);
            }
        });
    }

    @Override
    public Integer findMemberCount(String roomId) {
        return chatRoomRepository.findById(roomId).get().getMemberCount();
    }

    @Override
    public Map<Integer, List<Chat>> chatsGroupByDay(String roomId) {
        List<Chat> chats = findChatsByRoomId(roomId);
        Map<Integer, List<Chat>> chatsMap = new HashMap<>();
        List<Integer> days = new ArrayList<>();
        for (Chat chat : chats) {
            int day = chat.getRegDate().getDayOfMonth();
            if (!days.contains(day)) {
                days.add(day);
            }
        }
        for (Integer day : days) {
            chatsMap.put(day,
                    chats.stream().filter(chat -> chat.getRegDate().getDayOfMonth() == day)
                            .collect(Collectors.toList()));
        }
        return chatsMap;
    }

    @Override
    public Map<Integer, List<Chat>> chatsGroupByDay(Long chatId) {
        List<Chat> chats = findChatsByRoomId(findChatRoomByChatId(chatId).getId());
        Map<Integer, List<Chat>> chatsMap = new HashMap<>();
        List<Integer> days = new ArrayList<>();
        for (Chat chat : chats) {
            int day = chat.getRegDate().getDayOfMonth();
            if (!days.contains(day)) {
                days.add(day);
            }
        }
        for (Integer day : days) {
            chatsMap.put(day,
                    chats.stream().filter(chat -> chat.getRegDate().getDayOfMonth() == day)
                            .collect(Collectors.toList()));
        }
        return chatsMap;
    }

    @Override
    public List<ChatRoomDto> highlightChatRooms(String inputVal) {
        List<ChatRoomDto> chatRooms = findChatRoomsByAddressOrName("", inputVal);
        if (chatRooms != null) {
            chatRooms.forEach(e -> e.coincidenceHighlight(inputVal));
        }
        return chatRooms;
    }

    @Override
    public Integer numberOfMyRooms(Authentication authentication) {
        List<ChatRoom> chatRooms = findChatRoomsByMember(memberService.findMember(authentication));
        return chatRooms.size();
    }

    @Override
    public Integer numberOfMembersOnChat() {
        Stream<Member> distinctMembers = memberChatRoomRepository.findAll().stream().map(memberChatRoom -> memberChatRoom.getMember()).distinct();
        return Math.toIntExact(distinctMembers.count());
    }

    @Override
    public void chatReport(Long chatId, String reason, Authentication authentication) {
        reportRepository.save(Report.builder()
                .chat(chatRepository.findById(chatId).get())
                .reporter(memberService.findMember(authentication))
                .reason(reason)
                .build());
    }

    @Override
    public List<ChatRoomDto> findChatRoomsByAddressOrName(String address, String name) {
        return apartDataRepository.findByAddressContainingIgnoreCaseAndNameContainingIgnoreCase(address, name)
                .stream().map(apartData -> apartData.toDto()).collect(Collectors.toList())
                .stream().map(apartDataDto -> chatRoomRepository.findByApartDataId(apartDataDto.getId()))
                .map(chatRoom -> chatRoom.toDto()).collect(Collectors.toList());
    }

    @Override
    public List<ChatRoom> findTop3ChatRooms() {
        return chatRoomRepository.findTop3ByOrderByMemberCountDesc();
    }

}
