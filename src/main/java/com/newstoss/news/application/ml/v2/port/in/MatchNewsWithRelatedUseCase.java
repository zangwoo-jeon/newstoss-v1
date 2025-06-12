package com.newstoss.news.application.ml.v2.port.in;


import com.newstoss.news.adapter.in.web.news.dto.v1.NewsDTOv1;
import com.newstoss.news.adapter.in.web.news.dto.v1.NewsMathRelatedDTOTest;
import com.newstoss.news.adapter.in.web.news.dto.v2.NewsDTOv2;
import com.newstoss.news.adapter.in.web.news.dto.v2.NewsMathRelatedDTO;

import java.util.List;

public interface MatchNewsWithRelatedUseCase {
    List<NewsMathRelatedDTO> exec(List<NewsDTOv2> newsList);
    List<NewsMathRelatedDTOTest> test(List<NewsDTOv1> newsList);
}
