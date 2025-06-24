package com.newstoss.news.application.news.v1.port.in;

import com.newstoss.news.adapter.in.web.news.dto.v2.GetAllNewsDTO;
import com.newstoss.news.adapter.in.web.news.dto.v1.NewsDTOv1;
import java.util.List;

public interface GetAllNewsUseCase {
    List<NewsDTOv1> exec(GetAllNewsDTO getAllNewsDTO);
}
