package com.nischala.employee_management_system.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {
    private Long id;
    private String name;
    private String department;
    private int age;
    private String email;
    private BigDecimal salary;
}
