package com.project.team.plice.repository.chat;

import com.project.team.plice.domain.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    public ChatRoom findByApartDataId(Long apartDataId);
    public List<ChatRoom> findTop3ByOrderByMemberCountDesc();
}
