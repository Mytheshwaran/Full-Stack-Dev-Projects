package com.dhyan.xslt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Xslt {
	public static void main(String[] args)
			throws ParserConfigurationException, SAXException, IOException, TransformerException {
		File file=new File("test.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer(new StreamSource("test.xslt"));
        DOMSource source = new DOMSource(doc);

		File newfile = new File("test.html");
        FileOutputStream output=new FileOutputStream(newfile);
        transformer.transform(source,new StreamResult(output));
        
        StreamResult console=new StreamResult(System.out);
        transformer.transform(source, console);
	}
}
