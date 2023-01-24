package com.project.team.plice.service.classes;

import com.project.team.plice.dto.contents.ArticleDto;
import com.project.team.plice.dto.contents.NaverClient;
import com.project.team.plice.dto.contents.SearchNewsReq;
import com.project.team.plice.dto.contents.SearchNewsRes;
import com.project.team.plice.dto.utils.SearchUtils;
import com.project.team.plice.service.interfaces.ContentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentsServiceImpl implements ContentsService {

    private final NaverClient naverClient;

    public List<ArticleDto> search(SearchUtils searchParams) {
        SearchNewsReq req = SearchNewsReq.builder()
                .query(searchParams.getKeyword())
                .start(searchParams.getPage())
                .display(searchParams.getTotalPage())
                .sort(searchParams.getSort())
                .build();
        SearchNewsRes searchNewsRes = naverClient.searchLocal(req).getBody();
        List<ArticleDto> articleDtos = searchNewsRes.getItems().stream().map(
                o -> ArticleDto.builder()
                        .title(o.getTitle())
                        .description(o.getDescription())
                        .link(o.getLink())
                        .date(o.getPubDate())
                        .build()
        ).collect(Collectors.toList());
        articleDtos.forEach(articleDto -> {
            int totalPage = (int) Math.ceil(searchNewsRes.getTotal() / 4);
            articleDto.setPage(searchNewsRes.getStart());
            articleDto.setTotalPage(totalPage > 1000 ? 1000 : totalPage);
        });
        return articleDtos;

    }
}
