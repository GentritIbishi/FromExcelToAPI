package com.gentritibishi.fromexceltoapi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="department")
public class Department {
    @Id
    @Column(name = "department_name")
    private String department_name;

    @Column(name = "department_leader")
    private String department_leader;

    @Column(name = "department_phone")
    private String department_phone;

    public Department() {
    }

    public Department(String department_name, String department_leader, String department_phone) {
        this.department_name = department_name;
        this.department_leader = department_leader;
        this.department_phone = department_phone;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getDepartment_leader() {
        return department_leader;
    }

    public void setDepartment_leader(String department_leader) {
        this.department_leader = department_leader;
    }

    public String getDepartment_phone() {
        return department_phone;
    }

    public void setDepartment_phone(String department_phone) {
        this.department_phone = department_phone;
    }
}
