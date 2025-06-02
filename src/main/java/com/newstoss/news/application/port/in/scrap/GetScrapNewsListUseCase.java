package com.newstoss.news.application.port.in.scrap;


import com.newstoss.news.adapter.in.web.dto.news.v2.NewsDTOv2;

import java.util.List;
import java.util.UUID;

public interface GetScrapNewsListUseCase {
    List<NewsDTOv2> exec(UUID memberId);
}
