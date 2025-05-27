package com.newstoss.news.domain;

import com.newstoss.news.adapter.out.dto.MLNewsDTO;

import java.util.List;

public interface MLNewsPort {
    List<MLNewsDTO> getRealTimeNews();

    MLNewsDTO getDetailNews(String newsId);
}
