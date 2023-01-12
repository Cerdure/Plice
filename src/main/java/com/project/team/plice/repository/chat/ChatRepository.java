package com.project.team.plice.repository.chat;

import com.project.team.plice.domain.chat.Chat;
import com.project.team.plice.domain.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    public List<Chat> findByChatRoom(ChatRoom chatRoom);
}
