package com.revotran.EmployeeRecordSystem.ConstantAndEnum;
import com.revotran.EmployeeRecordSystem.Repository.EmployeeRepository;
import com.revotran.EmployeeRecordSystem.Request.EmployeeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Constant {
    private final EmployeeRepository employeeRepository;
    public void validateUniqueEmployee(EmployeeRequest empRequest) {

        if (employeeRepository.existsByEmail(empRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (employeeRepository.existsByMobileNumber(empRequest.getMobileNumber())) {
            throw new RuntimeException("Mobile number already exists");
        }
    }

    public String generateEmployeeId() {
        return "EMP-" + "REV"+ UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
