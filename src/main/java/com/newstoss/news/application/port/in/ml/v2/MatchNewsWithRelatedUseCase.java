package com.newstoss.news.application.port.in.ml.v2;


import com.newstoss.news.adapter.in.web.dto.news.v2.NewsDTOv2;
import com.newstoss.news.adapter.in.web.dto.news.v2.NewsMathRelatedDTO;

import java.util.List;

public interface MatchNewsWithRelatedUseCase {
    List<NewsMathRelatedDTO> exec(List<NewsDTOv2> newsList);
}
