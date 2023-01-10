package com.project.team.plice.controller;

import com.project.team.plice.domain.chat.Chat;
import com.project.team.plice.domain.chat.ChatMessage;
import com.project.team.plice.domain.chat.ChatRoom;
import com.project.team.plice.domain.member.Member;
import com.project.team.plice.service.ChatServiceImpl;
import com.project.team.plice.service.MemberServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.awt.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController extends Socket {
    private final MemberServiceImpl memberService;
    private final SimpMessagingTemplate template;
    private final ChatServiceImpl chatService;

    @GetMapping("/chat")
    public String chat(Model model) {
        Member member = memberService.findByPhone("01012345678");
        List<ChatRoom> chatRooms = chatService.findChatRoomsByMember(member);
        model.addAttribute("chatRooms", chatRooms);
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
}