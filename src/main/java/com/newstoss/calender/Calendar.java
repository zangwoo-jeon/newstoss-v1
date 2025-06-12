package com.newstoss.calender;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "calendar")
@ToString
@Getter
@Setter
@NoArgsConstructor
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long irId;
    private String companyName;
    private String title;
    private String place;
    private LocalDate date;
    private LocalTime time;
    private String market;
}
