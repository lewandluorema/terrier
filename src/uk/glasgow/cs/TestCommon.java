package uk.glasgow.cs;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.apache.pdfbox.util.operator.Concatenate;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.SettingsDocument;

public class TestCommon {
 public static void main(String[] args) throws NumberFormatException, IOException {
	 if (args[0].toString().equals("-i")) {
		  int a;
		  System.out.println( System.getProperty("flair.home"));
		 
		 System.out.println("建立索引 ");
		  BufferedReader strin=new BufferedReader(new InputStreamReader(System.in));
//		  Scanner input=new Scanner(System.in);
//		  String cmd=input.nextLine();
//		  System.out.println(cmd);
		  a=Integer.parseInt(strin.readLine()); 
		  System.out.println("输入的数是："+a);
		 
	}else if (args[0].toString().equals("-q")) {
		 System.out.println("开始查询 ");
	}
	
}
}
