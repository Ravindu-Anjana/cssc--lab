package com.csse.sundayrefactoring.service;

import com.csse.sundayrefactoring.config.PropertyConfigs;
import com.csse.sundayrefactoring.model.Employee;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

public class EmployeeService extends PropertyConfigs {

	private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
	private final ArrayList<Employee>  employeesList = new ArrayList<Employee>();
	private static Connection connection;
	private static Statement statement;
	private PreparedStatement preparedStatement;

	public static final String QUERY_1 = "q1";
	public static final String QUERY_2 = "q2";
	public static final String QUERY_3 = "q3";
	public static final String QUERY_4 = "q4";
	public static final String QUERY_5 = "q5";
	public static final String QUERY_6 = "q6";
	public static final String JDBC_NAME ="com.mysql.jdbc.Driver" ;
	public static final String URL = "url";
	public static final String USERNAME = "username";
	public static final String PASSWORD= "password";
	private static final String XPATH_EMPLOYEE_ID_KEY="XpathEmployeeIDKey";
	private static final String XPATH_EMPLOYEE_NAME_KEY="XpathEmployeeNameKey";
	private static final String XPATH_EMPLOYEE_ADDRESS_KEY="XpathEmployeeAddressKey";
	private static final String XPATH_FACULTY_NAME_KEY="XpathFacultyNameKey";
	private static final String XPATH_DEPARTMENT_KEY="XpathDepartmentKey";
	private static final String XPATH_DESIGNATION_KEY="XpathDesignationKey";


	public EmployeeService() throws SQLException, ClassNotFoundException {
		try {
			Class.forName(JDBC_NAME);
			connection = DriverManager.getConnection(properties.getProperty(URL), properties.getProperty(USERNAME),
					properties.getProperty(PASSWORD));
		} catch (SQLException | ClassNotFoundException exception) {
			logger.error("EmployeeService: Exception {}", exception);
//			throw ex;
		} 
	}

	public void parseXmlToDto() throws Exception {

		try {
			int size = XMLTransformService.getXmlXpath().size();
			for (int i = 0; i < size; i++) {
				Map<String, String> l = XMLTransformService.getXmlXpath().get(i);//rename variable
				Employee employee = new Employee();
				employee.setEmployeeId(l.get(XPATH_EMPLOYEE_ID_KEY));
				employee.setFullName(l.get(XPATH_EMPLOYEE_NAME_KEY));
				employee.setAddress(l.get(XPATH_EMPLOYEE_ADDRESS_KEY));
				employee.setFacultyName(l.get(XPATH_FACULTY_NAME_KEY));
				employee.setDepartment(l.get(XPATH_DEPARTMENT_KEY));
				employee.setDesignation(l.get(XPATH_DESIGNATION_KEY));
				employeesList.add(employee);
				System.out.println(employee + "\n");
			}
		} catch (Exception exception) {
			logger.error("parseXmlToDto : Exception {}", exception.getMessage());
			throw exception;
		}
	}

	public void executeSqlQuery() throws Exception {

		try {
			statement = connection.createStatement();
			statement.executeUpdate(XMLInspectService.findElementById(QUERY_2));
			statement.executeUpdate(XMLInspectService.findElementById(QUERY_1));

		} catch (SQLException sqlException) {
			logger.error("executeSqlQuery: SQLException {}", sqlException.getMessage());
			//throw ex;
		} catch (Exception exception) {
			logger.error("executeSqlQuery: SQLException {}", exception.getMessage());
			//throw ex;
		}
	}

	public void insertAllEmployees() throws Exception {
		try {
			preparedStatement = connection.prepareStatement(XMLInspectService.findElementById(QUERY_3));
			connection.setAutoCommit(false);
			for(Employee employee : employeesList){
				preparedStatement.setString(1, employee.getEmployeeId());
				preparedStatement.setString(2, employee.getFullName());
				preparedStatement.setString(3, employee.getAddress());
				preparedStatement.setString(4, employee.getFacultyName());
				preparedStatement.setString(5, employee.getDepartment());
				preparedStatement.setString(6, employee.getDesignation());
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
			connection.commit();
		} catch (Exception exception) {
			logger.error("insertAllEmployees: SQLException {}", exception.getMessage());
			//throw ex;
		}
	}

	public void retrieveEmployeesById(String employeeId) throws Exception {

		Employee employee = new Employee();
		try {
			preparedStatement = connection.prepareStatement(XMLInspectService.findElementById(QUERY_4));
			preparedStatement.setString(1, employeeId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				employee.setEmployeeId(resultSet.getString(1));
				employee.setFullName(resultSet.getString(2));
				employee.setAddress(resultSet.getString(3));
				employee.setFacultyName(resultSet.getString(4));
				employee.setDepartment(resultSet.getString(5));
				employee.setDesignation(resultSet.getString(6));
			}
			ArrayList<Employee> employeeList = new ArrayList<Employee>();
			employeeList.add(employee);
			displayEmployees(employeeList);
		} catch (Exception exception) {
			logger.error("retrieveEmployeesById: Exception {}", exception.getMessage());
			throw exception;
		}
	}



	public void deleteEmployeeById(String employeeId) {

		try {
			preparedStatement = connection.prepareStatement(XMLInspectService.findElementById(QUERY_6));
			preparedStatement.setString(1, employeeId);
			preparedStatement.executeUpdate();
		} catch (Exception exception) {
			logger.error("deleteEmployeeById: Exception {}", exception.getMessage());
			exception.printStackTrace();
		}
	}

	public void retrieveAllEmployees() throws Exception {

		try {
			ArrayList<Employee> employeeList = new ArrayList<Employee>();
			preparedStatement = connection.prepareStatement(XMLInspectService.findElementById(QUERY_5));
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Employee employee = new Employee();
				employee.setEmployeeId(resultSet.getString(1));
				employee.setFullName(resultSet.getString(2));
				employee.setAddress(resultSet.getString(3));
				employee.setFacultyName(resultSet.getString(4));
				employee.setDepartment(resultSet.getString(5));
				employee.setDesignation(resultSet.getString(6));;
				employeeList.add(employee);
			}
			displayEmployees(employeeList);
		} catch (Exception exception) {
			logger.error("deleteEmployeeById: Exception {}", exception.getMessage());
			//throw ex;
		}

	}
	
	public void displayEmployees(ArrayList<Employee> employeesList){
		
		System.out.println("Employee ID" + "\t\t" + "Full Name" + "\t\t" + "Address" + "\t\t" + "Faculty Name" + "\t\t"
				+ "Department" + "\t\t" + "Designation" + "\n");
		System.out.println("================================================================================================================");
		for(Employee employee : employeesList){
			System.out.println(employee.getEmployeeId() + "\t" + employee.getFullName() + "\t\t"
					+ employee.getAddress() + "\t" + employee.getFacultyName() + "\t" + employee.getDepartment() + "\t"
					+ employee.getDesignation() + "\n");
			System.out.println("----------------------------------------------------------------------------------------------------------------");
		}
		
	}
}
