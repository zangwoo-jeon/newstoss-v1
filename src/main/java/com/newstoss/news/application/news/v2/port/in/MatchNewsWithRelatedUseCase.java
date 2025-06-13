package com.newstoss.news.application.news.v2.port.in;

import com.newstoss.news.adapter.in.web.news.dto.v2.NewsMathRelatedDTO;

import java.util.List;

public interface MatchNewsWithRelatedUseCase<T> {
    List<NewsMathRelatedDTO<T>> exec(List<T> newsList);
}