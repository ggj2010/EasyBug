package com.framework.utils;

public class StudentFormatUtil {
	
	public static String getFormatUnitCode(String unitCode){
		if(unitCode.equals("TIAN")){return "��";}
		if(unitCode.equals("CI")){return "��ʱ";}
		
		return "";
	}
	
	public static String getFormatFeeType(String feeType){
		if(feeType.equals("TIAN")){return "��";}
		if(feeType.equals("CI")){return "��ʱ";}
		if(feeType.equals("QI")){return "��";}
		if(feeType.equals("YUE")){return "��";}
		return "";
	}
	
	public static String getFormatGrade(String grade){
		if(grade.equals("01")){return "СС��";}
		if(grade.equals("02")){return "С��";}
		if(grade.equals("03")){return "�а�";}
		if(grade.equals("04")){return "���";}
		if(grade.equals("11")){return "һ�꼶";}
		if(grade.equals("12")){return "���꼶";}
		if(grade.equals("13")){return "���꼶";}
		if(grade.equals("14")){return "���꼶";}
		if(grade.equals("15")){return "���꼶";}
		if(grade.equals("16")){return "���꼶";}
		if(grade.equals("17")){return "���꼶";}
		if(grade.equals("18")){return "���꼶";}
		if(grade.equals("19")){return "���꼶";}
		if(grade.equals("21")){return "��һ";}
		if(grade.equals("22")){return "�߶�";}
		if(grade.equals("23")){return "����";}
		return "";
	}

}
