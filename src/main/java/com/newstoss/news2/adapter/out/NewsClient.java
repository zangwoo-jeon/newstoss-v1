package com.newstoss.news2.adapter.out;

import com.newstoss.news2.adapter.in.NewsDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class NewsClient {

    private final RestTemplate restTemplate;

    public List<NewsDTO> getNews(int skip, int limit) {
        String url = "http://3.37.207.16:8000/news/v2?skip=" + skip + "&limit=" + limit;
        ResponseEntity<NewsDTO[]> response = restTemplate.getForEntity(url, NewsDTO[].class);
        return Arrays.asList(response.getBody());
    }
}