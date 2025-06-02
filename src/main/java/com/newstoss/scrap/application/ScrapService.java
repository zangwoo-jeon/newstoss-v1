package com.newstoss.scrap.application;

import com.newstoss.news.adapter.in.web.dto.v2.NewsDTOv2;
import com.newstoss.scrap.adapter.in.web.dto.ScrapDTO;
import com.newstoss.scrap.application.port.in.DeleteScrapNewsUseCase;
import com.newstoss.scrap.application.port.in.GetScrapNewsListUseCase;
import com.newstoss.scrap.application.port.in.SaveScrapNewsUseCase;
import com.newstoss.scrap.domain.NewsScrap;
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
