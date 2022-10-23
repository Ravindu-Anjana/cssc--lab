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
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.w3c.dom.Document;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.TransformerFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;

public class XMLTransformService extends PropertyConfigs {

	private static final Logger logger = LoggerFactory.getLogger(XMLTransformService.class);

	private static final ArrayList<Map<String, String>> employeeList = new ArrayList<Map<String, String>>();

	private static Map<String, String> xpathMap = null;

	public static void transformXml() throws Exception {

		Source x = new StreamSource(new File("src/com/csse/sundayrefactoring/resources/xml/EmployeeRequest.xml"));
		Source s = new StreamSource(new File("src/com/csse/sundayrefactoring/resources/xml/Employee-modified.xsl"));
		Result o = new StreamResult(new File("src/com/csse/sundayrefactoring/resources/xml/EmployeeResponse.xml"));
		TransformerFactory.newInstance().newTransformer(s).transform(x, o);
	}

	public static ArrayList<Map<String, String>> getXmlXpath() throws Exception {

		Document d = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse("src/com/csse/sundayrefactoring/resources/xml/EmployeeResponse.xml");
		XPath x = XPathFactory.newInstance().newXPath();
		int n = Integer.parseInt((String) x.compile("count(//Employees/Employee)").evaluate(d, XPathConstants.STRING));
		for (int i = 1; i <= n; i++) {
			xpathMap = new HashMap<String, String>();
			xpathMap.put("XpathEmployeeIDKey", (String) x.compile("//Employees/Employee[" + i + "]/EmployeeID/text()")
					.evaluate(d, XPathConstants.STRING));
			xpathMap.put("XpathEmployeeNameKey", (String) x.compile("//Employees/Employee[" + i + "]/EmployeeFullName/text()")
					.evaluate(d, XPathConstants.STRING));
			xpathMap.put("XpathEmployeeAddressKey",
					(String) x.compile("//Employees/Employee[" + i + "]/EmployeeFullAddress/text()").evaluate(d,
							XPathConstants.STRING));
			xpathMap.put("XpathFacultyNameKey", (String) x.compile("//Employees/Employee[" + i + "]/FacultyName/text()")
					.evaluate(d, XPathConstants.STRING));
			xpathMap.put("XpathDepartmentKey", (String) x.compile("//Employees/Employee[" + i + "]/Department/text()")
					.evaluate(d, XPathConstants.STRING));
			xpathMap.put("XpathDesignationKey", (String) x.compile("//Employees/Employee[" + i + "]/Designation/text()")
					.evaluate(d, XPathConstants.STRING));
			employeeList.add(xpathMap);
		}
		return employeeList;
	}
}
