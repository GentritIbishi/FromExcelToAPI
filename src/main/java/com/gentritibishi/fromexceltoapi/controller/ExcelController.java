package com.gentritibishi.fromexceltoapi.controller;

import com.gentritibishi.fromexceltoapi.helpers.ExcelHelper;
import com.gentritibishi.fromexceltoapi.helpers.StringHelper;
import com.gentritibishi.fromexceltoapi.message.ResponseMessage;
import com.gentritibishi.fromexceltoapi.models.Department;
import com.gentritibishi.fromexceltoapi.models.Employee;
import com.gentritibishi.fromexceltoapi.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.gentritibishi.fromexceltoapi.helpers.Constants.ASC;
import static com.gentritibishi.fromexceltoapi.helpers.Constants.DESC;

@CrossOrigin("http://localhost:8080")
@Controller
@RequestMapping("/api/excel")
public class ExcelController {
    @Autowired
    private ExcelService fileService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (ExcelHelper.hasExcelFormat(file)) {
            try {
                fileService.save(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!" + " Error: " + e.getMessage();
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @GetMapping("/departments")
    public ResponseEntity<List<Department>> getAllDepartments() {
        try {
            List<Department> departments = fileService.getAllDepartment();

            if (departments.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(departments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployee(@RequestParam(value = "status", required = false) String status,
                                                         @RequestParam(value = "field", required = false) String field,
                                                         @RequestParam(value = "direction", required = false) String direction,
                                                         @RequestParam(value = "department", required = false) String department) {
        try {
            List<Employee> employees = new ArrayList<>();
            if (!StringHelper.empty(status)) {
                employees = fileService.getAllEmployeeByStatus(status);

                if (employees.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }

                return new ResponseEntity<>(employees, HttpStatus.OK);
            } else if (!StringHelper.empty(field) && !StringHelper.empty(direction)) {
                if (direction.equalsIgnoreCase(ASC)) {
                    employees = fileService.getAllEmployeeByFieldAndDirection(field, Sort.Direction.ASC);
                } else if (direction.equalsIgnoreCase(DESC)) {
                    employees = fileService.getAllEmployeeByFieldAndDirection(field, Sort.Direction.DESC);
                }

                if (employees.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(employees, HttpStatus.OK);
                }
            } else if(!StringHelper.empty(department)){
                employees = fileService.getAllEmployeeByDepartment(department);

                List<Employee> lastNames = new ArrayList<>();
                for (int i = 0; i < employees.size(); i++) {
                    //for each employee get last name and list
                    String fullName = employees.get(i).getName();
                    String[] fullNameArr = fullName.split(" ");
                    if (fullNameArr.length > 2) {
                        Employee lEmployee = new Employee(fullNameArr[2]);
                        lastNames.add(lEmployee);
                    } else if (fullNameArr.length == 2) {
                        Employee lEmployee = new Employee(fullNameArr[1]);
                        lastNames.add(lEmployee);
                    }
                }

                if (employees.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }

                return new ResponseEntity<>(lastNames, HttpStatus.OK);
            }else{
                employees = fileService.getAllEmployees();

                if (employees.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }

                return new ResponseEntity<>(employees, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
