package com.project.team.plice;

import com.project.team.plice.domain.chat.ChatRoom;
import com.project.team.plice.domain.chat.MemberChatRoom;
import com.project.team.plice.domain.enums.MemberRole;
import com.project.team.plice.domain.member.Member;
import com.project.team.plice.domain.post.Notice;
import com.project.team.plice.domain.post.Post;
import com.project.team.plice.repository.chat.ChatRoomRepository;
import com.project.team.plice.repository.data.ApartDataRepository;
import com.project.team.plice.repository.member.MemberRepository;
import com.project.team.plice.repository.post.NoticeRepository;
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
        private final NoticeRepository noticeRepository;

        public void dbInit() {

            List<Post> posts = new ArrayList<>();

            posts.add(Post.builder()
                    .memberNickname("관리자1")
                    .title("공지사항1")
                    .content("플라이스 파이널 프로젝트 화이팅")
                    .build());

            posts.add(Post.builder()
                    .memberNickname("관리자2")
                    .title("공지사항2")
                    .content("플라이스 파이널 프로젝트 화이팅")
                    .build());

            posts.add(Post.builder()
                    .memberNickname("관리자3")
                    .title("공지사항3")
                    .content("플라이스 파이널 프로젝트 화이팅")
                    .build());

            posts.add(Post.builder()
                    .memberNickname("관리자4")
                    .title("공지사항4")
                    .content("플라이스 파이널 프로젝트 화이팅")
                    .build());

            posts.add(Post.builder()
                    .memberNickname("관리자5")
                    .title("공지사항5")
                    .content("플라이스 파이널 프로젝트 화이팅")
                    .build());

            posts.add(Post.builder()
                    .memberNickname("관리자6")
                    .title("공지사항6")
                    .content("플라이스 파이널 프로젝트 화이팅")
                    .build());

            posts.add(Post.builder()
                    .memberNickname("관리자7")
                    .title("공지사항7")
                    .content("플라이스 파이널 프로젝트 화이팅")
                    .build());

            posts.add(Post.builder()
                    .memberNickname("관리자8")
                    .title("공지사항8")
                    .content("플라이스 파이널 프로젝트 화이팅")
                    .build());

            posts.add(Post.builder()
                    .memberNickname("관리자9")
                    .title("공지사항9")
                    .content("플라이스 파이널 프로젝트 화이팅")
                    .build());

            posts.add(Post.builder()
                    .memberNickname("관리자10")
                    .title("공지사항10")
                    .content("플라이스 파이널 프로젝트 화이팅")
                    .build());

            posts.add(Post.builder()
                    .memberNickname("관리자11")
                    .title("공지사항11")
                    .content("플라이스 파이널 프로젝트 화이팅")
                    .build());

            posts.add(Post.builder()
                    .memberNickname("관리자12")
                    .title("공지사항12")
                    .content("플라이스 파이널 프로젝트 화이팅")
                    .build());

            posts.add(Post.builder()
                    .memberNickname("관리자13")
                    .title("공지사항13")
                    .content("플라이스 파이널 프로젝트 화이팅")
                    .build());

            posts.add(Post.builder()
                    .memberNickname("관리자14")
                    .title("공지사항14")
                    .content("플라이스 파이널 프로젝트 화이팅")
                    .build());

            posts.add(Post.builder()
                    .memberNickname("관리자15")
                    .title("공지사항15")
                    .content("플라이스 파이널 프로젝트 화이팅")
                    .build());

            posts.add(Post.builder()
                    .memberNickname("관리자16")
                    .title("공지사항16")
                    .content("플라이스 파이널 프로젝트 화이팅")
                    .build());

            posts.add(Post.builder()
                    .memberNickname("관리자17")
                    .title("공지사항17")
                    .content("플라이스 파이널 프로젝트 화이팅")
                    .build());

            posts.add(Post.builder()
                    .memberNickname("관리자18")
                    .title("공지사항18")
                    .content("플라이스 파이널 프로젝트 화이팅")
                    .build());

            posts.add(Post.builder()
                    .memberNickname("관리자19")
                    .title("공지사항19")
                    .content("플라이스 파이널 프로젝트 화이팅")
                    .build());

            posts.add(Post.builder()
                    .memberNickname("관리자20")
                    .title("공지사항20")
                    .content("플라이스 파이널 프로젝트 화이팅")
                    .build());

            posts.forEach(notice -> em.persist(notice));


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