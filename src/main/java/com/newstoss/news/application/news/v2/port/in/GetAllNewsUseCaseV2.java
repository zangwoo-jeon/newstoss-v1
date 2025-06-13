package com.newstoss.news.application.news.v2.port.in;

import com.newstoss.news.adapter.in.web.news.dto.common.GetAllNewsDTO;
import com.newstoss.news.adapter.in.web.news.dto.v2.NewsDTOv2;

import java.util.List;

public interface GetAllNewsUseCaseV2 {
    List<NewsDTOv2> exec(GetAllNewsDTO getAllNewsDTO);
}
