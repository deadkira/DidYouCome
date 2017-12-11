package edu.scut.software.entity;

public class AttendanceState {
	private Integer id;
	private String studentNumber;
	private String studentName;
	private String className;
	private String state;
	
	public static final String NOTATTENDED = "未签到";
	public static final String ATTENDED = "已签到";
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStudentNumber() {
		return studentNumber;
	}
	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public AttendanceState(Integer id, String studentNumber, String studentName, String className, String state) {
		super();
		this.id = id;
		this.studentNumber = studentNumber;
		this.studentName = studentName;
		this.className = className;
		this.state = state;
	}
	public AttendanceState() {
		super();
	}
}
