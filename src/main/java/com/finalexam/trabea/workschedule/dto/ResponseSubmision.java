package com.finalexam.trabea.workschedule.dto;

import com.finalexam.trabea.workshift.dto.ResponseWorkShift;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseSubmision {
    private Integer workScheduleId;
    private Integer partTimeId;
    private String fullname;
    private LocalDate workDate;
    private ResponseWorkShift shift;
}
