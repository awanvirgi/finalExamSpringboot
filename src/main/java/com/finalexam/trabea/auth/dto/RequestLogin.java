package com.finalexam.trabea.auth.dto;


import com.finalexam.trabea.role.Role;
import com.finalexam.trabea.role.RolesName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestLogin {
    @NotNull
    @NotBlank
    @Email
    private final String email;
    @NotNull
    @NotBlank
    private final String password;
    @NotNull
    private final RolesName rolesName;
}
