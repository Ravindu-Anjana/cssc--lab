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
	public EmployeeService() throws SQLException, ClassNotFoundException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("username"),
					properties.getProperty("password"));
		} catch (SQLException | ClassNotFoundException ex) {
			logger.error("EmployeeService: Exception {}", ex);
//			throw ex;
		} 
	}

	public void parseXmlToDto() throws Exception {

		try {
			int size = XMLTransformService.getXmlXpath().size();
			for (int i = 0; i < size; i++) {
				Map<String, String> l = XMLTransformService.getXmlXpath().get(i);
				Employee employee = new Employee();
				employee.setEmployeeId(l.get("XpathEmployeeIDKey"));
				employee.setFullName(l.get("XpathEmployeeNameKey"));
				employee.setAddress(l.get("XpathEmployeeAddressKey"));
				employee.setFacultyName(l.get("XpathFacultyNameKey"));
				employee.setDepartment(l.get("XpathDepartmentKey"));
				employee.setDesignation(l.get("XpathDesignationKey"));
				employeesList.add(employee);
				System.out.println(employee.toString() + "\n");
			}
		} catch (Exception ex) {
			logger.error("parseXmlToDto : Exception {}", ex.getMessage());
			throw ex;
		}
	}

	public void executeSqlQuery() throws Exception {
		try {
			statement = connection.createStatement();
			statement.executeUpdate(XMLInspectService.findElementById("q2"));
			statement.executeUpdate(XMLInspectService.findElementById("q1"));

		} catch (SQLException ex) {
			logger.error("executeSqlQuery: SQLException {}", ex.getMessage());
			//throw ex;
		} catch (Exception ex) {
			logger.error("executeSqlQuery: SQLException {}", ex.getMessage());
			//throw ex;
		}
	}

	public void insertAllEmployees() throws Exception {
		try {
			preparedStatement = connection.prepareStatement(XMLInspectService.findElementById("q3"));
			connection.setAutoCommit(false);
			for(int i = 0; i < employeesList.size(); i++){
				Employee e = employeesList.get(i);
				preparedStatement.setString(1, e.getEmployeeId());
				preparedStatement.setString(2, e.getFullName());
				preparedStatement.setString(3, e.getAddress());
				preparedStatement.setString(4, e.getFacultyName());
				preparedStatement.setString(5, e.getDepartment());
				preparedStatement.setString(6, e.getDesignation());
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
			connection.commit();
		} catch (Exception ex) {
			logger.error("insertAllEmployees: SQLException {}", ex.getMessage());
			//throw ex;
		}
	}

	public void retrieveEmployeesById(String eid) throws Exception {

		Employee employee = new Employee();
		try {
			preparedStatement = connection.prepareStatement(XMLInspectService.findElementById("q4"));
			preparedStatement.setString(1, eid);
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
		} catch (Exception ex) {
			logger.error("retrieveEmployeesById: Exception {}", ex.getMessage());
			throw ex;
		}
	}

	public void deleteEmployeeById(String eid) {

		try {
			preparedStatement = connection.prepareStatement(XMLInspectService.findElementById("q6"));
			preparedStatement.setString(1, eid);
			preparedStatement.executeUpdate();
		} catch (Exception ex) {
			logger.error("deleteEmployeeById: Exception {}", ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void retrieveAllEmployees() throws Exception {

		try {
			ArrayList<Employee> employeeList = new ArrayList<Employee>();
			preparedStatement = connection.prepareStatement(XMLInspectService.findElementById("q5"));
			ResultSet r = preparedStatement.executeQuery();
			while (r.next()) {
				Employee e = new Employee();
				e.setEmployeeId(r.getString(1));
				e.setFullName(r.getString(2));
				e.setAddress(r.getString(3));
				e.setFacultyName(r.getString(4));
				e.setDepartment(r.getString(5));
				e.setDesignation(r.getString(6));
				employeeList.add(e);
			}
			displayEmployees(employeeList);
		} catch (Exception ex) {
			logger.error("deleteEmployeeById: Exception {}", ex.getMessage());
			//throw ex;
		}

	}
	
	public void displayEmployees(ArrayList<Employee> employeesList){
		
		System.out.println("Employee ID" + "\t\t" + "Full Name" + "\t\t" + "Address" + "\t\t" + "Faculty Name" + "\t\t"
				+ "Department" + "\t\t" + "Designation" + "\n");
		System.out.println("================================================================================================================");
		for(int i = 0; i < employeesList.size(); i++){
			Employee e = employeesList.get(i);
			System.out.println(e.getEmployeeId() + "\t" + e.getFullName() + "\t\t"
					+ e.getAddress() + "\t" + e.getFacultyName() + "\t" + e.getDepartment() + "\t"
					+ e.getDesignation() + "\n");
			System.out.println("----------------------------------------------------------------------------------------------------------------");
		}
		
	}
}
