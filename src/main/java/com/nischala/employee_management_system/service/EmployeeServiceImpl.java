package com.nischala.employee_management_system.service;

import com.nischala.employee_management_system.dto.EmployeeDTO;
import com.nischala.employee_management_system.mapper.EmployeeMapper;
import com.nischala.employee_management_system.model.Employee;
import com.nischala.employee_management_system.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.toEntity(employeeDTO);
        Employee saved = employeeRepository.save(employee);
        return employeeMapper.toDto(saved);
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
        return employeeMapper.toDto(employee);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));

        // Update fields
        existing.setName(employeeDTO.getName());
        existing.setDepartment(employeeDTO.getDepartment());
        existing.setAge(employeeDTO.getAge());
        existing.setEmail(employeeDTO.getEmail());
        existing.setSalary(employeeDTO.getSalary());

        Employee updated = employeeRepository.save(existing);
        return employeeMapper.toDto(updated);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
        employeeRepository.delete(existing);
    }

    @Override
    public List<EmployeeDTO> searchEmployees(String keyword) {
        List<Employee> results = employeeRepository
                .findByNameContainingIgnoreCaseOrDepartmentContainingIgnoreCase(keyword, keyword);
        return results.stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }
}
