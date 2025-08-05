package com.dhyan.dom;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DomWrite {

    public static void main(String[] args) throws ParserConfigurationException,
            TransformerException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transf = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        
        Element root=doc.createElement("Students");
        doc.appendChild(root);
        root.appendChild(createChild(doc,"1","name1","age1"));
        root.appendChild(createChild(doc,"2","name2","age2"));
        
        File myFile = new File("test.xml");
        StreamResult file = new StreamResult(myFile);

        transf.transform(source, file);
    }
    
    /**
     * Create Child Element
     * @param doc
     * @param value
     * @param name
     * @param age
     * @return
     */
    public static Element createChild(Document doc,String value,String name,String age)
    {
    	Element child=doc.createElement("Student");
    	child.setAttribute("id", value);
    	child.appendChild(createSubchild(doc,"name",name));
    	child.appendChild(createSubchild(doc,"age",age));
    	return child;
    }
    
    /**
     * Create Sub child element
     * @param doc
     * @param name
     * @param value
     * @return
     */
    public static Element createSubchild(Document doc,String name,String value)
    {
    	Element subchild=doc.createElement(name);
    	subchild.appendChild(doc.createTextNode(value));
    	return subchild;
    }
}
