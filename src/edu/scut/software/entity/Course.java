package edu.scut.software.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

import edu.scut.software.tool.Helper;

@Cacheable
@Table(name = "course")
@Entity
public class Course {
	private Integer id;
	private String name;
	private Integer teacherId;
	private Integer venueId;
	private Integer year;
	private Integer term;
	private Integer number;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDate;
	@DateTimeFormat(pattern = "HH:mm:ss")
	private Date startTime;
	@DateTimeFormat(pattern = "HH:mm:ss")
	private Date endTime;
	private Integer whatDay;
	private String status;
	private boolean valid;

	public static final Date FIRST_LESSON_BEGIN_TIME = Helper.pareseString("HH:mm", "8:50");
	public static final Date FIRST_LESSON_END_TIME = Helper.pareseString("HH:mm", "9:35");
	public static final Date SECOND_LESSON_BEGIN_TIME = Helper.pareseString("HH:mm", "9:40");
	public static final Date SECOND_LESSON_END_TIME = Helper.pareseString("HH:mm", "10:25");
	public static final Date THIRD_LESSON_BEGIN_TIME = Helper.pareseString("HH:mm", "10:40");
	public static final Date THIRD_LESSON_END_TIME = Helper.pareseString("HH:mm", "11:25");;
	public static final Date FOURTH_LESSON_BEGIN_TIME = Helper.pareseString("HH:mm", "11:30");
	public static final Date FOURTH_LESSON_END_TIME = Helper.pareseString("HH:mm", "12:15");
	public static final Date FIFTH_LESSON_BEGIN_TIME = Helper.pareseString("HH:mm", "14:00");
	public static final Date FIFTH_LESSON_END_TIME = Helper.pareseString("HH:mm", "14:45");
	public static final Date SISTH_LESSON_BEGIN_TIME = Helper.pareseString("HH:mm", "14:50");
	public static final Date SISTH_LESSON_END_TIME = Helper.pareseString("HH:mm", "15:35");
	public static final Date SEVENTH_LESSON_BEGIN_TIME = Helper.pareseString("HH:mm", "15:50");
	public static final Date SEVENTH_LESSON_END_TIME = Helper.pareseString("HH:mm", "16:35");
	public static final Date EIGHTH_LESSON_BEGIN_TIME = Helper.pareseString("HH:mm", "16:40");
	public static final Date EIGHTH_LESSON_END_TIME = Helper.pareseString("HH:mm", "17:25");
	public static final Date NINTH_LESSON_BEGIN_TIME = Helper.pareseString("HH:mm", "19:00");
	public static final Date NINTH_LESSON_END_TIME = Helper.pareseString("HH:mm", "19:45");
	public static final Date TENTH_LESSON_BEGIN_TIME = Helper.pareseString("HH:mm", "19:50");
	public static final Date TENTH_LESSON_END_TIME = Helper.pareseString("HH:mm", "20:35");
	public static final Date ELEVENTH_LESSON_BEGIN_TIME = Helper.pareseString("HH:mm", "20:50");
	public static final Date ELEVENTH_LESSON_END_TIME = Helper.pareseString("HH:mm", "21:35");;

	public static final Integer SUNDAY = 0;
	public static final Integer MONDY = 1;
	public static final Integer TUESDAY = 2;
	public static final Integer WEDNESDAY = 3;
	public static final Integer THURSDAY = 4;
	public static final Integer FRIDAY = 5;
	public static final Integer SATURDAY = 6;

	public static final String NOTBEGINNING = "未开始";
	public static final String NOTENDING = "未结束";
	public static final String ENDED = "已经结束";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public Integer getVenueId() {
		return venueId;
	}

	public void setVenueId(Integer venueId) {
		this.venueId = venueId;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@Temporal(TemporalType.DATE)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.DATE)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Temporal(TemporalType.TIME)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Temporal(TemporalType.TIME)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getWhatDay() {
		return whatDay;
	}

	public void setWhatDay(Integer whatDay) {
		this.whatDay = whatDay;
	}

	public boolean getValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Course(String name, Integer teacherId, Integer venueId, Integer year, Integer term, Integer number,
			Date startDate, Date endDate, Date startTime, Date endTime, Integer whatDay, String status, boolean valid) {
		super();
		this.name = name;
		this.teacherId = teacherId;
		this.venueId = venueId;
		this.year = year;
		this.term = term;
		this.number = number;
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.whatDay = whatDay;
		this.status = status;
		this.valid = valid;
	}

	public Course() {
		super();
	}
}
