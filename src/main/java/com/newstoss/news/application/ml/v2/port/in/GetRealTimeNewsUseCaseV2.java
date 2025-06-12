package com.newstoss.news.application.ml.v2.port.in;


import com.newstoss.news.adapter.in.web.news.dto.v2.NewsDTOv2;

import java.util.List;

public interface GetRealTimeNewsUseCaseV2 {
    List<NewsDTOv2> exec();
}
