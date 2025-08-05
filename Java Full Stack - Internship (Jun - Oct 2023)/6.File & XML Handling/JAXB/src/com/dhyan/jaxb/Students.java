package com.dhyan.jaxb;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Students extends Student{
	private int id;
	private String name;
	private String age;

	@XmlAttribute
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	@Override
	public String toString()
	{
		return name+" "+age;
	}
}
