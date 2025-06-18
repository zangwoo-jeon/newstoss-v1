package com.newstoss.news.application.news.v2.port.in;



import com.newstoss.news.adapter.in.web.news.dto.v2.Meta.RelatedNewsDTOv2;

import java.util.List;

public interface GetRelatedNewsUseCaseV2 {
    List<RelatedNewsDTOv2> exec(String newsId);
}
