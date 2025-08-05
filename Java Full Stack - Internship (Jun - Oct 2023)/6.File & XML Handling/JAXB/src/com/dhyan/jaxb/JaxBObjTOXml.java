package com.dhyan.jaxb;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class JaxBObjTOXml {
	public static void main(String[] args) throws Exception {
		JAXBContext contextObj = JAXBContext.newInstance(Student.class);

		Marshaller marshallerObj = contextObj.createMarshaller();
		marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		FileOutputStream output=new FileOutputStream("test.xml");
		marshallerObj.marshal(create(), output);
		
	}
	public static Student create() throws FileNotFoundException, JAXBException
	{
		Student obj=new Student();
		Students emp1=new Students();
		emp1.setId(1);
		emp1.setName("name1");
		emp1.setAge("age1");
		Students emp2=new Students();
		emp2.setId(2);
		emp2.setName("name2");
		emp2.setAge("age2");
		obj.setList(Arrays.asList(emp1,emp2));
		return obj;
	}
}

