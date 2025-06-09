//package com.newstoss.news2.adapter;
//
//import com.newstoss.news2.application.CsvImportService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.io.File;
//
//@Component
//@RequiredArgsConstructor
//public class CsvRunner implements CommandLineRunner {
//
//    private final CsvImportService csvImportService;
//
//    @Override
//    public void run(String... args) {
//        String path = "C:/Users/user/Downloads/news_2023_2025.csv";// 기본 경로
//
//        // 선택적으로 CLI 인자에서 파일 경로 받기
//        if (args.length > 0) {
//            path = args[0];
//        }
//
//        File file = new File(path);
//        if (!file.exists()) {
//            System.err.println("❌ 파일이 존재하지 않습니다: " + path);
//            return;
//        }
//
//        try {
//            csvImportService.importCsv(file);
//            System.out.println("✅ CSV 처리 완료: " + path);
//        } catch (Exception e) {
//            System.err.println("❌ CSV 처리 중 오류 발생: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//}
