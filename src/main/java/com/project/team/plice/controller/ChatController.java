package com.project.team.plice.controller;

import com.project.team.plice.domain.chat.Chat;
import com.project.team.plice.dto.DataUtils;
import com.project.team.plice.dto.chat.ChatDto;
import com.project.team.plice.domain.chat.ChatRoom;
import com.project.team.plice.domain.member.Member;
import com.project.team.plice.dto.chat.ChatRoomDto;
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

    @GetMapping("/chat/update")
    public String updateChats(@RequestParam("roomId") String roomId, Model model) {
        List<Chat> chats = chatService.findChatsByRoomId(roomId);
        model.addAttribute("chats", chats);
        return "chat :: #chat-box";
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