package com.project.team.plice.service.interfaces;

import com.project.team.plice.domain.data.ApartData;
import com.project.team.plice.dto.data.ApartDataDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface FavoriteService {
    public void favoriteSave(String apartName, Authentication authentication);
}
