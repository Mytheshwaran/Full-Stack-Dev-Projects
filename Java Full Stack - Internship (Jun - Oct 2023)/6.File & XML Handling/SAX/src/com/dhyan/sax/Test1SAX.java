package com.dhyan.sax;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
 
class Test1SAX extends DefaultHandler {
   boolean isName = false;
   boolean isAge= false;
 
   @Override
   public void startElement(String uri,String localName, String child, Attributes attributes)throws SAXException {
      if (child.equalsIgnoreCase("student")) 
      {
         String rollNo = attributes.getValue("id");
         System.out.println("Roll No : " + rollNo);
      } 
      else if (child.equalsIgnoreCase("name")) 
      {
    	  isName = true;
      } 
      else if (child.equalsIgnoreCase("age")) 
      {
    	  isAge = true;
      }
   }
 
   @Override
   public void endElement(String uri,String localName, String child) throws SAXException {
      if (child.equalsIgnoreCase("student")) {
         System.out.println("End Element:" + child);
      }
   }
 
   @Override
   public void characters(char ch[],int start, int length) throws SAXException {
      if (isName) 
      {
         System.out.println("Name: "+ new String(ch, start, length));
         isName = false;
      } 
      else if (isAge) 
      {
         System.out.println("Age: "+ new String(ch, start, length));
         isAge = false;
      }
   }
}
