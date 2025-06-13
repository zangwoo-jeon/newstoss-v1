package com.newstoss.news.application.news.v1.port.in;


import com.newstoss.news.adapter.in.web.news.dto.v1.NewsDTOv1;

import java.util.List;

public interface GetRealTimeNewsUseCaseV1 {
    List<NewsDTOv1> exec();
}
