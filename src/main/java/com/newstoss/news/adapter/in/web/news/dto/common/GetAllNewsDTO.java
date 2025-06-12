package com.newstoss.news.adapter.in.web.news.dto.common;

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
