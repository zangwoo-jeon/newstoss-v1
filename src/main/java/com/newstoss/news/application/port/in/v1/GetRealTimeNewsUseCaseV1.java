package com.newstoss.news.application.port.in.v1;


import com.newstoss.news.adapter.in.web.dto.v1.NewsDTOv1;

import java.util.List;

public interface GetRealTimeNewsUseCaseV1 {
    List<NewsDTOv1> exec();
}
