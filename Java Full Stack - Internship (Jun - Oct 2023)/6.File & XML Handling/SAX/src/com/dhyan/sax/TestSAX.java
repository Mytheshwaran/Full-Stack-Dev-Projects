package com.dhyan.sax;

import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
 

public class TestSAX {
	public static void main(String[] args) throws Exception{ 
		
	          File inputFile = new File("test.xml");
 
	          SAXParserFactory factory=SAXParserFactory.newInstance();
 
	          SAXParser saxParser = factory.newSAXParser();
 
	          Test1SAX Handler = new Test1SAX();
 
	          saxParser.parse(inputFile, Handler);     
	   }   
}