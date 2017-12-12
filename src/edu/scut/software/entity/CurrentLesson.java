package edu.scut.software.entity;

import java.util.Date;

public class CurrentLesson {
	public Integer id;
	public Integer courseId;
	public String courseName;
	public String teacherName;
	public Date startTime;
	public Date endTime;
	public Integer curTimes;
	public Integer totalTimes;
	public Venue venue;
	public Integer totalAttendence;
	public Boolean isAttended;

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

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getCurTimes() {
		return curTimes;
	}

	public void setCurTimes(Integer curTimes) {
		this.curTimes = curTimes;
	}

	public Integer getTotalTimes() {
		return totalTimes;
	}

	public void setTotalTimes(Integer totalTimes) {
		this.totalTimes = totalTimes;
	}


	public Venue getVenue() {
		return venue;
	}

	public void setVenue(Venue venue) {
		this.venue = venue;
	}

	public Integer getTotalAttendence() {
		return totalAttendence;
	}

	public void setTotalAttendence(Integer totalAttendence) {
		this.totalAttendence = totalAttendence;
	}

	public Boolean getIsAttended() {
		return isAttended;
	}

	public void setIsAttended(Boolean isAttended) {
		this.isAttended = isAttended;
	}

	public CurrentLesson(Integer id, Integer courseId, String courseName, String teacherName, Date startTime,
			Date endTime, Integer curTimes, Integer totalTimes, Venue venue, Integer totalAttendence,
			Boolean isAttended) {
		super();
		this.id = id;
		this.courseId = courseId;
		this.courseName = courseName;
		this.teacherName = teacherName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.curTimes = curTimes;
		this.totalTimes = totalTimes;
		this.venue = venue;
		this.totalAttendence = totalAttendence;
		this.isAttended = isAttended;
	}

	public CurrentLesson() {
		super();
	}
}
