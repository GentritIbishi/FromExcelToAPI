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
    private String departmentName;

    @Column(name = "department_leader")
    private String departmentLeader;

    @Column(name = "department_phone")
    private String departmentPhone;

    public Department() {
    }

    public Department(String departmentName, String departmentLeader, String departmentPhone) {
        this.departmentName = departmentName;
        this.departmentLeader = departmentLeader;
        this.departmentPhone = departmentPhone;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentLeader() {
        return departmentLeader;
    }

    public void setDepartmentLeader(String departmentLeader) {
        this.departmentLeader = departmentLeader;
    }

    public String getDepartmentPhone() {
        return departmentPhone;
    }

    public void setDepartmentPhone(String departmentPhone) {
        this.departmentPhone = departmentPhone;
    }
}
