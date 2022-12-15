package com.gentritibishi.fromexceltoapi.helpers;

public class Constants {
    public static final String SHEET = "employees";
    public static final String ACTIVE = "active";
    public static final String INACTIVE = "inactive";
    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE = "yyyy-MM-dd";
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static String[] EMPLOYEE_HEADER = { "name",
            "manager", "username",
            "email", "department",
            "phone number", "address",
            "start date", "end date" };
    public static String[] DEPARTMENT_HEADER = { "department_name",
            "department_leader", "department_phone" };
}
