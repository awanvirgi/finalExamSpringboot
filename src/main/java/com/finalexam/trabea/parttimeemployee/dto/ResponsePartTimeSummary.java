package com.finalexam.trabea.parttimeemployee.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePartTimeSummary {
    private Integer id;
    private String fullName;
    private String personalEmail;
    private String workEmail;
    private String personalPhoneNumber;
    private LocalDate joinDate;
}
