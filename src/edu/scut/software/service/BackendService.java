package edu.scut.software.service;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.scut.software.entity.AttendanceRecord;
import edu.scut.software.entity.AttendanceState;
import edu.scut.software.entity.ClassOfStudent;
import edu.scut.software.entity.Course;
import edu.scut.software.entity.CourseAndStudent;
import edu.scut.software.entity.CourseMessage;
import edu.scut.software.entity.User;
import edu.scut.software.repository.AttendanceRecord_Repository;
import edu.scut.software.repository.ClassOfStudent_Repository;
import edu.scut.software.repository.CourseAndStudent_Repository;
import edu.scut.software.repository.Course_Repository;
import edu.scut.software.repository.User_Repository;
import edu.scut.software.tool.Helper;

@Service
public class BackendService {
	@Autowired
	AttendanceRecord_Repository attendanceRecord_Repository;
	@Autowired
	ClassOfStudent_Repository classOfStudent_Repository;
	@Autowired
	Course_Repository course_Repository;
	@Autowired
	CourseAndStudent_Repository courseAndStudent_Repository;
	@Autowired
	User_Repository user_Repository;

	@Transactional(readOnly = true)
	public User getUser(String username, String password) {
		return user_Repository.getByUsernameAndPassword(username.trim().toLowerCase(), password);
	}

	@Transactional
	public void saveUser(User user) {
		user_Repository.saveAndFlush(user);
	}

	public Course[] getCoursesByCondition(Integer year, Integer term, String name, User user) {
		Specification<Course> specification = new Specification<Course>() {
			public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (year != -1)
					predicates.add(cb.equal(root.get("year"), year));
				if (term != -1)
					predicates.add(cb.equal(root.get("term"), term));
				if (!name.trim().equals(""))
					predicates.add(cb.equal(root.get("name"), name.trim()));
				if (user != null)
					predicates.add(cb.equal(root.get("teacherId"), user.getId()));
				query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
				return query.getRestriction();
			}
		};
		List<Course> result = course_Repository.findAll(specification);
		return result.toArray(new Course[result.size()]);
	}

	public Course getCourse(Integer id) {
		return course_Repository.getById(id);
	}

	public CourseMessage[] getCourseMessage(Integer id) {
		Course course = getCourse(id);
		Integer numberOfCourse = course.getNumber();
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(course.getStartDate());
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(course.getEndDate());
		endCalendar.add(Calendar.DAY_OF_WEEK, 1);
		CourseMessage[] courseMessages = new CourseMessage[numberOfCourse];
		for (int i = 0; i < numberOfCourse && startCalendar.compareTo(endCalendar) < 0; i++) {
			courseMessages[i] = new CourseMessage();
			courseMessages[i].setId(course.getId());
			Integer whatDay = startCalendar.get(Calendar.DAY_OF_WEEK) - 1;
			while (!whatDay.equals(course.getWhatDay())) {
				startCalendar.add(Calendar.DAY_OF_WEEK, 1);
			}
			courseMessages[i].setCourseDate(startCalendar.getTime());
			courseMessages[i].setCourseStartTime(course.getStartTime());
			courseMessages[i].setCourseEndTime(course.getEndTime());
			Date current = new Date();
			Date startTime = Helper.combineDateWithTime(startCalendar.getTime(), course.getStartTime());
			Date endTime = Helper.combineDateWithTime(startCalendar.getTime(), course.getEndTime());
			if (current.compareTo(startTime) < 0)
				courseMessages[i].setState(CourseMessage.NOTDOYET);
			else if (current.compareTo(endTime) < 0)
				courseMessages[i].setState(CourseMessage.DOING);
			else
				courseMessages[i].setState(CourseMessage.DONE);
		}
		return courseMessages;
	}

	public AttendanceState[] getAttendanceState(Integer courseId, Date courseDate) {
		Course course = getCourse(courseId);
		Date attendingTime = courseDate;
		Calendar c = Calendar.getInstance();
		c.setTime(attendingTime);
		c.add(Calendar.DAY_OF_WEEK, 1);
		Date endOfDay = c.getTime();
		List<User> users = getStudents(courseId);
		AttendanceState[] attendanceStates = new AttendanceState[users.size()];
		AttendanceRecord record;

		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			ClassOfStudent classOfStudent = getClassOfStudent(user.getClassOfStudent());
			record = attendanceRecord_Repository.getRecordsBy(courseId, user.getId(), courseDate, endOfDay);
			String state = null;
			Date startTime = Helper.combineDateWithTime(courseDate, course.getStartTime());
			Date endTime = Helper.combineDateWithTime(courseDate, course.getEndTime());
			if (record != null) {
				Date attendedTime = record.getAttendingTime();
				if (attendedTime.compareTo(startTime) < 0) {
					state = AttendanceState.ATTENDED;
				} else if (attendedTime.compareTo(endTime) < 0) {
					state = AttendanceState.LATE;
				} else
					state = AttendanceState.NOTATTENDED;
			} else {
				state = AttendanceState.NOTATTENDED;
			}
			attendanceStates[i] = new AttendanceState(user.getId(), user.getName(), classOfStudent.getName(), state);
		}
		return attendanceStates;
	}

	public List<User> getStudents(Integer courseId) {
		List<CourseAndStudent> cas = courseAndStudent_Repository.getByCourseId(courseId);
		int[] userIds = new int[cas.size()];
		for (int i = 0; i < cas.size(); i++)
			userIds[i] = cas.get(i).getStudentId();
		Specification<User> specification = new Specification<User>() {
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				for (int i = 0; i < userIds.length; i++) {
					predicates.add(cb.equal(root.get("id"), userIds[i]));
				}
				query.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
				return query.getRestriction();
			}
		};
		List<User> result = user_Repository.findAll(specification);
		return result;
	}

	public ClassOfStudent getClassOfStudent(Integer id) {
		return classOfStudent_Repository.findOne(id);
	}
}
