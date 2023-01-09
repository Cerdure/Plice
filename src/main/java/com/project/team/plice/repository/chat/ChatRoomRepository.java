package com.project.team.plice.repository.chat;

import com.project.team.plice.domain.chat.ChatRoom;
import com.project.team.plice.domain.data.ApartData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    public ChatRoom findByApartDataId(Long apartDataId);
}
