package com.newstoss.news.application.news.v2.port.in;

import com.newstoss.news.adapter.in.web.news.dto.v2.RecommendNewsDTO;

import java.util.List;
import java.util.UUID;

public interface GetRecommendNewsUseCase {
    List<RecommendNewsDTO> exec(UUID memberId);
}
