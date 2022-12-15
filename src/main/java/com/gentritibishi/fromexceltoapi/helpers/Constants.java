package com.gentritibishi.fromexceltoapi.helpers;

public class Constants {
    public static final String SHEET = "employees";
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static String[] EMPLOYEE_HEADER = { "name",
            "manager", "username",
            "email", "department",
            "phone number", "address",
            "start date", "end date" };
    public static String[] DEPARTMENT_HEADER = { "department_name",
            "department_leader", "department_phone" };
}
