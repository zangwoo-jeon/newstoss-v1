package com.newstoss.news.adapter.in.web.news.dto.v2;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@Getter
@Setter
public class LogEntryDTO {
    private LocalDateTime timestamp; // LocalDateTime으로 변경하여 정확한 정렬
    private String timestampRaw;   // 원본 시간 문자열 (필요시)
    private String thread;
    private String level;
    private String logger;
    private String message;     // 원본 메시지 라인 전체
    private String memberId;
    private String newsId;

    public void setTimestamp(String timestampStr) {
        this.timestampRaw = timestampStr;
        try {
            java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            this.timestamp = LocalDateTime.parse(timestampStr, formatter);
        } catch (DateTimeParseException e) {
            this.timestamp = null;
        }
    }
}
