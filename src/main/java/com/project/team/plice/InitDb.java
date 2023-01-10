package com.project.team.plice;

import com.project.team.plice.domain.chat.ChatRoom;
import com.project.team.plice.domain.chat.MemberChatRoom;
import com.project.team.plice.domain.enums.MemberRole;
import com.project.team.plice.domain.member.Member;
import com.project.team.plice.repository.chat.ChatRoomRepository;
import com.project.team.plice.repository.data.ApartDataRepository;
import com.project.team.plice.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
//        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        private final BCryptPasswordEncoder passwordEncoder;
        private final MemberRepository memberRepository;
        private final ApartDataRepository apartDataRepository;
        private final ChatRoomRepository chatRoomRepository;

        public void dbInit() {

            Member member1 = Member.builder()
                    .name("최유저")
                    .nickname("최유저")
                    .phone("01012345678")
                    .birth("900101")
                    .sex("1")
                    .email("user11@mail.com")
                    .pw(passwordEncoder.encode("1234"))
                    .role(MemberRole.USER)
                    .profileImgPath("/img/images/profile.jpg")
                    .build();

            Member member2 = Member.builder()
                    .name("김유저")
                    .nickname("김유저")
                    .phone("01043214321")
                    .birth("999999")
                    .sex("1")
                    .email("user22@mail.com")
                    .pw(passwordEncoder.encode("1234"))
                    .role(MemberRole.USER)
                    .build();

            em.persist(member1);
            em.persist(member2);


            List<ChatRoom> chatRooms = apartDataRepository.findAll().stream().map(apartData ->
                    ChatRoom.builder().apartData(apartData).build()).collect(Collectors.toList());
            chatRooms.forEach(e -> em.persist(e));


            MemberChatRoom[] memberChatRooms = new MemberChatRoom[3];
            memberChatRooms[0] = MemberChatRoom.builder().member(member1).chatRoom(chatRooms.get(12556)).build();
            memberChatRooms[1] = MemberChatRoom.builder().member(member1).chatRoom(chatRooms.get(13251)).build();
            memberChatRooms[2] = MemberChatRoom.builder().member(member1).chatRoom(chatRooms.get(13826)).build();
            for (MemberChatRoom memberChatRoom : memberChatRooms) {
                em.persist(memberChatRoom);
            }
        }
    }
}