package com.framework.utils;

public class StudentFormatUtil {
	
	public static String getFormatUnitCode(String unitCode){
		if(unitCode.equals("TIAN")){return "天";}
		if(unitCode.equals("CI")){return "课时";}
		
		return "";
	}
	
	public static String getFormatFeeType(String feeType){
		if(feeType.equals("TIAN")){return "天";}
		if(feeType.equals("CI")){return "课时";}
		if(feeType.equals("QI")){return "期";}
		if(feeType.equals("YUE")){return "月";}
		return "";
	}
	
	public static String getFormatGrade(String grade){
		if(grade.equals("01")){return "小小班";}
		if(grade.equals("02")){return "小班";}
		if(grade.equals("03")){return "中班";}
		if(grade.equals("04")){return "大班";}
		if(grade.equals("11")){return "一年级";}
		if(grade.equals("12")){return "二年级";}
		if(grade.equals("13")){return "三年级";}
		if(grade.equals("14")){return "四年级";}
		if(grade.equals("15")){return "五年级";}
		if(grade.equals("16")){return "六年级";}
		if(grade.equals("17")){return "七年级";}
		if(grade.equals("18")){return "八年级";}
		if(grade.equals("19")){return "九年级";}
		if(grade.equals("21")){return "高一";}
		if(grade.equals("22")){return "高二";}
		if(grade.equals("23")){return "高三";}
		return "";
	}

}
