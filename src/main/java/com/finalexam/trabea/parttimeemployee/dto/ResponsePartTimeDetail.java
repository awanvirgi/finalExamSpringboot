package com.finalexam.trabea.parttimeemployee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePartTimeDetail {
    private String fullName;
    private String personalEmail;
    private String workEmail;
    private String personalPhoneNumber;
}
