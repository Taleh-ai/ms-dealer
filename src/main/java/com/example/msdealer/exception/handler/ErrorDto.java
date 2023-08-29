package com.example.msdealer.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDto {
    ErrorResponse errorResponse;
    private int statusCode;
    private boolean success;
}
