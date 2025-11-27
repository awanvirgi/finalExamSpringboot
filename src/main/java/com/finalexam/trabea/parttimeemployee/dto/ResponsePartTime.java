package com.finalexam.trabea.parttimeemployee.dto;

import com.finalexam.trabea.education.EducationType;
import com.finalexam.trabea.role.RolesName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class ResponsePartTime {
    private Integer id;
    private String firstName;
    private String lastName;
    private String personalEmail;
    private String personalPhoneNumber;
    private String workEmail;
    private EducationType lastEducation;
    private EducationType onGoingEducation;
    private LocalDate joinDate;
    private List<String> roles;
}
