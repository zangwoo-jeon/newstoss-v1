package com.newstoss.calender;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CalendarController {
    private final CalendarRepository calendarRepository;

   @GetMapping("/calen")
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
