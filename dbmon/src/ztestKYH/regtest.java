package ztestKYH;

import java.io.FileInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regtest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String oraHomeAdd = "C:\\app\\kyh\\product\\11.2.0\\dbhome_1\\NETWORK\\ADMIN\\";
		StringBuffer str = new StringBuffer();
		
		try{
			FileInputStream fis = new FileInputStream(oraHomeAdd+"tnsnames.ora");

			int data = 0;
		
			while((data=fis.read()) != -1){
				char c = (char)data;
				str.append(c);
			}
		
			fis.close();
		}catch(Exception cnfe){
			cnfe.printStackTrace();
		}
		
		String pattern = "(\\w+)(\\s*=\\s*\r\n)"+
		                 "(\\s*[(]\\s*DESCRIPTION\\s*=\\s*\r\n)"+
				         "(\\s*[(]\\s*ADDRESS\\s*=\\s*[(]\\s*PROTOCOL\\s*=\\s*\\w+\\s*[)])"+
		                 "(\\s*[(]\\w+\\s*=\\s*)([\\w|\\.]+)(\\s*[)]\\s*[(]\\s*PORT\\s*=\\s*)(\\w+)(\\s*[)]\\s*[)]\\s*\r\n)"+
						 "(\\s*[(]\\s*CONNECT_DATA\\s*=\\s*\r\n)"+
		                 "(\\s*[(]\\s*SERVER\\s*=\\s*DEDICATED\\s*[)]\\s*\r\n)"+
						 "(\\s*[(]\\s*SERVICE_NAME\\s*=\\s*)(\\w+)([)]\\s*\r\n)"+
		                 "(\\s*[)]\r\n)(\\s*[)]\r\n)";
		Pattern pt = Pattern.compile(pattern);
		Matcher mc = pt.matcher(str);
		
		while(mc.find()){
			System.out.println(mc.group());
			System.out.println(mc.group(1));
			System.out.println(mc.group(6));
			System.out.println(mc.group(8));
			System.out.println(mc.group(13));
		
		}
	}
}
