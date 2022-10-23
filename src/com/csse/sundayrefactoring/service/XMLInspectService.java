package com.csse.sundayrefactoring.service;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import com.csse.sundayrefactoring.config.PropertyConfigs;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class XMLInspectService extends PropertyConfigs {

	private static final Logger logger = LoggerFactory.getLogger(XMLInspectService.class);
	
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
