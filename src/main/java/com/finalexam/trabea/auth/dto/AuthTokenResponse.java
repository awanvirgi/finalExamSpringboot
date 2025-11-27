package com.finalexam.trabea.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthTokenResponse {
    private final String username;
    private final String role;
    private final String token;
}
