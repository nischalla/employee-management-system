package com.nischala.employee_management_system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity 
@Table(name = "employees") 
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
@EntityListeners(AuditingEntityListener.class) 
public class Employee {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @NotBlank(message = "Name is required") 
    private String name;

    @NotBlank(message = "Department is required")
    private String department;

    @Min(value = 18, message = "Minimum age is 18")
    private int age;

    @Email(message = "Invalid email address")
    private String email;

    @DecimalMin(value = "30000.00", message = "Minimum salary is 30000.00")
    private BigDecimal salary;

    @CreatedDate 
    private LocalDateTime createdAt;

    @LastModifiedDate 
    private LocalDateTime updatedAt;
}

