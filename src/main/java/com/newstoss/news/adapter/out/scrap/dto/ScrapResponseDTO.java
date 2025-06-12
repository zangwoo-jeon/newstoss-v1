package com.newstoss.news.adapter.out.scrap.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScrapResponseDTO {
    private UUID scrapNewsId;
    private UUID memberId;
    private String memberName;
    private String newsId;
    private String newsTitle;
}
