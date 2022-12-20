# FromExcelToAPI
[![License MIT](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/GentritIbishi/FromExcelToAPI/blob/master/LICENSE)

Spring Boot Application - Import data from Excel to MySQL Database

## Features

* Excel file upload extension support " .xlsx ".
* The application before uploading to the database checks:


* * If it exists on the database
* * * It then checks for any modifications:

* * * * "Phone Number" column has been changed, then it updates that row with the last modification.

* * * * If it turns out that the row has not been modified then it does not insert it into the database as a duplicate.

* * If it does not exist on the database
* * * Then enter it directly into the base date..

### Screenshots Endpoints - METHOD URL ACTION

> POST /api/excel/upload Upload an Excel File and Insert Data into MySQL Database

#### First Time Request Uploading

![First Time - Uploading file](screenshots/uploadEndpoint/first_upload_endpoint.png) 

> First Time Request - Department Table

![First Time - Department Table](screenshots/uploadEndpoint/first_department_table.png) 

> First Time Request - Employee Table

![First Time - Employee Table Part 1](screenshots/uploadEndpoint/first_employee_table_1.png) 
![First Time - Employee Table Part 2](screenshots/uploadEndpoint/first_employee_table_2.png) 

#### Second Time Request Uploading

![Second Time - Uploading file](screenshots/uploadEndpoint/second_upload_endpoint.png) 

> Second Time Request - Department Table

![Second Time - Department Table](screenshots/uploadEndpoint/second_department_table.png) 

> Second Time Request - Employee Table

![Second Time - Employee Table Part 1](screenshots/uploadEndpoint/second_employee_table_1.png) 
![Second Time - Employee Table Part 2](screenshots/uploadEndpoint/second_employee_table_2.png) 

> GET	/api/excel/department Get List of departments in db table

![Response as JSON List of departments](screenshots/departmentsEndpoint/endpoint.png) 

> GET	/api/excel/employees	Get List of employees in db table

![Response as JSON List of employees](screenshots/employeeEndpoint/endpoint.png) 

> GET	/api/excel/employees?status=active	Get List of employees active in db table

![Response as JSON List of active employees](screenshots/employeeEndpoint/employee_field_status_active.png) 

> GET	/api/excel/employees?status=inactive Get List of employees inactive in db table

![Response as JSON List of inactive employees](screenshots/employeeEndpoint/employee_field_status_inactive.png) 

> GET	/api/excel/employees?field=id&direction=asc Get List of employees in ascending by field

![Response as JSON List of employees in Ascending](screenshots/employeeEndpoint/employee_field_id_direction_asc.png) 

> GET	/api/excel/employees?field=id&direction=desc Get List of employees in descending by field

![Response as JSON List of employees in Descending](screenshots/employeeEndpoint/employee_field_id_direction_desc.png) 

> GET	/api/excel/employees?field=name&direction=asc Get List of employees in ascending by field

![Response as JSON List of employees in Ascending](screenshots/employeeEndpoint/employee_field_name_direction_asc.png) 

> GET	/api/excel/employees?field=name&direction=desc Get List of employees in descending by field

![Response as JSON List of employees in Descending](screenshots/employeeEndpoint/employee_field_name_direction_desc.png) 

> GET	/employees?department=IT Get List of last name of employees by department we want for ex: IT department.

![List lastNames of employees by department: IT in name field](screenshots/employeeEndpoint/employee_departmant_it.png)

> GET	/employees?department=Sales Get List of last name of employees by department we want for ex: Sales department.

![List lastNames of employees by department: Sales in name field](screenshots/employeeEndpoint/employee_departmant_sales.png) 

### Unit test Screenshots

> Unit test - Uploading Dile - uploadFile()

![Unit test - Uploading file](screenshots/unitTests/unit_test_uploadFile.png) 

> Unit test - Get all Departments - getAllDepartments()

![Unit test - Get all departments](screenshots/unitTests/unit_test_getAllDepartments.png) 

> Unit test - Get all Employee - getAllEmployee()

![Unit test - Get all employee](screenshots/unitTests/unit_test_getAllEmployee.png) 

> Unit test - Get all Active Employee - getAllActiveEmployee()

![Unit test - Get all active employee](screenshots/unitTests/unit_test_getAllActiveEmployee.png) 

> Unit test - Get all InActive Employee - getAllInActiveEmployee()

![Unit test - Get all inactive employee](screenshots/unitTests/unit_test_getAllInActiveEmployee.png) 

> Unit test - Get all employee by field with sort ASC - getAllEmployeeByFieldWithSortASC()

![Unit test - Get all employee by field with sort ASC](screenshots/unitTests/unit_test_getAllEmployeeByFieldWithSortASC.png) 

> Unit test - Get all employee by field with sort DESC - getAllEmployeeByFieldWithSortDESC()

![Unit test - Get all employee by field with sort DESC](screenshots/unitTests/unit_test_getAllEmployeeByFieldWithSortDESC.png) 

> Unit test - Get all employee by department - getAllEmployeeByDepartment()

![Unit test - Get all employee by department](screenshots/unitTests/unit_test_getAllEmployeeByDepartment.png) 

### Requirements
* Java 17
* Apache POI Common dependency
* MySQL dependency
* Joda-time dependency
* JUnit Vintage Engine
* Hamcrest Core

## Installation
1. Press the **Fork** button (top right the page) to save copy of this project on your account.
2. Download the repository files (project) from the download section or clone this project by typing in the bash the following command:

       git clone https://github.com/GentritIbishi/FromExcelToAPI
3. Import it in Intellij IDEA or any other Java IDE and let Maven download the required dependencies for you.
4. Run the application 

* First page uploading file

![First page uploading file](screenshots/runningApplication/firstPage.png) 

* Success page after uploading file

![First page uploading file](screenshots/runningApplication/SuccessPage.png) 

## Contributing 💡
If you want to contribute to this project and make it better with new ideas, your pull request is very welcomed.
If you find any issue just put it in the repository issue section, thank you.

