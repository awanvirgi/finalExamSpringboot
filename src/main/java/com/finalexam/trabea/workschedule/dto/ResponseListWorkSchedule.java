package com.finalexam.trabea.workschedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseListWorkSchedule {
    private LocalDate startDay;
    private LocalDate endDay;
    private List<ResponseWorkSchedule> WorkSchedules;
}
