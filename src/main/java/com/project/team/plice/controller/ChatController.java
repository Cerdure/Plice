package com.project.team.plice.controller;

import com.project.team.plice.domain.chat.Chat;
import com.project.team.plice.domain.chat.ChatRoom;
import com.project.team.plice.domain.member.Member;
import com.project.team.plice.dto.chat.ChatDto;
import com.project.team.plice.dto.chat.ChatRoomDto;
import com.project.team.plice.service.interfaces.ChatService;
import com.project.team.plice.service.interfaces.MemberService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController extends Socket {
    private final MemberService memberService;
    private final ChatService chatService;
    private final SimpMessagingTemplate template;

    @GetMapping("/chat")
    public String chat(Authentication authentication, Model model) {
        if(authentication != null){
            model.addAttribute("myChatRooms", chatService.myRoomsResolver(authentication));
        }
        return "chat";
    }

    @GetMapping("/chat/my-rooms")
    public String updateMyRooms(Authentication authentication, Model model) {
        if(authentication != null){
            model.addAttribute("myChatRooms", chatService.myRoomsResolver(authentication));
        }
        return "chat :: #my-rooms";
    }

    @GetMapping("/chat/update")
    public String updateChats(@RequestParam("roomId") String roomId, Model model) {
        ChatRoomDto chatRoomDto = chatService.findChatRoomById(roomId).toDto();
        chatService.setLastChat(chatRoomDto);
        model.addAttribute("chatsMap", chatService.chatsGroupByDay(roomId));
        model.addAttribute("lastChat", chatRoomDto.getLastChat());
        return "chat :: #chat";
    }

    @GetMapping("/chat/in")
    @ResponseBody
    public Integer joinChatRoom(@RequestParam("roomId") String roomId, Authentication authentication) throws Exception{
        if(authentication != null){
            return chatService.chatRoomJoin(roomId, authentication);
        } else {
            throw new IllegalStateException("로그인 상태가 아닙니다.");
        }
    }

    @GetMapping("/chat/input-search")
    public String homeSearchInput(@RequestParam(name = "inputVal") String inputVal, Model model) {
        if(inputVal!=""){
            model.addAttribute("chatRooms", chatService.highlightChatRooms(inputVal));
        }
        return "chat :: #search-input-results";
    }

    @GetMapping("/chat/login-check")
    @ResponseBody
    public String loginCheck(Authentication authentication) {
        return authentication == null ? "no" : "ok";
    }

    @GetMapping("/chat/my-rooms/count")
    @ResponseBody
    public Integer numberOfMyRooms(Authentication authentication) {
        return chatService.numberOfMyRooms(authentication);
    }

    @MessageMapping("/chat/in-message")
    public void joinChatRoomMessage(ChatDto message, Authentication authentication) {
        Member member = memberService.findByPhone(authentication.getName());
        message.setMessage(member.getNickname() + "님이 등장했습니다.");
        message.setType(TrayIcon.MessageType.INFO);
        chatService.chatSave(message, member);
        message.setMemberCount(chatService.findMemberCount(message.getChatRoomId()));
        template.convertAndSend("/subscribe/chat/room/" + message.getChatRoomId(), message);
    }

    @MessageMapping("/chat/message")
    public void message(ChatDto message, Authentication authentication) {
        Member member = memberService.findByPhone(authentication.getName());
        message.setMember(member);
        message.setType(TrayIcon.MessageType.NONE);
        Chat chat = chatService.chatSave(message, member);
        message.setRegDate(chat.getRegDate());
        template.convertAndSend("/subscribe/chat/room/" + message.getChatRoomId(), message);
    }

    @GetMapping("/chat/room-exit")
    @ResponseBody
    public String roomExit(@RequestParam("roomId") String roomId, Authentication authentication) {
        if(authentication != null){
            Member member = memberService.findByPhone(authentication.getName());
            chatService.chatRoomExit(member, roomId);
            ChatDto message = new ChatDto();
            message.setChatRoomId(roomId);
            message.setMessage(member.getNickname() + "님이 채팅방을 나갔습니다.");
            message.setType(TrayIcon.MessageType.INFO);
            message.setMemberCount(chatService.findMemberCount(message.getChatRoomId()));
            template.convertAndSend("/subscribe/chat/room/" + roomId, message);
            chatService.chatSave(message, member);
            return "exit";
        }
        return "fail";
    }

    @GetMapping("/login-info")
    @ResponseBody
    public Member loginInfo(Authentication authentication){
        return memberService.findByPhone(authentication.getName());
    }
}