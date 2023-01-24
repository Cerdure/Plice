package com.project.team.plice.dto.chat;

import com.project.team.plice.domain.chat.Chat;
import com.project.team.plice.domain.chat.ChatRoom;
import com.project.team.plice.domain.data.ApartData;
import com.project.team.plice.domain.member.Member;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ChatRoomDto {

    private String id;
    private ApartData apartData;
    private List<Chat> chats;
    private Integer memberCount;
    private Integer chatCount;
    private String highlightName;
    private Chat lastChat;
    private List<Member> members;
    private LocalDateTime regDate;

    @Builder
    public ChatRoomDto(String id, ApartData apartData, List<Chat> chats, Integer memberCount, Integer chatCount, String highlightName, Chat lastChat, List<Member> members, LocalDateTime regDate) {
        this.id = id;
        this.apartData = apartData;
        this.chats = chats;
        this.memberCount = memberCount;
        this.chatCount = chatCount;
        this.highlightName = highlightName;
        this.lastChat = lastChat;
        this.members = members;
        this.regDate = regDate;
    }

    public ChatRoom toEntity() {
        return ChatRoom.builder()
                .id(this.id)
                .chats(this.chats)
                .apartData(this.apartData)
                .memberCount(this.memberCount)
                .chatCount(this.chatCount)
                .regDate(this.regDate)
                .build();
    }

    public void coincidenceHighlight(String inputVal) {
        inputVal = inputVal.toUpperCase();
        int startIndex = this.apartData.getName().toUpperCase().indexOf(inputVal);
        int endIndex = startIndex + inputVal.length();
        String coincidenceStr = "<strong>" + this.apartData.getName().substring(startIndex, endIndex) + "</strong>";
        String prevStr = this.apartData.getName().substring(0, startIndex);
        String nextStr = this.apartData.getName().substring(endIndex);
        this.highlightName = prevStr + coincidenceStr + nextStr;
    }
}
