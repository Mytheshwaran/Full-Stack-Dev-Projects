package com.dhyan.jaxb;
import java.io.File;  
import javax.xml.bind.JAXBContext;  
import javax.xml.bind.JAXBException;  
import javax.xml.bind.Unmarshaller;  
  
public class JaxBRead {  
public static void main(String[] args) throws Exception{  
            File file = new File("test.xml");    
            JAXBContext jaxbContext = JAXBContext.newInstance(Student.class);    
         
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();    
            Student e=(Student) jaxbUnmarshaller.unmarshal(file);    
            System.out.println(e.getList());
}  

}
