package com.dhyan.dom;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public class DomRead {

	public static void main(String argv[]) throws SAXException, IOException, ParserConfigurationException {

		File xmlFile = new File("test.xml");

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = factory.newDocumentBuilder();
		Document doc = dBuilder.parse(xmlFile);

		doc.getDocumentElement().normalize();

		System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

		NodeList nList = doc.getElementsByTagName("Student");

		for (int i = 0; i < nList.getLength(); i++) {

			Node nNode = nList.item(i);

			System.out.println("\n Current Element: " + nNode.getNodeName());

			Element elem = (Element) nNode;

			String uid = elem.getAttribute("id");

			Node node1 = elem.getElementsByTagName("name").item(0);
			String fname = node1.getTextContent();

			Node node2 = elem.getElementsByTagName("age").item(0);
			String age = node2.getTextContent();

			System.out.printf("User id: %s%n", uid);
			System.out.printf("Name: %s%n", fname);
			System.out.printf("Age: %s%n", age);
		}
	}
}
