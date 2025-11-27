package com.finalexam.trabea.education;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EducationType {
    SD("Elementary School"),
    SMP ("Junior High SChool"),
    SMA ("High School"),
    S1 ("University – Bachelor"),
    S2("University – Master"),
    S3("University – Doctorate");
    private final String description;
}
