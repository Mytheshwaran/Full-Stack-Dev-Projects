package com.dhyan.user.adder;

import java.util.Scanner;

public class IOUtility
{
    static Scanner scan=new Scanner(System.in);
    public static int getInteger()
    {
        int integer=scan.nextInt();
        return integer;
    }
    public static String getString()
    {
        String string=scan.next();
        return string;
    }
}
