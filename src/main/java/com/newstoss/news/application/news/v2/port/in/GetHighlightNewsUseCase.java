package com.newstoss.news.application.news.v2.port.in;

import com.newstoss.news.adapter.in.web.news.dto.v2.HighlightNewsDTO;
import com.newstoss.news.adapter.in.web.news.dto.v2.NewsDTOv2;

import java.util.List;

public interface GetHighlightNewsUseCase {
    List<HighlightNewsDTO> exec();
}
