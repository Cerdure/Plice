package com.project.team.plice.domain.chat;

import com.project.team.plice.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private String chatRoomId;
    private String phone;
    private String message;
    private LocalDateTime regDate;
    private TrayIcon.MessageType type;
    private boolean isMine;
    private Member member;

}