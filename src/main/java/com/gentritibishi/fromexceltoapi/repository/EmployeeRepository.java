package com.gentritibishi.fromexceltoapi.repository;

import com.gentritibishi.fromexceltoapi.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
    @Query("select (count(e) > 0) from Employee e where e.id = ?1")
    boolean ExistById(Long id);
    @Query("SELECT e FROM Employee e WHERE e.status = 'active'")
    List<Employee> findEmployeeByStatusActive();

    @Query("SELECT e FROM Employee e WHERE e.status = 'inactive'")
    List<Employee> findEmployeeByStatusInActive();

    @Query("SELECT e FROM Employee e WHERE e.department = ?1")
    List<Employee> findEmployeeByDepartment(String department);

    Boolean existsByEmail(String email);
    Employee getByEmail(String email);
    Boolean deleteByEmail(String email);
}
