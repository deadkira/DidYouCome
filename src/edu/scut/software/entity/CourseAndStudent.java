package edu.scut.software.entity;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Cacheable
@Table(name = "CourseAndStudent")
@Entity
public class CourseAndStudent {
	private Integer id;
	private Integer courseId;
	private Integer studentId;
	private boolean valid;

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

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public boolean getValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public CourseAndStudent(Integer courseId, Integer studentId, boolean valid) {
		super();
		this.courseId = courseId;
		this.studentId = studentId;
		this.valid = valid;
	}

	public CourseAndStudent() {
		super();
		// TODO Auto-generated constructor stub
	}

}
