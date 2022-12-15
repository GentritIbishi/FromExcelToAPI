package com.gentritibishi.fromexceltoapi.service;

import com.gentritibishi.fromexceltoapi.helpers.ExcelHelper;
import com.gentritibishi.fromexceltoapi.models.Department;
import com.gentritibishi.fromexceltoapi.models.Employee;
import com.gentritibishi.fromexceltoapi.repository.DepartmentRepository;
import com.gentritibishi.fromexceltoapi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
public class ExcelService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    public void save(MultipartFile file) {
        try {
            List<Employee> employees = ExcelHelper.excelToEmployees(file.getInputStream());
            List<Department> departments = ExcelHelper.excelToDepartments(file.getInputStream());
            employeeRepository.saveAll(employees);
            departmentRepository.saveAll(departments);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    public List<Department> getAllDepartment() {
        return departmentRepository.findAll();
    }
}
