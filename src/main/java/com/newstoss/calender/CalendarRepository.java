package com.newstoss.calender;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    @Query("SELECT c FROM Calendar c WHERE YEAR(c.date) = :year AND MONTH(c.date) = :month")
    List<Calendar> findByYearAndMonth(@Param("year") Integer year, @Param("month") Integer month);

    @Query("SELECT c FROM Calendar c WHERE YEAR(c.date) = :year AND MONTH(c.date) = :month AND DAY(c.date) = :day")
    List<Calendar> findByYearAndMonthAndDay(@Param("year") Integer year, @Param("month") Integer month, @Param("day") Integer day);
}
