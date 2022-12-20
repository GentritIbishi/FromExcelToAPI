package com.gentritibishi.fromexceltoapi.service;

import com.gentritibishi.fromexceltoapi.helpers.ExcelHelper;
import com.gentritibishi.fromexceltoapi.models.Department;
import com.gentritibishi.fromexceltoapi.models.Employee;
import com.gentritibishi.fromexceltoapi.repository.DepartmentRepository;
import com.gentritibishi.fromexceltoapi.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<Employee> getAllEmployeeByStatus(String status) {
        return employeeRepository.findByStatus(status);
    }

    public List<Department> getAllDepartment() {
        return departmentRepository.findAll();
    }

    public List<Employee> getAllEmployeeByFieldAndDirection(String field, Sort.Direction direction) {
        return employeeRepository.findAll(Sort.by(direction, field));
    }

    public List<Employee> getAllEmployeeByDepartment(String department) {
        return employeeRepository.findEmployeeByDepartment(department);
    }

    public Boolean checkExistsEmployeeByEmail(String email) {
        return employeeRepository.existsByEmail(email);
    }

    public Employee findAndGetEmployeeByEmail(String email) {
        return employeeRepository.getByEmail(email);
    }

    public Boolean findAndDeleteEmployeeByEmail(String email) {
        return employeeRepository.deleteByEmail(email);
    }

    public boolean checkExistsDepartmentByDepartmentName(String department_name) {
        return departmentRepository.existsByDepartmentName(department_name);
    }

    public Department findAndGetDepartmentByDepartmentName(String department_name) {
        return departmentRepository.getByDepartmentName(department_name);
    }

    public boolean findAndDeleteDepartmentByDepartmentName(String department_name) {
        return departmentRepository.deleteByDepartmentName(department_name);
    }

    @Transactional
    public void save(MultipartFile file) {
        try {
            List<Employee> employees = ExcelHelper.excelToEmployees(file.getInputStream());
            List<Department> departments = ExcelHelper.excelToDepartments(file.getInputStream());

            List<Employee> toAddEmployees = new ArrayList<>();
            List<Department> toAddDepartments = new ArrayList<>();

            //check if employee exist.
            // if exist check. if any change on it expect email
            // if not exist save add
            for (Employee employee : employees) {
                String email = String.valueOf(employee.getEmail());
                if (checkExistsEmployeeByEmail(email)) {
                    // if exist check. if any change on it expect email
                    Employee employeeInDb = findAndGetEmployeeByEmail(email);
                    Employee employeeInExcel = new Employee(
                            employee.getUsername(),
                            employee.getName(),
                            employee.getManager(),
                            employee.getEmail(),
                            employee.getDepartment(),
                            employee.getPhoneNumber(),
                            employee.getAddress(),
                            employee.getStartDate(),
                            employee.getEndDate(),
                            employee.getStatus()
                    );

                    if (!String.valueOf(employeeInDb.getUsername()).equals(String.valueOf(employeeInExcel.getUsername())) ||
                            !String.valueOf(employeeInDb.getName()).equals(String.valueOf(employeeInExcel.getName())) ||
                            !String.valueOf(employeeInDb.getManager()).equals(String.valueOf(employeeInExcel.getManager())) ||
                            !String.valueOf(employeeInDb.getEmail()).equals(String.valueOf(employeeInExcel.getEmail())) ||
                            !String.valueOf(employeeInDb.getDepartment()).equals(String.valueOf(employeeInExcel.getDepartment())) ||
                            !String.valueOf(employeeInDb.getPhoneNumber()).equals(String.valueOf(employeeInExcel.getPhoneNumber())) ||
                            !String.valueOf(employeeInDb.getAddress()).equals(String.valueOf(employeeInExcel.getAddress())) ||
                            !String.valueOf(employeeInDb.getStartDate()).equals(String.valueOf(employeeInExcel.getStartDate())) ||
                            !String.valueOf(employeeInDb.getEndDate()).equals(String.valueOf(employeeInExcel.getEndDate())) ||
                            !String.valueOf(employeeInDb.getStatus()).equals(String.valueOf(employeeInExcel.getStatus()))) {
                        // delete that employee
                        if (findAndDeleteEmployeeByEmail(email)) {
                            // save add to new list
                            toAddEmployees.add(employee);
                        } else {
                            System.out.println("Error: deleting employee with name: " + employee.getName());
                        }
                    }
                } else {
                    // if not exist save add
                    toAddEmployees.add(employee);
                }
            }

            //check if department exist.
            // if exist check. if any change on it expect department_name
            // if not exist save add
            for (Department department : departments) {
                String department_name = department.getDepartmentName();
                if (checkExistsDepartmentByDepartmentName(department_name)) {
                    // if exist check. if any change on it expect department_name
                    Department departmentInDb = findAndGetDepartmentByDepartmentName(department_name);
                    Department departmentInExcel = new Department(
                            department.getDepartmentName(),
                            department.getDepartmentLeader(),
                            department.getDepartmentPhone()
                    );

                    if (!String.valueOf(departmentInDb.getDepartmentName()).equals(String.valueOf(departmentInExcel.getDepartmentName()))
                            || !String.valueOf(departmentInDb.getDepartmentLeader()).equals(String.valueOf(departmentInExcel.getDepartmentLeader()))
                            || !String.valueOf(departmentInDb.getDepartmentPhone()).equals(String.valueOf(departmentInExcel.getDepartmentPhone()))) {
                        // delete that department
                        if (findAndDeleteDepartmentByDepartmentName(department_name)) {
                            // save add to new list
                            toAddDepartments.add(department);
                        } else {
                            System.out.println("Error: deleting employee with dName: " + department.getDepartmentName());
                        }
                    }

                } else {
                    // if not exist save add
                    toAddDepartments.add(department);
                }
            }

            employeeRepository.saveAll(toAddEmployees);
            departmentRepository.saveAll(toAddDepartments);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }


}


