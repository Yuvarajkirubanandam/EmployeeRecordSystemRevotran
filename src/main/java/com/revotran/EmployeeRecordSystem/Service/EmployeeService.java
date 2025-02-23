package com.revotran.EmployeeRecordSystem.Service;

import com.revotran.EmployeeRecordSystem.Model.Employee;
import com.revotran.EmployeeRecordSystem.Request.EmployeeRequest;
import com.revotran.EmployeeRecordSystem.Request.EmployeeUpdateRequest;

import java.util.List;

public interface EmployeeService {

    Employee createEmployee(EmployeeRequest employeeRequest);

    Employee updateEmployee(String employeeId, EmployeeUpdateRequest updateRequest);

    String deleteEmployee(String employeeId);

    List<Employee> getAllEmployees(String search, int page, int size, String sortBy, String sortDir);

    Employee getEmployeeById(String employeeId);

}
