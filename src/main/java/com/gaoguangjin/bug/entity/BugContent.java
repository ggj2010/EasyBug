package com.gaoguangjin.bug.entity;

import com.framework.base.BaseEntity;
import com.gaoguangjin.project.entity.Project;
import com.gaoguangjin.project.entity.ProjectModule;
import com.gaoguangjin.project.entity.ProjectUser;
import com.gaoguangjin.project.entity.ProjectVersion;

public class BugContent extends BaseEntity {
	
	// ��ɫ�� ����Ա�����Ծ�������Ա��������Ա
	
	// ����Ա������¼��BUG,��Ȼ����ԱҲ����¼��Bug�� bug��¼���ʱ���ת���� **����Ա���� QAȷ���Ƿ���
	// ���Ծ��������new BUG-->{isOpen=Y;
	// { repeate �ظ��� �ر�
	// {invalid ��Ч�� �ر�
	// ����Ա��Bug���У������ת���� �����״̬Ϊ״̬��opend-->���resolved
	// Ȼ���������֤һ�Σ�û�����ˣ��Ͱ�resolved���fixed��������������ת����ȥ������ת�����ˡ�
	
	// �������Bug������˭����� ���Ը������ڲ������һ�ν���Bug����
	
	public static String STATUS_NEW = "new";
	
	public static String STATUS_INVALID = "invalid";
	public static String STATUS_REPEATE = "repeate";
	public static String STATUS_OPEN = "open";
	public static String STATUS_LATER = "later";
	public static String STATUS_WORKSFORME = "worksForMe";
	
	// �Ѿ����
	public static String STATUS_FIXED = "fixed";
	// �رյġ�
	public static String STATUS_CLOSED = "closed";
	// ����˵ȴ����Թ���ʦ��֤����ȷ�Ͻ��
	public static String STATUS_SOLVED = "solved";
	// ���½��
	public static String STATUS_RESOLVED = "resolved";
	// ����һЩ״̬ͳ�Ƶ����
	// bug��open ��
	// 1.�Ѿ������bug status=fixed and isOpend=Y
	// 2.û�н����bug status=opend and isOpend=Y
	// 3.�Ƴٵ�bug status=later and isOpend=Y
	// 4.�޷������ֳ�bug status=worksForMe and isOpend=Y
	// 5.�Ѿ�������ȴ���֤�� status=
	
	// bugû��open
	// 1.�ظ���bug status=repeate and isOpend=N
	// 2.��Ч��bug status=invalid and isOpend=N
	// ���´򿪵�bug һЩ״̬
	
	// �汾
	private ProjectVersion projectVersion;
	// ģ��
	private ProjectModule projectModule;
	
	private ProjectUser userHandler;
	// bug��˭ȥ��˷��䡣
	// ��˷����Ȩ��ֻ�в����˲��С�
	private ProjectUser userAssigner;
	
	// ���ȼ�
	private String level;
	// ��Ŀ
	private Project project;
	// ����
	private String describe;
	// ����status��һЩ״̬
	private String status;
	
	// Open����ȷ��
	private String isOpen;
	// Open����ȷ��
	private String isReopen;
	// �Ƿ�ر�
	private String isClosed;
	// �Ƿ���
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
