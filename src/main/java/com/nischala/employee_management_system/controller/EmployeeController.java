package com.nischala.employee_management_system.controller;

import com.nischala.employee_management_system.dto.EmployeeDTO;
import com.nischala.employee_management_system.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController 
@RequestMapping("/api/employees") 
@RequiredArgsConstructor // Automatically injects the service
public class EmployeeController {

    private final EmployeeService employeeService;

    // Create an Employee
    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO created = employeeService.createEmployee(employeeDTO);
        return ResponseEntity.ok(created);
    }

    // Get all Employees
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    // Get Employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    // Update Employee
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO dto) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, dto));
    }

    // Delete Employee
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    // Search Employees
    @GetMapping("/search")
    public ResponseEntity<List<EmployeeDTO>> search(@RequestParam("query") String query) {
        return ResponseEntity.ok(employeeService.searchEmployees(query));
    }
}
