package com.gentritibishi.fromexceltoapi.repository;

import com.gentritibishi.fromexceltoapi.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, String> {
    boolean existsByDepartmentName (String department_name);
    Department getByDepartmentName(String department_name);

    boolean deleteByDepartmentName(String department_name);
}
