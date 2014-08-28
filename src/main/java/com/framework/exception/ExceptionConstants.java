package com.framework.exception;


/**
 * 异常代码常量类
 * @author duruxin
 */
public class ExceptionConstants {
	

	//==全局异常
	
	//未找到相应的数据
	public static final String NOT_FOUND_DATA="NOT_FOUND_DATA";
	public static final String PARAM_ERROR="PARAM_ERROR";
	public static final String IS_EXIST="IS_EXIST";
	public static final String BIORGAN_PRESTR_IS_EXIST="BIORGAN_PRESTR_IS_EXIST";
	
	public static final String STUENROLL_ALREADY_OWNFEE="STUENROLL_ALREADY_OWNFEE";
	
	public static final String STUENROLL_HAPPEND="STUENROLL_HAPPEND";
	
	public static final String STUEARNEST_STU_IS_IN_CLASS="STU_IS_IN_CLASS";//学生在班,不能交定金
	public static final String STUEARNEST_IS_USED="STUEARNEST_IS_USED";//定金已经被用
	public static final String STUEARNEST_IS_NOT_DEFAULT="STUEARNEST_IS_NOT_DEFAULT";//定金已经作废或者退费
	
	public static final String STUENROLL_IS_NOT_DEFAULT="STUENROLL_IS_NOT_DEFAULT";//报名业务已经作废或者退费
	
	public static final String TPC_STU_ATTEND_ENROLL_ISUSED="TPC_STU_ATTEND_ENROLL_ISUSED";//考勤修改时，如果扣费已经结束，不能修改考勤。
	
	
}
