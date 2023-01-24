package com.project.team.plice.repository.inquire;

import com.project.team.plice.domain.inquire.Inquire;
import com.project.team.plice.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquireRepository extends JpaRepository<Inquire, Long> {
    public Page<Inquire> findById(Long id, Pageable pageable);
    public Page<Inquire> findByMemberId(Long memberId, Pageable pageable);
    public Page<Inquire> findByMember(Member member, Pageable pageable);
    public Page<Inquire> findByTypeContainsIgnoreCase(String type, Pageable pageable);
    public Page<Inquire> findByTitleContainsIgnoreCase(String title, Pageable pageable);
}
