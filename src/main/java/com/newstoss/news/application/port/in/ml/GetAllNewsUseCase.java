package com.newstoss.news.application.port.in.ml;

import com.newstoss.news.adapter.in.web.dto.news.common.GetAllNewsDTO;
import com.newstoss.news.adapter.in.web.dto.news.v2.NewsDTOv2;

import java.util.List;

public interface GetAllNewsUseCase {
    List<NewsDTOv2> exec(GetAllNewsDTO getAllNewsDTO);
}
