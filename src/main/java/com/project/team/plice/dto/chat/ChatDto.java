package com.project.team.plice.dto.chat;

import com.project.team.plice.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {
    private String chatRoomId;
    private String phone;
    private String message;
    private LocalDateTime regDate;
    private TrayIcon.MessageType type;
    private boolean isMine;
    private Member member;
    private Integer memberCount;
}