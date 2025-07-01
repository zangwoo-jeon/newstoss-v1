package com.newstoss.news.adapter.out.news.dto.v2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MLRecommendNewsDTO {
    @JsonProperty("user_click_count")
    private Integer userClickCount;
    @JsonProperty("use_other_user")
    private boolean useOtherUser;
    @JsonProperty("other_user_data")
    private MLOtherUserDataDTO otherUserData;
    @JsonProperty("news_data")
    private List<MLRecommendNewsDataDTO> newsData;
}
