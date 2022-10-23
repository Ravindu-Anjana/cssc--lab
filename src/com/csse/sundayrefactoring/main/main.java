package com.csse.sundayrefactoring.main;

import com.csse.sundayrefactoring.service.EmployeeService;
import com.csse.sundayrefactoring.service.XMLTransformService;

import java.sql.SQLException;

public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws SQLException, ClassNotFoundException {

		EmployeeService a1 = new EmployeeService();
		try {
			XMLTransformService.transformXml();
			a1.parseXmlToDto();
			a1.executeSqlQuery();
			a1.insertAllEmployees();
//			employeeService.eMPLOYEEGETBYID("EMP10004");
//			employeeService.EMPLOYEEDELETE("EMP10001");
			a1.retrieveAllEmployees();
		} catch (Exception e) {
		}

	}

}
