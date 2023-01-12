package com.project.team.plice.controller;

import com.project.team.plice.domain.chat.Chat;
import com.project.team.plice.domain.chat.ChatMessage;
import com.project.team.plice.domain.chat.ChatRoom;
import com.project.team.plice.domain.chat.MemberChatRoom;
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
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping("/chat/in")
    @ResponseBody
    public String joinChatRoom(@RequestParam("roomId") String roomId, Authentication authentication) throws Exception{
        if(authentication != null){
            Member member = memberService.findByPhone(authentication.getName());
            String memberCount = chatService.chatRoomJoin(member, roomId);
            return memberCount;
        } else {
            throw new IllegalStateException("로그인 상태가 아닙니다.");
        }
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

    @GetMapping("/chat/login-check")
    @ResponseBody
    public String loginCheck(Authentication authentication) {
        return authentication == null ? "no" : "ok";
    }

    @GetMapping("/chat/my-rooms")
    public String updateMyRooms(Authentication authentication, Model model) {
        if(authentication != null){
            String phone = authentication.getName();
            Member member = memberService.findByPhone(phone);
            List<ChatRoom> chatRooms = chatService.findChatRoomsByMember(member);
            model.addAttribute("myChatRooms", chatRooms);
        }
        return "chat :: #my-rooms";
    }

    @GetMapping("/chat/room-exit")
    @ResponseBody
    public String roomExit(@RequestParam("roomId") String roomId, Authentication authentication) {
        if(authentication != null){
            Member member = memberService.findByPhone(authentication.getName());
            chatService.chatRoomExit(member, roomId);
            ChatMessage message = new ChatMessage();
            message.setMessage(member.getNickname() + "님이 채팅방을 나갔습니다.");
            message.setType(TrayIcon.MessageType.INFO);
            template.convertAndSend("/subscribe/chat/room/" + roomId, message);
            return "exit";
        }
        return "fail";
    }

    @GetMapping("/chat/my-rooms/count")
    @ResponseBody
    public String myRoomsCount(Authentication authentication) {
        String phone = authentication.getName();
        Member member = memberService.findByPhone(phone);
        List<ChatRoom> chatRooms = chatService.findChatRoomsByMember(member);
        return String.valueOf(chatRooms.size());
    }

    @MessageMapping("/chat/in-message")
    public void joinChatRoomMessage(ChatMessage message, Authentication authentication) {
        Member member = memberService.findByPhone(authentication.getName());
        message.setMessage(member.getNickname() + "님이 등장했습니다.");
        message.setType(TrayIcon.MessageType.INFO);
        template.convertAndSend("/subscribe/chat/room/" + message.getChatRoomId(), message);
    }

    @MessageMapping("/chat/message")
    public void message(ChatMessage message, Authentication authentication) {
        Member member = memberService.findByPhone(authentication.getName());
        Chat chat = chatService.chatSave(message, member);
        message.setType(TrayIcon.MessageType.NONE);
        message.setRegDate(chat.getRegDate());
        message.setMember(member);
        template.convertAndSend("/subscribe/chat/room/" + message.getChatRoomId(), message);
    }
}