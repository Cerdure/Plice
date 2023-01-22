package com.project.team.plice.service.classes;

import com.project.team.plice.domain.enums.MemberRole;
import com.project.team.plice.domain.member.Member;
import com.project.team.plice.dto.member.MemberDto;
import com.project.team.plice.repository.member.MemberRepository;
import com.project.team.plice.service.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public boolean join(MemberDto memberDto) {
        boolean result = validateDuplicateMember(memberDto);
        Member member = memberDto.createMember(passwordEncoder);
        memberRepository.save(member);
        return result;
    }

    public boolean validateDuplicateMember(MemberDto memberDto) {
        boolean isDuplicated = false;
        Optional<Member> findMembers = memberRepository.findByPhone(memberDto.getPhone());
        if (!findMembers.isEmpty()) {
            isDuplicated = true;
        }
        return isDuplicated;
    }

    @Override
    public Member findMember(Authentication authentication) {
        return findByPhone(authentication.getName());
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    @Override
    public List<Member> findByRoles(List<MemberRole> roles) {
        return memberRepository.findByRoleIn(roles);
    }

    @Override
    public Page<Member> findByRoles(List<MemberRole> roles, Pageable pageable) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 12, Sort.by("regDate").descending());
        return memberRepository.findByRoleIn(roles, pageable);
    }

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).get();
    }

    @Override
    public Member findByPhone(String phone) {
        return memberRepository.findByPhone(phone).get();
    }

    @Override
    @Transactional
    public void update(Authentication authentication, MemberDto memberDto) {
        String phone = authentication.getName();
        Member member = memberRepository.findByPhone(phone).get();
        member.update(memberDto.getName(),
                memberDto.getNickname());
        if (memberDto.getPw() != null && memberDto.getPw() != "") {
            member.updatePw(passwordEncoder.encode(memberDto.getPw()));
        }
        memberRepository.save(member);
    }

    @Override
    @Transactional
    public void update(MemberDto memberDto) {
        Member member = memberRepository.findById(memberDto.getId()).get();
        member.updatePhone(memberDto.getPhone());
        member.updateName(memberDto.getName());
        member.updateNickname(memberDto.getNickname());
        member.updateBirth(memberDto.getBirth());
        memberRepository.save(member);
    }

    @Override
    public void update(String phone, String pw) {
        Member member = memberRepository.findByPhone(phone).get();
        member.updatePw(passwordEncoder.encode(pw));
        memberRepository.save(member);
    }

    @Transactional
    public void delete(Authentication authentication) {
        String phone = authentication.getName();
        Member member = memberRepository.findByPhone(phone).get();
        memberRepository.delete(member);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        memberRepository.delete(memberRepository.findById(id).get());
    }

    @Override
    public String checkPhone(String idInput) {
        Optional<Member> findMembers = memberRepository.findByPhone(idInput);
        if (!findMembers.isEmpty()) { // 등록된 회원이 있다.
            return "ok";
        } else {
            return "no";
        }
    }

    @Override
    public String checkNick(String nickInput) {
        List<Member> byNickname = memberRepository.findByNickname(nickInput);
        if (!byNickname.isEmpty()) {
            return "ok";    // 회원이 있다.
        } else {
            return "no";
        }
    }

    @Override
    public String certifiedPhoneNumber(String phoneNumber) {
        Random rand = new Random();
        String numStr = "";
        for (int i = 0; i < 6; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr += ran;
        }

        System.out.println("수신자 번호 : " + phoneNumber);
        System.out.println("인증번호 : " + numStr);

        String api_key = "NCS8ZGHMQ5ENZB49";
        String api_secret = "GOQEJ1SWZDGSCYUWATYVYC0VWLCCMFRS";
        Message coolsms = new Message(api_key, api_secret);

        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", phoneNumber);    // 수신전화번호
        params.put("from", "01033149467");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "SMS");
        params.put("text", "Plice 사이트 휴대폰인증 테스트 메시지 : 인증번호는" + "[" + numStr + "]" + "입니다.");
        params.put("app_version", "test app 1.2"); // application name and version

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }
        return numStr;
    }
}