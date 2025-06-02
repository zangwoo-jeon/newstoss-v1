package com.newstoss.news.application.impl.ml.v2;


import com.newstoss.news.adapter.in.web.dto.v2.NewsDTOv2;
import com.newstoss.news.adapter.out.dto.v2.MLNewsDTOv2;

import java.time.LocalDateTime;

public class NewsDTOv2Mapper {
    public static NewsDTOv2 from(MLNewsDTOv2 ml) {
        return new NewsDTOv2(ml.getNewsId(),  LocalDateTime.parse(ml.getWdate()), ml.getTitle(), ml.getArticle(), ml.getUrl(), ml.getPress(), ml.getImage());
    }
}
