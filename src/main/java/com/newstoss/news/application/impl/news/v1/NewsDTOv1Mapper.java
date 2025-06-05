package com.newstoss.news.application.impl.news.v1;

import com.newstoss.news.adapter.in.web.dto.news.v1.NewsDTOv1;
import com.newstoss.news.adapter.out.dto.v1.MLNewsDTOv1;

public class NewsDTOv1Mapper {
    public static NewsDTOv1 from(MLNewsDTOv1 ml) {
        return new NewsDTOv1(ml.getNewsId(), ml.getTitle(), ml.getUrl(), ml.getContent());
    }
}