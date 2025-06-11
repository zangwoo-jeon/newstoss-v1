package com.newstoss.news.adapter.in.web.dto.news.v2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsMathRelatedDTO {
    private NewsDTOv2 news;
    private List<RelatedNewsDTOv2> related;
}
