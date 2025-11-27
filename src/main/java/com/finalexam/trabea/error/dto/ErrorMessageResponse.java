package com.finalexam.trabea.error.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Data
@Builder
public class ErrorMessageResponse<T> {
    private final HttpStatus status;
    private final String message;
    private final T errors;
    private final ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Z"));
}
