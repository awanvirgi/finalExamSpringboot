package com.finalexam.trabea.workshift.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseWorkShift {
    private Integer id;
    private LocalTime startTime;
    private LocalTime endTime;
}
