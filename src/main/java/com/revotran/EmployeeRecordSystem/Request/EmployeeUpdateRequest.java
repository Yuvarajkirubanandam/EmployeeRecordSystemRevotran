package com.revotran.EmployeeRecordSystem.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeUpdateRequest {
    private String name;

    @Min(value = 18, message = "Age must be at least 18")
    private Integer age; // Use Integer instead of int to allow null values

    private String department;

    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid mobile number. Must be 10 digits and start with 6-9")
    private String mobileNumber;

    @Email(message = "Invalid email format")
    private String email;
}
