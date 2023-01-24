package com.project.team.plice.service.interfaces;

import com.project.team.plice.domain.inquire.Inquire;
import com.project.team.plice.dto.inquire.InquireDto;
import com.project.team.plice.dto.utils.SearchUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface InquireService {
    public Page<Inquire> getInquireList(Authentication authentication, Pageable pageable);
    public Inquire getInquireById(Long id);
    public Page<Inquire> findAllInquire(Pageable pageable);
    public Inquire findInquire(Long id);
    public void savePost(Authentication authentication,InquireDto inquireDto);
    public void delete(Long inquireId, Authentication authentication);
    public Page<Inquire> searchInquire(SearchUtils searchUtils, Pageable pageable);
}
