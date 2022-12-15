package com.gentritibishi.fromexceltoapi.helpers;

import com.gentritibishi.fromexceltoapi.models.Department;
import com.gentritibishi.fromexceltoapi.models.Employee;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static com.gentritibishi.fromexceltoapi.helpers.Constants.*;

public class ExcelHelper {
    public static boolean hasExcelFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }

    public static List<Employee> excelToEmployees(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();

            List<Employee> employees = new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Employee employee = new Employee();

                for (int colNum = 0; colNum < 9; colNum++)
                {

                    Cell cell = currentRow.getCell(colNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellType(CellType.STRING);
                    if(!StringHelper.empty(String.valueOf(cell)))
                    {
                        switch (colNum)
                        {
                            case 0:
                                employee.setName(String.valueOf(cell.getStringCellValue()));
                                break;

                            case 1:
                                employee.setManager(String.valueOf(cell.getStringCellValue()));
                                break;

                            case 2:
                                employee.setUsername(String.valueOf(cell.getStringCellValue()));
                                break;

                            case 3:
                                employee.setEmail(String.valueOf(cell.getStringCellValue()));
                                break;

                            case 4:
                                employee.setDepartment(String.valueOf(cell.getStringCellValue()));
                                break;

                            case 5:
                                employee.setPhone_number(String.valueOf(cell.getStringCellValue()));
                                break;

                            case 6:
                                employee.setAddress(String.valueOf(cell.getStringCellValue()));
                                break;

                            case 7:
                                employee.setStart_date(DateHelper.formatDate(cell.getStringCellValue()));
                                break;

                            case 8:
                                employee.setEnd_date(DateHelper.formatDate(cell.getStringCellValue()));
                                break;

                            default:
                                break;
                        }
                    }

                }

                checkAndSetStatusToModel(employee);
                employees.add(employee);
            }

            workbook.close();

            return employees;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }

    public static void checkAndSetStatusToModel(Employee employee) {
        String end_date = employee.getEnd_date();

        int year = Integer.parseInt(end_date.substring(0,4));
        int month = Integer.parseInt(end_date.substring(5,7));
        int day = Integer.parseInt(end_date.substring(8));

        if(DateHelper.isAccountActive(year, month, day))
        {
            employee.setStatus(ACTIVE);
        }else {
            employee.setStatus(INACTIVE);
        }
    }

    public static List<Department> excelToDepartments(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);

            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();

            List<Department> departments = new ArrayList<>();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Department department = new Department();

                for (int colNum = 11; colNum < 14; colNum++)
                {

                    Cell cell = currentRow.getCell(colNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellType(CellType.STRING);
                    if(!StringHelper.empty(String.valueOf(cell)))
                    {
                        switch (colNum)
                        {
                            case 11:
                                department.setDepartment_name(String.valueOf(cell.getStringCellValue()));
                                break;
                            case 12:
                                department.setDepartment_leader(String.valueOf(cell.getStringCellValue()));
                                break;
                            case 13:
                                department.setDepartment_phone(String.valueOf(cell.getStringCellValue()));
                                break;

                            default:
                                break;
                        }
                    }

                }
                if(!StringHelper.empty(department.getDepartment_name()))
                {
                    departments.add(department);
                }
            }

            workbook.close();

            return departments;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
    }
}
