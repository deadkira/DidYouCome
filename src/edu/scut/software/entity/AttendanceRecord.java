package edu.scut.software.entity;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Cacheable
@Table(name = "AttendanceRecord")
@Entity
public class AttendanceRecord {
	private Integer id;
	private Integer lessonId;
	private Integer studentId;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date attendingTime;
	private boolean valid;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLessonId() {
		return lessonId;
	}

	public void setLessonId(Integer lessonId) {
		this.lessonId = lessonId;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getAttendingTime() {
		return attendingTime;
	}

	public void setAttendingTime(Date attendingTime) {
		this.attendingTime = attendingTime;
	}

	public boolean getValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public AttendanceRecord(Integer lessonId, Integer studentId, Date attendingTime, boolean valid) {
		super();
		this.lessonId = lessonId;
		this.studentId = studentId;
		this.attendingTime = attendingTime;
		this.valid = valid;
	}

	public AttendanceRecord() {
		super();
	}
}
