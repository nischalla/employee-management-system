package com.nischala.employee_management_system.mapper;

import com.nischala.employee_management_system.dto.EmployeeDTO;
import com.nischala.employee_management_system.model.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeDTO toDto(Employee employee);
    Employee toEntity(EmployeeDTO employeeDTO);
}
