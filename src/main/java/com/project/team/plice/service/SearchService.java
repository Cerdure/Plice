package com.project.team.plice.service;

import com.project.team.plice.domain.news.NewsEntity;
import com.project.team.plice.dto.contents.NaverClient;
import com.project.team.plice.dto.contents.SearchNewsReq;
import com.project.team.plice.dto.contents.SearchNewsRes;
import com.project.team.plice.repository.search.SearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final NaverClient naverClient;
    private final SearchRepository searchRepository;

    public List<NewsEntity> search(String query){
        SearchNewsReq req = new SearchNewsReq();
        req.setQuery(query);
        ResponseEntity<SearchNewsRes> res = naverClient.searchLocal(req);
        List<NewsEntity> newsEntities = res.getBody().getItems().stream().map(o -> new NewsEntity(o.getTitle(),
                                                                                                  o.getLink(),
                                                                                                  o.getDescription())).collect(Collectors.toList());
        return newsEntities;

    }
}
