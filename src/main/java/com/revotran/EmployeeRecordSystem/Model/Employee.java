package com.revotran.EmployeeRecordSystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String employeeId;

    private String name;

    private int age;

    private String department;

    @Column(unique = true, nullable = false)
    private String mobileNumber;

    @Column(unique = true, nullable = false)
    private String email;

    private boolean isDeleted = false;


}
