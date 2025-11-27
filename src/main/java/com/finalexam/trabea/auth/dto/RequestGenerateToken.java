package com.finalexam.trabea.auth.dto;

import com.finalexam.trabea.role.RolesName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestGenerateToken {
    private String workEmail;
    private RolesName role;
}
