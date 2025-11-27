package com.finalexam.trabea.parttimeemployee.dto;

import com.finalexam.trabea.education.EducationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponsePartTimeForm {
    private String id;
    private  String firstName;
    private  String lastName;
    private  String personalEmail;
    private  String personalPhoneNumber;
    private EducationType lastEducation;
    private  EducationType onGoingEducation;
}
