package com.gentritibishi.fromexceltoapi.controller;

import com.gentritibishi.fromexceltoapi.models.Department;
import com.gentritibishi.fromexceltoapi.models.Employee;
import com.gentritibishi.fromexceltoapi.repository.EmployeeRepository;
import com.gentritibishi.fromexceltoapi.service.ExcelService;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.gentritibishi.fromexceltoapi.helpers.Constants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ExcelController.class)
public class ExcelControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ExcelService service;

    @Test
    public void uploadFile() throws Exception {
        String fileName = "employees.xlsx";
        String filePath = "src/test/resources/";
        String contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        String parameterName = "file";
        Path path=Paths.get(filePath);
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
            MockMultipartFile mockMultipartFile = new MockMultipartFile(
                    parameterName,
                    fileName,
                    contentType,
                    content
            );
            MockMultipartHttpServletRequestBuilder multipartRequest = MockMvcRequestBuilders.multipart("/api/excel/upload");
            mvc.perform(multipartRequest.file(mockMultipartFile))
                    .andExpect(status().isOk())
                    .andExpect(result ->
                            {
                                result.getResponse().getContentAsString().matches("Uploaded the file successfully: " + fileName);
                            }
                    );
        }catch (final IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void getAllDepartments() throws Exception {
        Department department = new Department("IT", "john.doe", "(566) 576-1111");
        List<Department> allDepartmens = Arrays.asList(department);

        given(service.getAllDepartment()).willReturn(allDepartmens);
        mvc.perform(MockMvcRequestBuilders.get("/api/excel/departments"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].departmentName", is(department.getDepartmentName())));
    }

    @Test
    public void getAllEmployee() throws Exception {
        Employee employee = new Employee("gentrit.ibishi", "Gentrit Ibishi", "liz.erd", "gentritibishi@gmail.com"
        , "IT", "(566) 576-7814", "L.Dardania", "2020-02-02", "2022-05-10", "inactive");

        List<Employee> allEmployees = Arrays.asList(employee);

        given(service.getAllEmployees()).willReturn(allEmployees);
        mvc.perform(MockMvcRequestBuilders.get("/api/excel/employees"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(employee.getName())));
    }

    @Test
    public void getAllActiveEmployee() throws Exception {
        Employee employee = new Employee("gentrit.ibishi", "Gentrit Ibishi", "liz.erd", "gentritibishi@gmail.com"
                , "IT", "(566) 576-7814", "L.Dardania", "2020-02-02", "2022-05-10", "active");

        List<Employee> allEmployees = Arrays.asList(employee);

        given(service.getAllEmployeeByStatus(ACTIVE)).willReturn(allEmployees);
        mvc.perform(MockMvcRequestBuilders.get("/api/excel/employees?status="+ACTIVE))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].status", is(employee.getStatus())));
    }

    @Test
    public void getAllInActiveEmployee() throws Exception {
        Employee employee = new Employee("gentrit.ibishi", "Gentrit Ibishi", "liz.erd", "gentritibishi@gmail.com"
                , "IT", "(566) 576-7814", "L.Dardania", "2020-02-02", "2022-05-10", "active");

        List<Employee> allEmployees = Arrays.asList(employee);

        given(service.getAllEmployeeByStatus(INACTIVE)).willReturn(allEmployees);
        mvc.perform(MockMvcRequestBuilders.get("/api/excel/employees?status="+INACTIVE))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].status", is(employee.getStatus())));
    }
    @Test
    public void getAllEmployeeByFieldIdAndDirectionASC() throws Exception {
        String field = "id";
        Employee employee = new Employee("gentrit.ibishi", "Gentrit Ibishi", "liz.erd", "gentritibishi@gmail.com"
                , "IT", "(566) 576-7814", "L.Dardania", "2020-02-02", "2022-05-10", "inactive");
        Employee employee1 = new Employee("besnik.ibishi", "Besnik Ibishi", "liz.erd", "besnikibishi@gmail.com"
                , "IT", "(566) 576-7814", "L.Dardania", "2020-02-02", "2022-05-10", "active");
        List<Employee> allEmployees = Arrays.asList(employee, employee1);

        given(service.getAllEmployeeByFieldAndDirection(field, Sort.Direction.ASC)).willReturn(allEmployees);

        mvc.perform(MockMvcRequestBuilders.get("/api/excel/employees?field="+field+"&direction="+ASC))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(employee.getId())))
                .andExpect(jsonPath("$[1].id", is(employee1.getId())));
    }

    @Test
    public void getAllEmployeeByFieldIdAndDirectionDESC() throws Exception {
        String field = "id";
        Employee employee = new Employee("gentrit.ibishi", "Gentrit Ibishi", "liz.erd", "gentritibishi@gmail.com"
                , "IT", "(566) 576-7814", "L.Dardania", "2020-02-02", "2022-05-10", "inactive");
        Employee employee1 = new Employee("besnik.ibishi", "Besnik Ibishi", "liz.erd", "besnikibishi@gmail.com"
                , "IT", "(566) 576-7814", "L.Dardania", "2020-02-02", "2022-05-10", "active");
        List<Employee> allEmployees = Arrays.asList(employee, employee1);

        given(service.getAllEmployeeByFieldAndDirection(field, Sort.Direction.DESC)).willReturn(allEmployees);

        mvc.perform(MockMvcRequestBuilders.get("/api/excel/employees?field="+field+"&direction="+DESC))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(employee.getId())))
                .andExpect(jsonPath("$[1].id", is(employee1.getId())));
    }

    @Test
    public void getAllEmployeeByFieldNameAndDirectionASC() throws Exception {
        String field = "name";
        Employee employee = new Employee("gentrit.ibishi", "Gentrit Ibishi", "liz.erd", "gentritibishi@gmail.com"
                , "IT", "(566) 576-7814", "L.Dardania", "2020-02-02", "2022-05-10", "inactive");
        Employee employee1 = new Employee("besnik.ibishi", "Besnik Ibishi", "liz.erd", "besnikibishi@gmail.com"
                , "IT", "(566) 576-7814", "L.Dardania", "2020-02-02", "2022-05-10", "active");
        List<Employee> allEmployees = Arrays.asList(employee, employee1);

        given(service.getAllEmployeeByFieldAndDirection(field, Sort.Direction.ASC)).willReturn(allEmployees);

        mvc.perform(MockMvcRequestBuilders.get("/api/excel/employees?field="+field+"&direction="+ASC))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(employee.getId())))
                .andExpect(jsonPath("$[1].id", is(employee1.getId())));
    }

    @Test
    public void getAllEmployeeByFieldNameAndDirectionDESC() throws Exception {
        String field = "name";
        Employee employee = new Employee("gentrit.ibishi", "Gentrit Ibishi", "liz.erd", "gentritibishi@gmail.com"
                , "IT", "(566) 576-7814", "L.Dardania", "2020-02-02", "2022-05-10", "inactive");
        Employee employee1 = new Employee("besnik.ibishi", "Besnik Ibishi", "liz.erd", "besnikibishi@gmail.com"
                , "IT", "(566) 576-7814", "L.Dardania", "2020-02-02", "2022-05-10", "active");
        List<Employee> allEmployees = Arrays.asList(employee, employee1);

        given(service.getAllEmployeeByFieldAndDirection(field, Sort.Direction.DESC)).willReturn(allEmployees);

        mvc.perform(MockMvcRequestBuilders.get("/api/excel/employees?field="+field+"&direction="+DESC))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(employee.getId())))
                .andExpect(jsonPath("$[1].id", is(employee1.getId())));
    }

    @Test
    public void getAllEmployeeByDepartmentIT() throws Exception {
        String department = "IT";
        Employee employee = new Employee("gentrit.ibishi", "Gentrit Ibishi", "liz.erd", "gentritibishi@gmail.com"
                , "IT", "(566) 576-7814", "L.Dardania", "2020-02-02", "2022-05-10", "inactive");
        List<Employee> allEmployees = Arrays.asList(employee);

        given(service.getAllEmployeeByDepartment(department)).willReturn(allEmployees);

        String fullName = employee.getName();
        String[] fullNameArr = fullName.split(" ");

        mvc.perform(MockMvcRequestBuilders.get("/api/excel/employees?department="+department))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result ->
                        {
                            result.getResponse().getContentAsString().equals(fullNameArr[1]);
                        }
                );
    }

}
