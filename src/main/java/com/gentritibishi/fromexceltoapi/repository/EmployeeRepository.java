package com.gentritibishi.fromexceltoapi.repository;

import com.gentritibishi.fromexceltoapi.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    @Query("SELECT e FROM Employee e WHERE e.status = ?1")
    List<Employee> findByStatus(String status);

    @Query("SELECT e FROM Employee e WHERE e.department = ?1")
    List<Employee> findEmployeeByDepartment(String department);

    Boolean existsByEmail(String email);
    Employee getByEmail(String email);
    Boolean deleteByEmail(String email);
}
