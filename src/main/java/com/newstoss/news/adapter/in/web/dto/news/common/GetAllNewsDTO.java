package com.newstoss.news.adapter.in.web.dto.news.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllNewsDTO {
    private Integer skip;
    private Integer limit;
}
