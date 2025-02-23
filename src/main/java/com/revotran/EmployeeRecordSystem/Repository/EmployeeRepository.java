package com.revotran.EmployeeRecordSystem.Repository;

import com.revotran.EmployeeRecordSystem.Model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    boolean existsByEmail(String email);
    boolean existsByMobileNumber(String mobileNumber);
    Optional<Employee> findByEmployeeIdAndIsDeletedFalse(String employeeId);
    Page<Employee> findByIsDeletedFalseAndNameContainingIgnoreCaseOrDepartmentContainingIgnoreCase(
            String name, String department, Pageable pageable);

    Page<Employee> findByIsDeletedFalse(Pageable pageable);


}
