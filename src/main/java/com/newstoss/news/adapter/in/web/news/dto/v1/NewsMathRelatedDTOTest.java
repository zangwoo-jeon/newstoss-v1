package com.newstoss.news.adapter.in.web.news.dto.v1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsMathRelatedDTOTest {
    private NewsDTOv1 news;
    private List<RelatedNewsDTO> related;
}
