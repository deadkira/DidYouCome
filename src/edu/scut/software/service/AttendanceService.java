package edu.scut.software.service;

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
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.scut.software.entity.AttendanceRecord;
import edu.scut.software.entity.AttendanceState;
import edu.scut.software.entity.ClassOfStudent;
import edu.scut.software.entity.Course;
import edu.scut.software.entity.CourseAndStudent;
import edu.scut.software.entity.Lesson;
import edu.scut.software.entity.Student;
import edu.scut.software.entity.Teacher;
import edu.scut.software.repository.AttendanceRecord_Repository;
import edu.scut.software.repository.ClassOfStudent_Repository;
import edu.scut.software.repository.CourseAndStudent_Repository;
import edu.scut.software.repository.Course_Repository;
import edu.scut.software.repository.Lesson_Repository;
import edu.scut.software.repository.Student_Repository;
import edu.scut.software.repository.Teacher_Repository;
import edu.scut.software.tool.Helper;

@Service
public class AttendanceService {
	@Autowired
	AttendanceRecord_Repository attendanceRecord_Repository;
	@Autowired
	ClassOfStudent_Repository classOfStudent_Repository;
	@Autowired
	Course_Repository course_Repository;
	@Autowired
	CourseAndStudent_Repository courseAndStudent_Repository;
	@Autowired
	Student_Repository student_Repository;
	@Autowired
	Teacher_Repository teacher_Repository;
	@Autowired
	Lesson_Repository lesson_Repository;

	@Transactional(readOnly = true)
	public Teacher getTeacher(String username, String password) {
		return teacher_Repository.getByUsernameIgnoreCaseAndPassword(username.trim().toLowerCase(), password);
	}

	@Transactional
	public void saveTeacher(Teacher teacher) {
		teacher_Repository.saveAndFlush(teacher);
	}

	@Transactional
	public Course[] getCoursesByCondition(Integer year, Integer term, String name, Teacher teacher) {
		Specification<Course> specification = new Specification<Course>() {
			public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (year != -1)
					predicates.add(cb.equal(root.get("year"), year));
				if (term != -1)
					predicates.add(cb.equal(root.get("term"), term));
				if (!name.trim().equals(""))
					predicates.add(cb.equal(root.get("name"), name.trim()));
				if (teacher != null)
					predicates.add(cb.equal(root.get("teacherId"), teacher.getId()));
				query.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
				return query.getRestriction();
			}
		};
		List<Course> result = course_Repository.findAll(specification);
		return result.toArray(new Course[result.size()]);
	}

	@Transactional
	public Course getCourse(Integer id) {
		return course_Repository.getById(id);
	}

	@Transactional
	public List<Lesson> getLessons(Integer courseId) {
		return lesson_Repository.getByCourseId(courseId);

	}

	@Transactional
	public List<AttendanceState> getAttendanceStates(Integer courseId, Integer lessonId) {
		List<Student> students = getStudents(courseId);
		List<AttendanceState> attendanceStates = new ArrayList<>();
		AttendanceRecord record;
		for (int i = 0; i < students.size(); i++) {
			Student student = students.get(i);
			ClassOfStudent classOfStudent = classOfStudent_Repository.getById(student.getClassOfStudent());
			record = attendanceRecord_Repository.getByLessonIdAndStudentId(lessonId, student.getId());
			if (record != null) {
				attendanceStates.add(new AttendanceState(lessonId, student.getUsername(), student.getName(),
						classOfStudent.getName(), AttendanceState.ATTENDED));
			} else {
				attendanceStates.add(new AttendanceState(-1, student.getUsername(), student.getName(),
						classOfStudent.getName(), AttendanceState.NOTATTENDED));
			}
		}
		return attendanceStates;
	}

	@Transactional
	public List<Student> getStudents(Integer courseId) {
		List<CourseAndStudent> cas = courseAndStudent_Repository.getByCourseId(courseId);
		int[] studentIds = new int[cas.size()];
		for (int i = 0; i < cas.size(); i++)
			studentIds[i] = cas.get(i).getStudentId();
		Specification<Student> specification = new Specification<Student>() {
			public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				for (int i = 0; i < studentIds.length; i++) {
					predicates.add(cb.equal(root.get("id"), studentIds[i]));
				}
				query.where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
				return query.getRestriction();
			}
		};
		List<Student> result = student_Repository.findAll(specification);
		return result;
	}

	@Transactional
	public void createCourse(String name, Integer teacherId, String venue, Integer year, Integer term, Date startDate,
			Date endDate, Date startTime, Date endTime, Integer whatDay) {
		Course course = new Course(name, teacherId, venue, year, term, 0, startDate, endDate, startTime, endTime,
				whatDay, Course.NOTBEGINNING, true);
		Integer numberOfCourse = 0;
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(course.getStartDate());
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(course.getEndDate());
		endCalendar.add(Calendar.DAY_OF_WEEK, 1);
		List<Lesson> lessons = new ArrayList<>();
		for (int i = 0; i < numberOfCourse && startCalendar.compareTo(endCalendar) < 0; i++) {
			Lesson lesson = new Lesson();
			lesson.setCourseId(course.getId());
			lesson.setSequence(i + 1);
			Integer whatDay1 = startCalendar.get(Calendar.DAY_OF_WEEK) - 1;
			while (!whatDay1.equals(course.getWhatDay())) {
				startCalendar.add(Calendar.DAY_OF_WEEK, 1);
			}
			lesson.setCourseDate(startCalendar.getTime());
			lesson.setCourseStartTime(course.getStartTime());
			lesson.setCourseEndTime(course.getEndTime());
			Date current = new Date();
			Date startTime1 = Helper.combineDateWithTime(startCalendar.getTime(), course.getStartTime());
			Date endTime1 = Helper.combineDateWithTime(startCalendar.getTime(), course.getEndTime());
			if (current.compareTo(startTime1) < 0)
				lesson.setState(Lesson.NOTDOYET);
			else if (current.compareTo(endTime1) < 0)
				lesson.setState(Lesson.DOING);
			else
				lesson.setState(Lesson.DONE);
			lesson.setValidate(true);
			lessons.add(lesson);
		}
		numberOfCourse = lessons.size();
		course_Repository.save(course);
		lesson_Repository.save(lessons);
	}

	@Transactional
	public void saveLesson(Lesson lesson) {
		lesson_Repository.saveAndFlush(lesson);
	}

	@Transactional
	public Teacher getTeacher(String token) {
		return teacher_Repository.getByToken(token);
	}

	@Transactional
	public List<Course> getCourses(Integer teacherId) {
		return course_Repository.getByTeacherId(teacherId);
	}

	@Transactional
	public Lesson getLesson(Integer id) {
		return lesson_Repository.getById(id);
	}

	@Transactional
	public AttendanceRecord getAttendanceRecord(Integer lessonId, Integer studentId) {
		return attendanceRecord_Repository.getByLessonIdAndStudentId(lessonId, studentId);
	}

	@Transactional
	public void saveAttendanceRecord(AttendanceRecord attendanceRecord) {
		attendanceRecord_Repository.save(attendanceRecord);
	}

	@Transactional
	public void removeAttendanceRecord(AttendanceRecord attendanceRecord) {
		attendanceRecord_Repository.delete(attendanceRecord);
	}

	@Transactional
	public List<Lesson> getLessonsNotDoYet(Date courseDate, Date courseStartTime) {
		return lesson_Repository.getByCourseDateAndCourseStartTimeAndState(courseDate, courseStartTime,
				Lesson.NOTDOYET);
	}

	@Transactional
	public void saveLessons(List<Lesson> lessons) {
		lesson_Repository.save(lessons);
	}

	@Transactional
	public List<Lesson> getLessonsDoing(Date courseDate, Date courseEndTime) {
		return lesson_Repository.getByCourseDateAndCourseEndTimeAndState(courseDate, courseEndTime, Lesson.DOING);
	}

	@Transactional
	public Student getStudent(String username, String password) {
		return student_Repository.getByUsernameIgnoreCaseAndPassword(username, password);
	}

	@Transactional
	public void saveStudent(Student student) {
		student_Repository.save(student);
	}

	@Transactional
	public Student getStudent(String token) {
		return student_Repository.getByToken(token);
	}

	@Transactional
	public Lesson getcurrentlesson(Integer studentId) {
		return lesson_Repository.getLesson(studentId,Lesson.DOING);
	}

	@Transactional
	public Integer getAttendingTimes(Integer studentId, Integer courseId) {
		return attendanceRecord_Repository.getAttendingTimes(studentId, courseId);
	}

	@Transactional
	public Teacher getTeacher(Integer id) {
		return teacher_Repository.findOne(id);
	}

	@Transactional
	public void attend(Integer studentId, Integer lessonId) {

		attendanceRecord_Repository.save(new AttendanceRecord(lessonId, studentId, new Date(), true));
	}
}
