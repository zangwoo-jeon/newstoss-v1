package com.newstoss.news.application.port.in.ml.v2;



import com.newstoss.news.adapter.in.web.dto.news.v2.RelatedNewsDTOv2;

import java.util.List;

public interface GetRelatedNewsUseCaseV2 {
    List<RelatedNewsDTOv2> exec(String newsId);
}
