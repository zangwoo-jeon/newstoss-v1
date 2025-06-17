package com.newstoss.calender;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/calen")
@RequiredArgsConstructor
public class CalendarController {
   private final CalendarRepository calendarRepository;

   //캘린더 조회
   @GetMapping("")
   @Operation(summary = "캘린더 조회", description = "연도와 달로 IR을 조회합니다. 뒤에 day를 붙히면 해당 날짜의 IR을 조회합니다.")
   public ResponseEntity<List<Calendar>> getMonthCalen(
           @RequestParam Integer year,
           @RequestParam Integer month,
           @RequestParam(required = false) Integer day
   ) {
       if (day == null) {
        return ResponseEntity.ok(calendarRepository.findByYearAndMonth(year, month));
       }else{
        return ResponseEntity.ok(calendarRepository.findByYearAndMonthAndDay(year, month, day));
       }
}
}
