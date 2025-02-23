package com.revotran.EmployeeRecordSystem.Controller;


import com.revotran.EmployeeRecordSystem.Model.Employee;
import com.revotran.EmployeeRecordSystem.Request.EmployeeRequest;
import com.revotran.EmployeeRecordSystem.Request.EmployeeUpdateRequest;
import com.revotran.EmployeeRecordSystem.Response.ApiResponse;
import com.revotran.EmployeeRecordSystem.Service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createEmployee(@Valid @RequestBody EmployeeRequest employeeRequest) {
        try {
            Employee savedEmployee = employeeService.createEmployee(employeeRequest);
            return ResponseEntity.ok().body(new ApiResponse(true,"Successfully created employee",savedEmployee));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Internal Server Error", e.getMessage()));
            }
    }

    @PutMapping("/update/{employeeId}")
    public ResponseEntity<ApiResponse> updateEmployee(
            @PathVariable String employeeId,
            @Valid @RequestBody EmployeeUpdateRequest updateRequest) {
        try {
            Employee updatedEmployee = employeeService.updateEmployee(employeeId, updateRequest);
            return ResponseEntity.ok().body(new ApiResponse(true,"Successfully Updated Employee",updatedEmployee));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Internal Server Error", e.getMessage()));
        }

    }
    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable String employeeId) {
        String message = employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse> getAllEmployees(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        try {
            List<Employee> employees = employeeService.getAllEmployees(search, page, size, sortBy, sortDir);
            return ResponseEntity.ok().body(new ApiResponse(true,"Data Fetched Successfully",employees));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Internal Server Error", e.getMessage()));
        }

    }

    @GetMapping("/get-by-empId/{employeeId}")
    public ResponseEntity<ApiResponse> getEmployeeById(@PathVariable String employeeId) {
        try {
            Employee employee = employeeService.getEmployeeById(employeeId);
            return ResponseEntity.ok().body(new ApiResponse(true,"Data Fetched Successfully",employee));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Internal Server Error", e.getMessage()));
        }

    }

}
