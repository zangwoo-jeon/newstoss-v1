package com.newstoss.news.application.news.v2.impl.ml;

import com.newstoss.news.adapter.in.web.news.dto.v2.CountDTO;
import com.newstoss.news.adapter.out.news.dto.v2.MLNewsCountDTO;
import com.newstoss.news.application.news.v2.port.in.GetNewsCountUseCaseV2;
import com.newstoss.news.application.news.v2.port.out.MLNewsPortV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetNewsCount implements GetNewsCountUseCaseV2 {
    private final MLNewsPortV2 mlNewsPortV2;


    @Override
    public CountDTO exec() {
        MLNewsCountDTO rawCount = mlNewsPortV2.count();
        return new CountDTO(rawCount.getNewsCountTotal(), rawCount.getNewsCountToday());
    }
}
