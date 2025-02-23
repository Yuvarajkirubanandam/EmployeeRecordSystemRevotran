package com.revotran.EmployeeRecordSystem.ServiceImpl;

import com.revotran.EmployeeRecordSystem.ConstantAndEnum.Constant;
import com.revotran.EmployeeRecordSystem.Exception.ResourceNotFoundException;
import com.revotran.EmployeeRecordSystem.Model.Employee;
import com.revotran.EmployeeRecordSystem.Repository.EmployeeRepository;
import com.revotran.EmployeeRecordSystem.Request.EmployeeRequest;
import com.revotran.EmployeeRecordSystem.Request.EmployeeUpdateRequest;
import com.revotran.EmployeeRecordSystem.Service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final Constant constant;
    private final EmployeeRepository employeeRepository;
    @Override
    public Employee createEmployee(EmployeeRequest employeeRequest) {
        constant.validateUniqueEmployee(employeeRequest);
        long count = employeeRepository.count();
        String generatedId = constant.generateEmployeeId() + (count + 1);
        Employee employee = mapToEmployee(employeeRequest);
        employee.setEmployeeId(generatedId+(count+1));
        return employeeRepository.save(employee);
    }

    private Employee mapToEmployee(EmployeeRequest employeeRequest) {
        return new Employee(
                null,
                null,
                employeeRequest.getName(),
                employeeRequest.getAge(),
                employeeRequest.getDepartment(),
                employeeRequest.getMobileNumber(),
                employeeRequest.getEmail(),
                false
        );
    }

    @Override
    public Employee updateEmployee(String employeeId, EmployeeUpdateRequest updateRequest) {

        Employee employee = employeeRepository.findByEmployeeIdAndIsDeletedFalse(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + employeeId));

        if (updateRequest.getName() != null) {
            employee.setName(updateRequest.getName());
        }
        if (updateRequest.getAge() != null) {
            employee.setAge(updateRequest.getAge());
        }
        if (updateRequest.getDepartment() != null) {
            employee.setDepartment(updateRequest.getDepartment());
        }
        if (updateRequest.getMobileNumber() != null) {
            employee.setMobileNumber(updateRequest.getMobileNumber());
        }
        if (updateRequest.getEmail() != null) {
            employee.setEmail(updateRequest.getEmail());
        }

        return employeeRepository.save(employee);
    }

    @Override
    public String deleteEmployee(String employeeId) {
        Employee employee = employeeRepository.findByEmployeeIdAndIsDeletedFalse(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + employeeId));

        employee.setDeleted(true);
        employeeRepository.save(employee);

        return "Employee with ID " + employeeId + " has been deleted successfully.";
    }

    @Override
    public List<Employee> getAllEmployees(String search, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Employee> employees;
        if (search != null && !search.trim().isEmpty()) {
            employees = employeeRepository.findByIsDeletedFalseAndNameContainingIgnoreCaseOrDepartmentContainingIgnoreCase(
                    search, search, pageable);
        } else {
            employees = employeeRepository.findByIsDeletedFalse(pageable);
        }

        if (employees.isEmpty()) {
            throw new ResourceNotFoundException("No employees found.");
        }

        return employees.getContent();
    }

    @Override
    public Employee getEmployeeById(String employeeId) {
        return employeeRepository.findByEmployeeIdAndIsDeletedFalse(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + employeeId));
    }

}
