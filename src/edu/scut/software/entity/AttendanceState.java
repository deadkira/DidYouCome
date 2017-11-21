package edu.scut.software.entity;

public class AttendanceState {
	private Integer id;
	private String studentNumber;
	private String className;
	private String state;
	
	public static final String NOTATTENDED = "δǩ��";
	public static final String ATTENDED = "��ǩ��";
	public static final String LATE = "�ٵ�";
	
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

	public AttendanceState(Integer id, String studentNumber, String className, String state) {
		super();
		this.id = id;
		this.studentNumber = studentNumber;
		this.className = className;
		this.state = state;
	}

	 public AttendanceState() {
		super();
	}
	
}