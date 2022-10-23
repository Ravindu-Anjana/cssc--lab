package com.csse.sundayrefactoring.service;

import javax.xml.xpath.XPathFactory;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;

import com.csse.sundayrefactoring.config.PropertyConfigs;
import org.w3c.dom.Document;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.TransformerFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;

public class XMLTransformService extends PropertyConfigs {

	private static final ArrayList<Map<String, String>> employeeList = new ArrayList<Map<String, String>>();

	private static final String REQUEST_FILE_PATH = "src/com/csse/sundayrefactoring/resources/xml/EmployeeRequest.xml";
	private static final String MODIFY_FILE_PATH = "src/com/csse/sundayrefactoring/resources/xml/Employee-modified.xsl";
	private static final String RESPONSE_FILE_PATH = "src/com/csse/sundayrefactoring/resources/xml/EmployeeResponse.xml";
	private static final String XPATH_EMPLOYEE_ID_KEY="XpathEmployeeIDKey";
	private static final String XPATH_EMPLOYEE_NAME_KEY="XpathEmployeeNameKey";
	private static final String XPATH_EMPLOYEE_ADDRESS_KEY="XpathEmployeeAddressKey";
	private static final String XPATH_FACULTY_NAME_KEY="XpathFacultyNameKey";
	private static final String XPATH_DEPARTMENT_KEY="XpathDepartmentKey";
	private static final String XPATH_DESIGNATION_KEY="XpathDesignationKey";

	private static Map<String, String> xpathMap = null;

	public static void transformXml() throws Exception {

		Source sourceRequest = new StreamSource(new File(REQUEST_FILE_PATH));
		Source sourceModify = new StreamSource(new File(MODIFY_FILE_PATH));
		Result sourceResponse = new StreamResult(new File(RESPONSE_FILE_PATH));
		TransformerFactory.newInstance().newTransformer(sourceModify).transform(sourceRequest, sourceResponse);
	}


	public static ArrayList<Map<String, String>> getXmlXpath() throws Exception {

		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(RESPONSE_FILE_PATH);
		XPath xPath = XPathFactory.newInstance().newXPath();
		int n = Integer.parseInt((String) xPath.compile("count(//Employees/Employee)").evaluate(document, XPathConstants.STRING));//n variable suitable name
		for (int i = 1; i <= n; i++) {
			xpathMap = new HashMap<String, String>();
			xpathMap.put(XPATH_EMPLOYEE_ID_KEY, (String) xPath.compile("//Employees/Employee[" + i + "]/EmployeeID/text()")
					.evaluate(document, XPathConstants.STRING));
			xpathMap.put(XPATH_EMPLOYEE_NAME_KEY, (String) xPath.compile("//Employees/Employee[" + i + "]/EmployeeFullName/text()")
					.evaluate(document, XPathConstants.STRING));
			xpathMap.put(XPATH_EMPLOYEE_ADDRESS_KEY,
					(String) xPath.compile("//Employees/Employee[" + i + "]/EmployeeFullAddress/text()").evaluate(document,
							XPathConstants.STRING));
			xpathMap.put(XPATH_FACULTY_NAME_KEY, (String) xPath.compile("//Employees/Employee[" + i + "]/FacultyName/text()")
					.evaluate(document, XPathConstants.STRING));
			xpathMap.put(XPATH_DEPARTMENT_KEY, (String) xPath.compile("//Employees/Employee[" + i + "]/Department/text()")
					.evaluate(document, XPathConstants.STRING));
			xpathMap.put(XPATH_DESIGNATION_KEY, (String) xPath.compile("//Employees/Employee[" + i + "]/Designation/text()")
					.evaluate(document, XPathConstants.STRING));
			employeeList.add(xpathMap);
		}
		return employeeList;
	}
}
