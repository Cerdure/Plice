package com.project.team.plice.repository.inquire;

import com.project.team.plice.domain.inquire.Inquire;
import com.project.team.plice.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InquireRepository extends JpaRepository<Inquire, Long> {
            public List<Inquire> findByMember(Member member);

}
