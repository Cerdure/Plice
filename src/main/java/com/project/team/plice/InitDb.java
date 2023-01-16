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
import java.util.ArrayList;
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

            List<Member> members = new ArrayList<>();

            members.add(Member.builder()
                    .name("최유저")
                    .nickname("최유저")
                    .phone("01012345678")
                    .birth("900101")
                    .sex("1")
                    .email("user11@mail.com")
                    .pw(passwordEncoder.encode("1234"))
                    .role(MemberRole.USER)
                    .profileImgPath("/img/images/profile.jpg")
                    .build());

            members.add(Member.builder()
                    .name("김유저")
                    .nickname("김유저")
                    .phone("01043214321")
                    .birth("999999")
                    .sex("1")
                    .email("user22@mail.com")
                    .pw(passwordEncoder.encode("1234"))
                    .role(MemberRole.USER)
                    .build());

            members.add(Member.builder()
                    .name("이유저")
                    .nickname("이유저")
                    .phone("01022223333")
                    .birth("999999")
                    .sex("1")
                    .email("user33@mail.com")
                    .pw(passwordEncoder.encode("1234"))
                    .role(MemberRole.USER)
                    .build());

            members.add(Member.builder()
                    .name("박유저")
                    .nickname("박유저")
                    .phone("01088887777")
                    .birth("999999")
                    .sex("1")
                    .email("user22@mail.com")
                    .pw(passwordEncoder.encode("1234"))
                    .role(MemberRole.USER)
                    .build());

            members.add(Member.builder()
                    .name("진유저")
                    .nickname("진유저")
                    .phone("01077776666")
                    .birth("999999")
                    .sex("1")
                    .email("user22@mail.com")
                    .pw(passwordEncoder.encode("1234"))
                    .role(MemberRole.USER)
                    .build());

            members.add(Member.builder()
                    .name("강유저")
                    .nickname("강유저")
                    .phone("01033334444")
                    .birth("999999")
                    .sex("1")
                    .email("user22@mail.com")
                    .pw(passwordEncoder.encode("1234"))
                    .role(MemberRole.USER)
                    .build());

            members.add(Member.builder()
                    .name("신유저")
                    .nickname("신유저")
                    .phone("01066665555")
                    .birth("999999")
                    .sex("1")
                    .email("user22@mail.com")
                    .pw(passwordEncoder.encode("1234"))
                    .role(MemberRole.USER)
                    .build());

            members.add(Member.builder()
                    .name("문유저")
                    .nickname("문유저")
                    .phone("01099998888")
                    .birth("999999")
                    .sex("1")
                    .email("user22@mail.com")
                    .pw(passwordEncoder.encode("1234"))
                    .role(MemberRole.USER)
                    .build());

            members.forEach(member -> em.persist(member));

            List<Member> admins = new ArrayList<>();

            admins.add(Member.builder()
                    .name("최원석")
                    .nickname("최원석")
                    .phone("01032100575")
                    .pw(passwordEncoder.encode("1234"))
                    .role(MemberRole.SUPER_ADMIN)
                    .build());

            admins.add(Member.builder()
                    .name("이현규")
                    .nickname("이현규")
                    .phone("01036198976")
                    .pw(passwordEncoder.encode("1234"))
                    .role(MemberRole.ADMIN)
                    .build());

            admins.add(Member.builder()
                    .name("문태웅")
                    .nickname("문태웅")
                    .phone("01033149467")
                    .pw(passwordEncoder.encode("1234"))
                    .role(MemberRole.ADMIN)
                    .build());

            admins.add(Member.builder()
                    .name("윤수호")
                    .nickname("윤수호")
                    .phone("01051025975")
                    .pw(passwordEncoder.encode("1234"))
                    .role(MemberRole.ADMIN)
                    .build());

            admins.forEach(admin -> em.persist(admin));

            List<ChatRoom> chatRooms = apartDataRepository.findAll().stream().map(apartData ->
                    ChatRoom.builder().apartData(apartData).build()).collect(Collectors.toList());

            chatRooms.get(10000).changeMemberCount(8);
            chatRooms.get(5000).changeMemberCount(6);
            chatRooms.get(1000).changeMemberCount(1);
            chatRooms.forEach(e -> em.persist(e));

            List<MemberChatRoom> memberChatRooms = new ArrayList<>();

            memberChatRooms.add(MemberChatRoom.builder().chatRoom(chatRooms.get(10000)).member(members.get(0)).build());
            memberChatRooms.add(MemberChatRoom.builder().chatRoom(chatRooms.get(10000)).member(members.get(1)).build());
            memberChatRooms.add(MemberChatRoom.builder().chatRoom(chatRooms.get(10000)).member(members.get(2)).build());
            memberChatRooms.add(MemberChatRoom.builder().chatRoom(chatRooms.get(10000)).member(members.get(3)).build());
            memberChatRooms.add(MemberChatRoom.builder().chatRoom(chatRooms.get(10000)).member(members.get(4)).build());
            memberChatRooms.add(MemberChatRoom.builder().chatRoom(chatRooms.get(10000)).member(members.get(5)).build());
            memberChatRooms.add(MemberChatRoom.builder().chatRoom(chatRooms.get(10000)).member(members.get(6)).build());
            memberChatRooms.add(MemberChatRoom.builder().chatRoom(chatRooms.get(10000)).member(members.get(7)).build());

            memberChatRooms.add(MemberChatRoom.builder().chatRoom(chatRooms.get(5000)).member(members.get(0)).build());
            memberChatRooms.add(MemberChatRoom.builder().chatRoom(chatRooms.get(5000)).member(members.get(1)).build());
            memberChatRooms.add(MemberChatRoom.builder().chatRoom(chatRooms.get(5000)).member(members.get(2)).build());
            memberChatRooms.add(MemberChatRoom.builder().chatRoom(chatRooms.get(5000)).member(members.get(3)).build());
            memberChatRooms.add(MemberChatRoom.builder().chatRoom(chatRooms.get(5000)).member(members.get(4)).build());
            memberChatRooms.add(MemberChatRoom.builder().chatRoom(chatRooms.get(5000)).member(members.get(5)).build());

            memberChatRooms.add(MemberChatRoom.builder().chatRoom(chatRooms.get(1000)).member(members.get(0)).build());

            memberChatRooms.forEach(memberChatRoom -> em.persist(memberChatRoom));
        }
    }
}