package com.csse.sundayrefactoring.main;

import com.csse.sundayrefactoring.service.EmployeeService;
import com.csse.sundayrefactoring.service.XMLTransformService;

import java.sql.SQLException;

public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws SQLException, ClassNotFoundException {

		EmployeeService employeeService = new EmployeeService();
		try {
			XMLTransformService.transformXml();
			employeeService.parseXmlToDto();
			employeeService.executeSqlQuery();
			employeeService.insertAllEmployees();
/*
			employeeService.retrieveEmployeesById("EMP10004");
			employeeService.deleteEmployeeById("EMP10001");
*/
			employeeService.retrieveAllEmployees();
		} catch (Exception e) {
		}

	}

}
