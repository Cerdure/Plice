package com.project.team.plice.service.interfaces;

import com.project.team.plice.dto.contents.ArticleDto;
import com.project.team.plice.dto.utils.SearchParamUtil;

import java.util.List;

public interface ContentsService {
    public List<ArticleDto> search(SearchParamUtil searchParams);
}
