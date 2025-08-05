package com.dhyan.taxi;

import java.util.Scanner;

public class IOUtility {
	static Scanner scan;
	
	/**
	 * Getting Integer Input
	 * @return
	 */
	public static int getInteger()
	{
		scan=new Scanner(System.in);
		int integer=scan.nextInt();
		return integer;
	}
	
	/**
	 * Getting String Input
	 * @return
	 */
	public static String getString()
	{
		scan=new Scanner(System.in);
		String string=scan.nextLine();
		return string;
	}
}
