package com.newstoss.scrap.application.port.in;


import com.newstoss.news.adapter.in.web.dto.v2.NewsDTOv2;

import java.util.List;
import java.util.UUID;

public interface GetScrapNewsListUseCase {
    List<NewsDTOv2> exec(UUID memberId);
}
