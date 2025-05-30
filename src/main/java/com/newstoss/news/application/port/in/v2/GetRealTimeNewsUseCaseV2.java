package com.newstoss.news.application.port.in.v2;


import com.newstoss.news.adapter.in.web.dto.v2.NewsDTOv2;

import java.util.List;

public interface GetRealTimeNewsUseCaseV2 {
    List<NewsDTOv2> exec();
}
