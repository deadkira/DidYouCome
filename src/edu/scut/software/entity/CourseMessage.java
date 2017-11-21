package edu.scut.software.entity;

import java.util.Date;

public class CourseMessage {
	private Integer id;
	private Date courseDate;
	private Date courseStartTime;
	private Date courseEndTime;
	private String state;

	public static final String DONE = "�ѽ���";
	public static final String DOING = "�����Ͽ�";
	public static final String NOTDOYET = "��δ��ʼ";

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCourseDate() {
		return courseDate;
	}

	public void setCourseDate(Date courseDate) {
		this.courseDate = courseDate;
	}

	public Date getCourseStartTime() {
		return courseStartTime;
	}

	public void setCourseStartTime(Date courseStartTime) {
		this.courseStartTime = courseStartTime;
	}

	public Date getCourseEndTime() {
		return courseEndTime;
	}

	public void setCourseEndTime(Date courseEndTime) {
		this.courseEndTime = courseEndTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public CourseMessage(Integer id, Date courseDate, Date courseStartTime, Date courseEndTime, String state) {
		super();
		this.id = id;
		this.courseDate = courseDate;
		this.courseStartTime = courseStartTime;
		this.courseEndTime = courseEndTime;
		this.state = state;
	}

	public CourseMessage() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
