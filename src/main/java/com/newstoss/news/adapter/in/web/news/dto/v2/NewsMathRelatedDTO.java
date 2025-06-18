package com.newstoss.news.adapter.in.web.news.dto.v2;

import com.newstoss.news.adapter.in.web.news.dto.v2.Meta.RelatedNewsDTOv2;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsMathRelatedDTO<T> {
    private T news;
    private List<RelatedNewsDTOv2> related;
}