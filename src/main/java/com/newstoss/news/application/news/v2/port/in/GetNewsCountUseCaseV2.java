package com.newstoss.news.application.news.v2.port.in;

import com.newstoss.news.adapter.in.web.news.dto.v2.CountDTO;

public interface GetNewsCountUseCaseV2 {
    CountDTO exec();
}
