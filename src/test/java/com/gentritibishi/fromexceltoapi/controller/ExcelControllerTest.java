package com.gentritibishi.fromexceltoapi.controller;

import com.gentritibishi.fromexceltoapi.models.Department;
import com.gentritibishi.fromexceltoapi.models.Employee;
import com.gentritibishi.fromexceltoapi.repository.EmployeeRepository;
import com.gentritibishi.fromexceltoapi.service.ExcelService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
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

        given(service.getAllActiveEmployee()).willReturn(allEmployees);
        mvc.perform(MockMvcRequestBuilders.get("/api/excel/employees/active"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].status", is(employee.getStatus())));
    }

    @Test
    public void getAllInActiveEmployee() throws Exception {
        Employee employee = new Employee("gentrit.ibishi", "Gentrit Ibishi", "liz.erd", "gentritibishi@gmail.com"
                , "IT", "(566) 576-7814", "L.Dardania", "2020-02-02", "2022-05-10", "inactive");

        List<Employee> allEmployees = Arrays.asList(employee);

        given(service.getAllInActiveEmployee()).willReturn(allEmployees);
        mvc.perform(MockMvcRequestBuilders.get("/api/excel/employees/inactive"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].status", is(employee.getStatus())));
    }

    @Test
    public void getAllEmployeeByFieldWithSortASC() throws Exception {
        String field = "id";
        Employee employee = new Employee("gentrit.ibishi", "Gentrit Ibishi", "liz.erd", "gentritibishi@gmail.com"
                , "IT", "(566) 576-7814", "L.Dardania", "2020-02-02", "2022-05-10", "inactive");
        Employee employee1 = new Employee("besnik.ibishi", "Besnik Ibishi", "liz.erd", "besnikibishi@gmail.com"
                , "IT", "(566) 576-7814", "L.Dardania", "2020-02-02", "2022-05-10", "active");
        List<Employee> allEmployees = Arrays.asList(employee, employee1);

        given(service.getAllEmployeeByFieldWithSortASC(field)).willReturn(allEmployees);

        mvc.perform(MockMvcRequestBuilders.get("/api/excel/employees/"+field+"/asc"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(employee.getId())))
                .andExpect(jsonPath("$[1].id", is(employee1.getId())));
    }

    @Test
    public void getAllEmployeeByFieldWithSortDESC() throws Exception {
        String field = "id";
        Employee employee = new Employee("gentrit.ibishi", "Gentrit Ibishi", "liz.erd", "gentritibishi@gmail.com"
                , "IT", "(566) 576-7814", "L.Dardania", "2020-02-02", "2022-05-10", "inactive");
        Employee employee1 = new Employee("besnik.ibishi", "Besnik Ibishi", "liz.erd", "besnikibishi@gmail.com"
                , "IT", "(566) 576-7814", "L.Dardania", "2020-02-02", "2022-05-10", "active");
        List<Employee> allEmployees = Arrays.asList(employee, employee1);

        given(service.getAllEmployeeByFieldWithSortDESC(field)).willReturn(allEmployees);

        mvc.perform(MockMvcRequestBuilders.get("/api/excel/employees/"+field+"/desc"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(employee.getId())))
                .andExpect(jsonPath("$[1].id", is(employee1.getId())));
    }

    @Test
    public void getAllEmployeeByDepartment() throws Exception {
        String department = "IT";
        Employee employee = new Employee("gentrit.ibishi", "Gentrit Ibishi", "liz.erd", "gentritibishi@gmail.com"
                , "IT", "(566) 576-7814", "L.Dardania", "2020-02-02", "2022-05-10", "inactive");
        List<Employee> allEmployees = Arrays.asList(employee);

        given(service.getAllEmployeeByDepartment(department)).willReturn(allEmployees);

        String fullName = employee.getName();
        String[] fullNameArr = fullName.split(" ");

        mvc.perform(MockMvcRequestBuilders.get("/api/excel/employees/"+department))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result ->
                        {
                            result.getResponse().getContentAsString().matches(fullNameArr[1]);
                        }
                );
    }

}