package com.gaoguangjin.bug.entity;

import com.framework.base.BaseEntity;
import com.gaoguangjin.project.entity.Project;
import com.gaoguangjin.project.entity.ProjectModule;
import com.gaoguangjin.project.entity.ProjectUser;
import com.gaoguangjin.project.entity.ProjectVersion;

public class BugContent extends BaseEntity {
	
	// 角色有 管理员，测试经理，测试员工，程序员
	
	// 测试员工负责录入BUG,当然程序员也可以录入Bug。 bug在录入的时候就转发给 **程序员处理。 QA确认是否开启
	// 测试经理负责审核new BUG-->{isOpen=Y;
	// { repeate 重复的 关闭
	// {invalid 无效的 关闭
	// 程序员对Bug具有，解决和转发。 解决了状态为状态由opend-->变成resolved
	// 然后测试再验证一次，没问题了，就把resolved变成fixed，如果有问题继续转发回去。或者转给他人。
	
	// 关于这个Bug最终是谁解决的 可以根据日期查找最后一次接收Bug的人
	
	public static String STATUS_NEW = "new";
	
	public static String STATUS_INVALID = "invalid";
	public static String STATUS_REPEATE = "repeate";
	public static String STATUS_OPEN = "open";
	public static String STATUS_LATER = "later";
	public static String STATUS_WORKSFORME = "worksForMe";
	
	// 已经解决
	public static String STATUS_FIXED = "fixed";
	// 关闭的。
	public static String STATUS_CLOSED = "closed";
	// 解决了等待测试工程师验证。代确认解决
	public static String STATUS_SOLVED = "solved";
	// 重新解决
	public static String STATUS_RESOLVED = "resolved";
	// 关于一些状态统计的情况
	// bug都open 过
	// 1.已经解决的bug status=fixed and isOpend=Y
	// 2.没有解决的bug status=opend and isOpend=Y
	// 3.推迟的bug status=later and isOpend=Y
	// 4.无法重新现成bug status=worksForMe and isOpend=Y
	// 5.已经被解决等待验证的 status=
	
	// bug没有open
	// 1.重复的bug status=repeate and isOpend=N
	// 2.无效的bug status=invalid and isOpend=N
	// 重新打开的bug 一些状态
	
	// 版本
	private ProjectVersion projectVersion;
	// 模块
	private ProjectModule projectModule;
	
	private ProjectUser userHandler;
	// bug让谁去审核分配。
	// 审核分配的权限只有部分人才有。
	private ProjectUser userAssigner;
	
	// 优先级
	private String level;
	// 项目
	private Project project;
	// 描述
	private String describe;
	// 关于status的一些状态
	private String status;
	
	// Open就是确认
	private String isOpen;
	// Open就是确认
	private String isReopen;
	// 是否关闭
	private String isClosed;
	// 是否解决
	private String isSolved;
	private String month;
	
	public String getMonth() {
		return month;
	}
	
	public void setMonth(String month) {
		this.month = month;
	}
	
	public ProjectVersion getProjectVersion() {
		return projectVersion;
	}
	
	public void setProjectVersion(ProjectVersion projectVersion) {
		this.projectVersion = projectVersion;
	}
	
	public ProjectModule getProjectModule() {
		return projectModule;
	}
	
	public void setProjectModule(ProjectModule projectModule) {
		this.projectModule = projectModule;
	}
	
	public ProjectUser getUserHandler() {
		return userHandler;
	}
	
	public void setUserHandler(ProjectUser userHandler) {
		this.userHandler = userHandler;
	}
	
	public ProjectUser getUserAssigner() {
		return userAssigner;
	}
	
	public void setUserAssigner(ProjectUser userAssigner) {
		this.userAssigner = userAssigner;
	}
	
	public String getLevel() {
		return level;
	}
	
	public void setLevel(String level) {
		this.level = level;
	}
	
	public Project getProject() {
		return project;
	}
	
	public void setProject(Project project) {
		this.project = project;
	}
	
	public String getDescribe() {
		return describe;
	}
	
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getIsOpen() {
		return isOpen;
	}
	
	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}
	
	public String getIsReopen() {
		return isReopen;
	}
	
	public void setIsReopen(String isReopen) {
		this.isReopen = isReopen;
	}
	
	public String getIsClosed() {
		return isClosed;
	}
	
	public void setIsClosed(String isClosed) {
		this.isClosed = isClosed;
	}
	
	public String getIsSolved() {
		return isSolved;
	}
	
	public void setIsSolved(String isSolved) {
		this.isSolved = isSolved;
	}
	
}
