package com.finalexam.trabea.parttimeemployee.dto;
import com.finalexam.trabea.education.EducationType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class RequestPartTime {
    @NotNull
    @NotBlank
    private final String firstName;
    @NotNull
    @NotBlank
    private final String lastName;
    @NotNull
    @NotBlank
    @Email
    private final String personalEmail;
    @NotNull
    @NotBlank
    @Pattern(regexp = "\\d{10,16}", message = "Nomor harus 10-16 digit angka")
    private final String personalPhoneNumber;
    @NotNull
    private final EducationType lastEducation;
    private final EducationType onGoingEducation;
}
