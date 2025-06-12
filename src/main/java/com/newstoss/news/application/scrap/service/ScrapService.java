package com.newstoss.news.application.scrap.service;

import com.newstoss.news.adapter.in.web.news.dto.v2.NewsDTOv2;
import com.newstoss.news.adapter.in.web.scrap.dto.ScrapDTO;
import com.newstoss.news.application.scrap.port.in.DeleteScrapNewsUseCase;
import com.newstoss.news.application.scrap.port.in.GetScrapNewsListUseCase;
import com.newstoss.news.application.scrap.port.in.SaveScrapNewsUseCase;
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
