package com.project.team.plice.service.interfaces;

import com.project.team.plice.domain.inquire.Inquire;
import com.project.team.plice.dto.inquire.InquireDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface InquireService {
    public void savePost(Authentication authentication,InquireDto inquireDto);
    public List<InquireDto> getInquireList(Authentication authentication);
    public void delete(Long inquireId, Authentication authentication);
    public Inquire getInquireById(Long id);
}
