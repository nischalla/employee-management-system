package com.nischala.employee_management_system.service;

import com.nischala.employee_management_system.dto.EmployeeDTO;
import com.nischala.employee_management_system.exception.ResourceNotFoundException;
import com.nischala.employee_management_system.mapper.EmployeeMapper;
import com.nischala.employee_management_system.model.Employee;
import com.nischala.employee_management_system.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * EmployeeServiceImpl implements core business logic for managing employee data,
 * including CRUD, search, pagination, and caching.
 */
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
    @Cacheable(value = "employees", key = "#id")
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
        return employeeMapper.toDto(employee);
    }

    @Override
    @Cacheable(value = "employeePages", key = "{#pageable.pageNumber, #pageable.pageSize}")
    public Page<EmployeeDTO> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable)
                .map(employeeMapper::toDto);
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));

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
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + id));
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

    @Override
    public Page<EmployeeDTO> searchEmployees(String keyword, Pageable pageable) {
        List<Employee> results = employeeRepository
                .findByNameContainingIgnoreCaseOrDepartmentContainingIgnoreCase(keyword, keyword);

        List<EmployeeDTO> mapped = results.stream()
                .map(employeeMapper::toDto)
                .toList();

        int start = Math.toIntExact(pageable.getOffset());
        int end = Math.min((start + pageable.getPageSize()), mapped.size());

        List<EmployeeDTO> subList = mapped.subList(start, end);
        return new PageImpl<>(subList, pageable, mapped.size());
    }


}
