package com.gentritibishi.fromexceltoapi.repository;

import com.gentritibishi.fromexceltoapi.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
