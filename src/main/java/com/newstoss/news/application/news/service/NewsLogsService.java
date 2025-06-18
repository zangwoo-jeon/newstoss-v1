package com.newstoss.news.application.news.service;

import com.newstoss.news.adapter.in.web.news.dto.v2.LogEntryDTO;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class NewsLogsService {
    private static final String NEWS_LOG_DIR = "/newsLogs"; // Dockerfile 및 logback 설정과 일치
    private static final String NEWS_LOG_BASE_NAME = "news";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // 현재 로그 라인 패턴에 맞춘 정규 표현식 (LogEntryDTO에서 사용)
    // 그룹 1: 날짜 시간, 그룹 2: 스레드, 그룹 3: 레벨, 그룹 4: 로거 이름, 그룹 5: memberId, 그룹 6: news_id
    private static final Pattern LOG_PATTERN = Pattern.compile(
            "^(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}) \\[(.*?)\\] \\[(.*?)\\] (.*?) - \\[memberId : (.*?)\\]\\s*\\[news_id : (.*?)\\]$"
    );

    // API 엔드포인트에서 호출될 메인 메서드
    public List<LogEntryDTO> getNewsLogs(String startDateStr, String endDateStr, String memberId) {

        LocalDate startDate = null;
        LocalDate endDate = null;

        // 날짜 파라미터 파싱 및 유효성 검사
        try {
            if (startDateStr != null && !startDateStr.isEmpty()) {
                startDate = LocalDate.parse(startDateStr, DATE_FORMATTER);
            }
            if (endDateStr != null && !endDateStr.isEmpty()) {
                endDate = LocalDate.parse(endDateStr, DATE_FORMATTER);
            }
        } catch (DateTimeParseException e) {
            // 날짜 형식 오류 발생 시 빈 리스트 반환 또는 예외 처리
            // TODO: 로깅 - 경고 메시지 남기기
            return new ArrayList<>();
        }

        List<File> logFilesToRead;

        if (startDate != null && endDate != null && !startDate.isAfter(endDate)) {
            // --- 1. 날짜 범위가 유효하게 지정된 경우: 해당 범위의 파일 목록 가져오기 ---
            logFilesToRead = getLogFilesByDateRange(startDate, endDate);

        } else {
            // --- 2. 날짜 범위가 지정되지 않았거나 잘못된 경우: 모든 로그 파일 조회 ---
            logFilesToRead = getAllLogFiles();
        }

        List<LogEntryDTO> allLogs = new ArrayList<>();

        // --- 모든 파일에서 로그 라인 읽고 파싱 ---
        for (File file : logFilesToRead) {
            List<String> rawLines;
            try {
                // TODO: 매우 큰 파일인 경우 Files.lines() 스트림 처리 또는 RandomAccessFile 등 고려
                rawLines = Files.readAllLines(file.toPath());
            } catch (IOException e) {
                // 파일 읽기 오류 로깅
                // TODO: 로거 사용
                e.printStackTrace();
                continue; // 다음 파일로 이동
            }

            for (String line : rawLines) {
                Matcher matcher = LOG_PATTERN.matcher(line);
                if (matcher.find()) {
                    LogEntryDTO entry = new LogEntryDTO();
                    // setTimestamp()에서 LocalDateTime으로 파싱
                    entry.setTimestamp(matcher.group(1));
                    entry.setThread(matcher.group(2));
                    entry.setLevel(matcher.group(3).trim());
                    entry.setLogger(matcher.group(4));
                    entry.setMessage(line); // 전체 메시지 저장
                    entry.setMemberId(matcher.group(5)); // 추출된 memberId
                    entry.setNewsId(matcher.group(6)); // 추출된 news_id

                    allLogs.add(entry);
                } else {
                    // 패턴에 맞지 않는 로그 라인도 처리 (예: 전체 메시지 저장)
                    LogEntryDTO entry = new LogEntryDTO();
                    entry.setMessage(line);
                    // TODO: 패턴에 맞지 않는 라인은 timestamp가 null이 될 수 있음. 처리 방식 결정.
                    // entry.setTimestamp(...) 등 가능한 정보 설정
                    allLogs.add(entry);
                }
            }
        }

        // --- 로그 발생 시간 기준으로 정렬 ---
        // LogEntryDTO의 timestamp 필드가 LocalDateTime이므로 정확히 정렬됩니다.
        // null 값 처리를 위해 nullsLast 등을 사용할 수 있습니다.
        allLogs.sort(Comparator.comparing(LogEntryDTO::getTimestamp, Comparator.nullsLast(Comparator.naturalOrder())));


        // --- memberId 필터링 적용 ---
        List<LogEntryDTO> filteredLogs = allLogs.stream()
                .filter(entry -> {
                    // memberId 파라미터가 없거나, 로그 엔트리의 memberId가 파라미터와 일치하는 경우만 남김
                    // 패턴에 맞지 않는 로그(memberId 추출 실패)는 entry.getMemberId()가 null일 수 있으므로 고려
                    boolean matchesMemberId = memberId == null || (entry.getMemberId() != null && entry.getMemberId().equals(memberId));
                    // TODO: 다른 필터 조건 추가 (레벨 등)
                    return matchesMemberId;
                })
                .collect(Collectors.toList());

        // TODO: 페이징 처리를 위한 limit, offset 등 추가

        return filteredLogs;
    }

    // --- 날짜 범위에 해당하는 로그 파일 목록 가져오기 메서드 (기존 로직 유지) ---
    private List<File> getLogFilesByDateRange(LocalDate startDate, LocalDate endDate) {
        List<File> files = new ArrayList<>();
        LocalDate currentDate = startDate;

        // 시작 날짜부터 종료 날짜까지 순회하며 롤링된 파일 이름 생성
        while (!currentDate.isAfter(endDate)) {
            // TODO: Logback fileNamePattern에 %i 인덱스가 있다면 해당 부분 고려 필요
            String fileName = NEWS_LOG_BASE_NAME + "." + currentDate.format(DATE_FORMATTER) + ".log";
            File logFile = new File(NEWS_LOG_DIR, fileName);
            if (logFile.exists() && logFile.canRead()) {
                files.add(logFile);
            }
            currentDate = currentDate.plusDays(1); // 다음 날짜로 이동
        }

        // 현재 활성 로그 파일 (news.log)도 조회 범위에 포함될 수 있는지 확인
        // (endDate가 오늘 날짜 이후이거나, endDate가 오늘이고 현재 파일이 있다면)
        File currentLogFile = new File(NEWS_LOG_DIR, NEWS_LOG_BASE_NAME + ".log");
        if (currentLogFile.exists() && currentLogFile.canRead()) {
            // 현재 파일의 마지막 수정 날짜 등을 확인하여 endDate 범위에 포함되는지 더 정확히 판단하는 로직 추가 필요
            // 간단하게는 endDate가 오늘 이후 날짜인 경우 포함
            if (endDate != null && (endDate.isAfter(LocalDate.now()) || endDate.isEqual(LocalDate.now()))) {
                files.add(currentLogFile);
            } else if (endDate == null && startDate != null) {
                // startDate만 있고 endDate가 없는 경우 - 현재 파일 포함 여부 결정 필요
                // (예: startDate가 오늘 이전이면 포함, 오늘 이후면 포함)
                // 여기서는 간단히 포함하지 않음.
            } else if (startDate == null && endDate == null) {
                // 전체 조회 모드는 getAllLogFiles()에서 처리되므로 여기서는 포함 안 함
            }
        }

        // 파일 이름 기준으로 정렬 (뉴스 로그는 보통 시간 순서대로 파일 이름이 정해지므로)
        // news.log가 가장 최근 내용 포함 -> 이름 기준 정렬 시 가장 마지막에 와야 함 (alphanumeric)
        // 예: news.2025-06-17.log, news.2025-06-18.log, news.log
        files.sort(Comparator.comparing(File::getName)); // 이름으로 정렬하여 시간 순 파일 목록 확보

        return files;
    }

    // --- 모든 로그 파일 목록 가져오기 메서드 (새로 추가) ---
    private List<File> getAllLogFiles() {
        File logDir = new File(NEWS_LOG_DIR);
        if (!logDir.exists() || !logDir.isDirectory() || !logDir.canRead()) {
            // TODO: 로깅 - 디렉토리 접근 불가
            return new ArrayList<>(); // 디렉토리가 없거나 읽을 수 없으면 빈 리스트 반환
        }

        File[] files = logDir.listFiles((dir, name) ->
                        // "news.log" 또는 "news.YYYY-MM-DD.log" 패턴을 따르는 파일만 필터링
                        name.equals(NEWS_LOG_BASE_NAME + ".log") || name.matches(NEWS_LOG_BASE_NAME + "\\.\\d{4}-\\d{2}-\\d{2}\\.log")
                // TODO: Logback fileNamePattern에 %i 인덱스가 있다면 해당 패턴도 추가
        );

        if (files == null) {
            // TODO: 로깅 - 파일 목록 가져오기 오류
            return new ArrayList<>();
        }

        // 파일 이름 기준으로 정렬 (시간 순서대로 읽고 최종 정렬)
        // news.log가 가장 마지막에 오도록 정렬
        List<File> fileList = new ArrayList<>(List.of(files));
        fileList.sort(Comparator.comparing(File::getName)); // news.log가 뒤로 오도록 정렬됨

        return fileList;
    }

    // TODO: LogEntryDTO 클래스 정의 (앞서 정의한 내용 참고)
    // public class LogEntryDTO { ... }
}
