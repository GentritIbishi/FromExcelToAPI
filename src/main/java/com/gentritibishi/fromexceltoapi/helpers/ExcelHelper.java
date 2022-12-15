package com.gentritibishi.fromexceltoapi.helpers;

import com.gentritibishi.fromexceltoapi.models.Department;
import com.gentritibishi.fromexceltoapi.models.Employee;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.gentritibishi.fromexceltoapi.helpers.Constants.SHEET;
import static com.gentritibishi.fromexceltoapi.helpers.Constants.TYPE;

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
                    if(cell == null || cell.getCellType() == CellType.BLANK)
                    {
                        //skip empty cell grow for one
                    }else
                    {
                        switch (colNum)
                        {
                            case 0:
                                employee.setName(cell.getStringCellValue()+"");
                                break;

                            case 1:
                                employee.setManager(cell.getStringCellValue()+"");
                                break;

                            case 2:
                                employee.setUsername(cell.getStringCellValue()+"");
                                break;

                            case 3:
                                employee.setEmail(cell.getStringCellValue()+"");
                                break;

                            case 4:
                                employee.setDepartment(cell.getStringCellValue()+"");
                                break;

                            case 5:
                                employee.setPhone_number(cell.getStringCellValue()+"");
                                break;

                            case 6:
                                employee.setAddress(cell.getStringCellValue()+"");
                                break;

                            case 7:
                                employee.setStart_date(cell.getNumericCellValue()+"");
                                break;

                            case 8:
                                employee.setEnd_date(cell.getNumericCellValue()+"");
                                break;

                            default:
                                break;
                        }
                    }

                }

                employees.add(employee);
            }

            workbook.close();

            return employees;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
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
                    if(cell == null || cell.getCellType() == CellType.BLANK)
                    {
                        //skip empty cell grow for one
                    }else
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
