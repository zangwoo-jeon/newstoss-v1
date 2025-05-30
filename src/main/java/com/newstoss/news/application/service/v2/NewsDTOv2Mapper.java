package com.newstoss.news.application.service.v2;


import com.newstoss.news.adapter.in.web.dto.v2.NewsDTOv2;
import com.newstoss.news.adapter.out.dto.v2.MLNewsDTOv2;

public class NewsDTOv2Mapper {
    public static NewsDTOv2 from(MLNewsDTOv2 ml) {
        return new NewsDTOv2(ml.getNewsId(), ml.getWdate(), ml.getTitle(), ml.getArticle(), ml.getUrl(), ml.getPress(), ml.getImage());
    }
}
