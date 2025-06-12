package com.newstoss.news.application.ml.v1.impl;

import com.newstoss.news.adapter.in.web.news.dto.v1.NewsDTOv1;
import com.newstoss.news.adapter.in.web.news.dto.v1.NewsMathRelatedDTOTest;
import com.newstoss.news.adapter.in.web.news.dto.v1.RelatedNewsDTO;
import com.newstoss.news.adapter.out.news.dto.v1.MLNewsDTOv1;
import com.newstoss.news.adapter.out.news.dto.v1.MLRelatedNewsDTOv1;

import java.util.List;

public class NewsDTOv1Mapper {
    public static NewsDTOv1 from(MLNewsDTOv1 ml) {
        return new NewsDTOv1(ml.getNewsId(), ml.getTitle(), ml.getUrl(), ml.getContent());
    }

    public static RelatedNewsDTO from(MLRelatedNewsDTOv1 ml) {
        return new RelatedNewsDTO(ml.getNewsId(), ml.getDate(),ml.getTitle(), ml.getUrl(), ml.getContent(), ml.getSimilarity());
    }

    public static List<RelatedNewsDTO> fromRelated(List<MLRelatedNewsDTOv1> list) {
        return list.stream()
                .map(NewsDTOv1Mapper::from)
                .toList();
    }

}