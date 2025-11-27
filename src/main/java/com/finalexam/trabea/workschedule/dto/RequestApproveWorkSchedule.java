package com.finalexam.trabea.workschedule.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class RequestApproveWorkSchedule {
    @NotNull
    private final Boolean approve;
}
