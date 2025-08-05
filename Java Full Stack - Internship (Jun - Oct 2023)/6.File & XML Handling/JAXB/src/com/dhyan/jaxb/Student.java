package com.dhyan.jaxb;

import java.util.*;
import javax.xml.bind.annotation.XmlRootElement;   

@XmlRootElement
public class Student {
	private List<Students> student=new ArrayList<Students>();
	public void setList(List<Students> student)
	{
		this.student=student;
	}
	public List<Students> getList()
	{
		return student;
	}
}
