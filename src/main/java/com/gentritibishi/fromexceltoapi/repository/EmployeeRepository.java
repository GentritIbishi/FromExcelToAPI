package com.gentritibishi.fromexceltoapi.repository;

import com.gentritibishi.fromexceltoapi.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    @Query("SELECT e FROM Employee e WHERE e.status = 'active'")
    public List<Employee> findEmployeeByStatusActive();

    @Query("SELECT e FROM Employee e WHERE e.status = 'inactive'")
    public List<Employee> findEmployeeByStatusInActive();
}
