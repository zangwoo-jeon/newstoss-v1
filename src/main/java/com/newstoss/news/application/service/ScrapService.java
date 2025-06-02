package com.newstoss.news.application.service;

import com.newstoss.news.adapter.in.web.dto.news.v2.NewsDTOv2;
import com.newstoss.news.adapter.in.web.dto.scrap.ScrapDTO;
import com.newstoss.news.application.port.in.scrap.DeleteScrapNewsUseCase;
import com.newstoss.news.application.port.in.scrap.GetScrapNewsListUseCase;
import com.newstoss.news.application.port.in.scrap.SaveScrapNewsUseCase;
import com.newstoss.news.domain.NewsScrap;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ScrapService {
    private final SaveScrapNewsUseCase saveScrap;
    private final GetScrapNewsListUseCase getScrap;
    private final DeleteScrapNewsUseCase deleteScrap;

    public NewsScrap scrap(ScrapDTO scrapDTO){
        return saveScrap.exec(scrapDTO);
    }

    public List<NewsDTOv2> getAllScrap(UUID memberId){
        return getScrap.exec(memberId);
    }

    public boolean delete(ScrapDTO scrapDTO){
        return deleteScrap.exec(scrapDTO);
    }

}
