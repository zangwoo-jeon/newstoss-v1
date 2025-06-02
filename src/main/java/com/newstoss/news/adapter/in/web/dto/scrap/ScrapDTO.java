package com.newstoss.news.adapter.in.web.dto.scrap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScrapDTO {
    private UUID memberId;
    private String newsId;
}
