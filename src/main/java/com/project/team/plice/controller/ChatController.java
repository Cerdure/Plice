package com.project.team.plice.controller;

import com.project.team.plice.domain.chat.Chat;
import com.project.team.plice.domain.chat.ChatMessage;
import com.project.team.plice.domain.chat.ChatRoom;
import com.project.team.plice.domain.member.Member;
import com.project.team.plice.dto.chat.ChatRoomDto;
import com.project.team.plice.dto.data.AddressDataDto;
import com.project.team.plice.dto.data.ApartDataDto;
import com.project.team.plice.service.ChatServiceImpl;
import com.project.team.plice.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.*;
import java.net.Socket;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController extends Socket {
    private final MemberServiceImpl memberService;
    private final SimpMessagingTemplate template;
    private final ChatServiceImpl chatService;

    @GetMapping("/chat")
    public String chat(Authentication authentication, Model model) {
        if(authentication != null){
            String phone = authentication.getName();
            Member member = memberService.findByPhone(phone);
            List<ChatRoom> chatRooms = chatService.findChatRoomsByMember(member);
            model.addAttribute("myChatRooms", chatRooms);
        }
        return "chat";
    }

    @MessageMapping("/chat/in")
    public void joinChatRoom(ChatMessage message) {
        Member member = memberService.findByPhone(message.getPhone());
        message.setMessage(member.getNickname() + "님이 등장했습니다.");
        message.setType(TrayIcon.MessageType.INFO);
        template.convertAndSend("/subscribe/chat/room/" + message.getChatRoomId(), message);
    }

    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        Chat chat = chatService.chatSave(message);
        message.setType(TrayIcon.MessageType.NONE);
        message.setRegDate(chat.getRegDate());
        message.setMember(chat.getMember());
        template.convertAndSend("/subscribe/chat/room/" + message.getChatRoomId(), message);
    }

    @GetMapping("/chat/input-search")
    public String homeSearchInput(@RequestParam(name = "inputVal") String inputVal, Model model) {
        if(inputVal!=""){
            List<ChatRoomDto> chatRooms = chatService.findChatRoomsByAddressOrName("",inputVal);
            if(chatRooms != null){
                chatRooms.forEach(e -> e.coincidenceHighlight(inputVal));
            }
            model.addAttribute("chatRooms", chatRooms);
        }
        return "chat :: #search-input-results";
    }
}