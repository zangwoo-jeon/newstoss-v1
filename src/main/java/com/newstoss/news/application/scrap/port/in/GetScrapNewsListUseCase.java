package com.newstoss.news.application.scrap.port.in;


import com.newstoss.news.adapter.in.web.news.dto.v2.NewsDTOv2;

import java.util.List;
import java.util.UUID;

public interface GetScrapNewsListUseCase {
    List<NewsDTOv2> exec(UUID memberId);
}
