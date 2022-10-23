package com.csse.sundayrefactoring.service;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import com.csse.sundayrefactoring.config.PropertyConfigs;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class XMLInspectService extends PropertyConfigs {
	
	public static String findElementById(String id) throws Exception {
		NodeList nodes; Element element = null;
		nodes = DocumentBuilderFactory.newInstance().newDocumentBuilder()
				.parse(new File("src/com/csse/sundayrefactoring/resources/xml/EmployeeQuery.xml"))
				.getElementsByTagName("query");
		for (int x = 0; x < nodes.getLength(); x++) {
			element = (Element) nodes.item(x);
			if (element.getAttribute("id").equals(id))
				break;
		}
		return element.getTextContent().trim();
	}
}
