package com.nischala.employee_management_system.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * ErrorResponse is a simple data structure for returning error messages and timestamps in exception responses.
 */
@Data
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private LocalDateTime timestamp;
}
