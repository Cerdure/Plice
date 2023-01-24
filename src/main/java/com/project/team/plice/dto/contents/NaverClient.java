package com.project.team.plice.dto.contents;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@Component
public class NaverClient {

    public ResponseEntity<SearchNewsRes> searchLocal(SearchNewsReq searchNLocalReq) {
        ByteBuffer buffer = StandardCharsets.UTF_8.encode(searchNLocalReq.getQuery());
        String encode = StandardCharsets.UTF_8.decode(buffer).toString();

        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("v1/search/news.json")
                .queryParams(searchNLocalReq.toMultiValueMap())
                .encode()
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();

        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", "AhreRSxGNAyxiHnEnP2Y")
                .header("X-Naver-Client-Secret", "0TIh8TFQ8E")
                .build();

        ResponseEntity<SearchNewsRes> responseEntity = new RestTemplate().exchange(req, SearchNewsRes.class);
        return responseEntity;
    }

}
