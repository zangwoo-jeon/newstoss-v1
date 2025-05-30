package com.newstoss.news.application.service.v1;

import com.newstoss.news.adapter.in.web.dto.v1.NewsDTOv1;
import com.newstoss.news.adapter.out.dto.v1.MLNewsDTOv1;

public class NewsDTOv1Mapper {
    public static NewsDTOv1 from(MLNewsDTOv1 ml) {
        return new NewsDTOv1(ml.getNewsId(), ml.getTitle(), ml.getUrl(), ml.getContent());
    }
}