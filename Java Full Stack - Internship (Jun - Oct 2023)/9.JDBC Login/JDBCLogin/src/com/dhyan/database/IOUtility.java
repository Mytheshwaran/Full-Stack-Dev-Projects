package com.dhyan.database;

import java.util.Scanner;

public class IOUtility {
	static Scanner scan=new Scanner(System.in);
	/**
	 * Get String Input
	 * @return
	 */
	public static String getString()
	{
		String string=scan.next();
		return string;
	}
	/** 
	 * Get Integer Input
	 * @return
	 */
	public static int getInteger()
	{
		int integer=scan.nextInt();
		return integer;
	}
}
