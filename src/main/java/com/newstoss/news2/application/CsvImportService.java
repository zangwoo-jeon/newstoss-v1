//package com.newstoss.news2.application;
//
//import com.newstoss.news.domain.NewsEntity;
//import com.newstoss.news2.adapter.out.NewsRepository;
//import com.opencsv.CSVReader;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.io.*;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//
//@Service
//@RequiredArgsConstructor
//public class CsvImportService {
//
//    private final NewsRepository newsRepository;
//    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//
//    public void importCsv(File file) throws Exception {
//        int success = 0;
//        int fail = 0;
//        int lineNo = 0;
//        boolean startSaving = false;
//        List<NewsEntity> batch = new ArrayList<>();
//
//        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
//            String line;
//            StringBuilder sb = new StringBuilder();
//
//            // skip header
//            br.readLine();
//            lineNo++;
//
//            while ((line = br.readLine()) != null) {
//                lineNo++;
//                sb.append(line).append("\n");
//
//                // 따옴표 짝수 확인
//                if (countQuotes(sb.toString()) % 2 != 0) {
//                    continue; // 아직 안 닫힘
//                }
//
//                String[] tokens = parseCsvLine(sb.toString());
//                sb.setLength(0); // clear buffer
//
//                if (tokens == null || tokens.length < 7) {
//                    System.err.println("⚠️ [줄 " + lineNo + "] 컬럼 부족 또는 파싱 실패: " + Arrays.toString(tokens));
//                    fail++;
//                    continue;
//                }
//
//                if (!startSaving) {
//                    if (tokens[0].trim().equals("20240107_0011")) {
//                        startSaving = true;
//                        System.out.println("👉 저장 시작 지점 발견: 줄 " + lineNo);
//                    } else {
//                        continue;
//                    }
//                }
//
//                try {
//                    NewsEntity news = new NewsEntity();
//                    news.setNewsId(tokens[0].trim());
//                    news.setWdate(LocalDateTime.parse(tokens[1].trim(), formatter));
//                    news.setTitle(tokens[2].trim());
//                    news.setArticle(tokens[3].trim());
//                    news.setPress(tokens[4].trim());
//                    news.setUrl(tokens[5].trim());
//                    news.setImage(tokens[6].trim());
//
//                    batch.add(news);
//                    success++;
//
//                    if (batch.size() >= 500) {
//                        newsRepository.saveAll(batch);
//                        batch.clear();
//                    }
//                } catch (Exception e) {
//                    System.err.println("❌ [줄 " + lineNo + "] 삽입 실패: " + e.getMessage());
//                    fail++;
//                }
//            }
//
//            if (!batch.isEmpty()) {
//                newsRepository.saveAll(batch);
//            }
//
//            System.out.println("✅ 처리 완료: 성공: " + success + " / 실패: " + fail);
//        }
//    }
//
//    private int countQuotes(String line) {
//        int count = 0;
//        for (char c : line.toCharArray()) {
//            if (c == '"') count++;
//        }
//        return count;
//    }
//
//    private String[] parseCsvLine(String csvLine) {
//        try (CSVReader reader = new CSVReader(new StringReader(csvLine))) {
//            return reader.readNext();
//        } catch (Exception e) {
//            return null;
//        }
//    }
//}
