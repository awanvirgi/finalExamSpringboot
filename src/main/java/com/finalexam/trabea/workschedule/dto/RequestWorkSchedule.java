package com.finalexam.trabea.workschedule.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestWorkSchedule {
    @NotNull
    @FutureOrPresent
    private LocalDate workDate;
    @NotNull
    private Integer workShiftId;
}
