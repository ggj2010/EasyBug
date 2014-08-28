package com.framework.base;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class BackUps {
	
	public static void main(String[] args) {
		// 得配置好环境变量D:\MySql\MySQL Server 5.6\bin
		// backup();
		recover();
	}
	
	public static void backup() {
		try {
			Runtime.getRuntime().exec(
					"cmd /c mysqldump -hlocalhost -uroot -proot --default-character-set=utf8 test>e:\\bk.sql");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void recover() {
		try {
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec("mysql -hlocalhost -uroot -proot --default-character-set=utf8 test");
			OutputStream outputStream = process.getOutputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("e:\\bk.sql"), "utf8"));
			String str = null;
			StringBuffer sb = new StringBuffer();
			while ((str = br.readLine()) != null) {
				sb.append(str + "\r\n");
			}
			str = sb.toString();
			System.out.println(str);
			OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf8");
			writer.write(str);
			writer.flush();
			outputStream.close();
			br.close();
			writer.close();
			System.out.println("恢复成功!");
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}
}
