package com.newstoss.news.application.port.in.ml.v2;


import com.newstoss.news.adapter.in.web.dto.news.v2.NewsDTOv2;

import java.util.List;

public interface GetRealTimeNewsUseCaseV2 {
    List<NewsDTOv2> exec();
}
