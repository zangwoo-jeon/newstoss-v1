package com.newstoss.news.application.port.in.ml.v1;

import com.newstoss.news.adapter.in.web.dto.news.common.GetAllNewsDTO;
import com.newstoss.news.adapter.in.web.dto.news.v1.NewsDTOv1;
import java.util.List;

public interface GetAllNewsUseCase {
    List<NewsDTOv1> exec(GetAllNewsDTO getAllNewsDTO);
}
