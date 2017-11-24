package edu.scut.software.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

public class CourseMessage {
	private Integer id;
	private Integer courseId;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date courseDate;
	@DateTimeFormat(pattern = "HH:mm:ss")
	private Date courseStartTime;
	@DateTimeFormat(pattern = "HH:mm:ss")
	private Date courseEndTime;
	private String state;
	private boolean validate;

	public static final String DONE = "已结束";
	public static final String DOING = "正在上课";
	public static final String NOTDOYET = "还未开始";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	@Temporal(TemporalType.DATE)
	public Date getCourseDate() {
		return courseDate;
	}

	public void setCourseDate(Date courseDate) {
		this.courseDate = courseDate;
	}

	@Temporal(TemporalType.TIME)
	public Date getCourseStartTime() {
		return courseStartTime;
	}

	public void setCourseStartTime(Date courseStartTime) {
		this.courseStartTime = courseStartTime;
	}

	@Temporal(TemporalType.TIME)
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

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public CourseMessage(Integer courseId, Date courseDate, Date courseStartTime, Date courseEndTime, String state,
			boolean validate) {
		super();
		this.courseId = courseId;
		this.courseDate = courseDate;
		this.courseStartTime = courseStartTime;
		this.courseEndTime = courseEndTime;
		this.state = state;
		this.validate = validate;
	}

	public CourseMessage() {
		super();
	}
}
