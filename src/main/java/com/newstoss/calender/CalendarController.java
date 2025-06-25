package com.newstoss.calender;

import com.newstoss.global.response.SuccessResponse;
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
   @Operation(summary = "캘린더 조회", description = "연도와 달로 IR을 조회합니다. 뒤에 day를 붙히면 해당 날짜의 IR을 조회합니다. market 파라미터로 코스피, 코스닥(한글로 쳐야됨) 필터링 가능합니다.")
   public ResponseEntity<SuccessResponse<List<Calendar>>> getMonthCalen(
           @RequestParam Integer year,
           @RequestParam Integer month,
           @RequestParam(required = false) Integer day
   ) {
        if(day == null){
            List<Calendar> result = calendarRepository.findByYearAndMonth(year, month);
            return ResponseEntity.ok(new SuccessResponse<>(true, "캘린더 조회 성공", result));
        }
        else {
            List<Calendar> result = calendarRepository.findByYearAndMonthAndDay(year, month, day);
            return ResponseEntity.ok(new SuccessResponse<>(true, "캘린더 조회 성공", result));
        }
   }
}
