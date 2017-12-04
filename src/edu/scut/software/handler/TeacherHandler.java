package edu.scut.software.handler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;

import com.mysql.fabric.xmlrpc.base.Array;

import edu.scut.software.entity.AttendanceRecord;
import edu.scut.software.entity.Course;
import edu.scut.software.entity.Lesson;
import edu.scut.software.entity.Teacher;
import edu.scut.software.service.AttendanceService;
import edu.scut.software.tool.Helper;

@Controller
public class TeacherHandler {
	@Autowired
	private AttendanceService attendanceService;
	private Date[] lessonTimePoints = { Course.FIFTH_LESSON_BEGIN_TIME, Course.FIFTH_LESSON_END_TIME,
			Course.SECOND_LESSON_BEGIN_TIME, Course.SECOND_LESSON_END_TIME, Course.THIRD_LESSON_BEGIN_TIME,
			Course.THIRD_LESSON_END_TIME, Course.FOURTH_LESSON_BEGIN_TIME, Course.FOURTH_LESSON_END_TIME,
			Course.FIFTH_LESSON_BEGIN_TIME, Course.FIFTH_LESSON_END_TIME, Course.SISTH_LESSON_BEGIN_TIME,
			Course.SISTH_LESSON_END_TIME, Course.SEVENTH_LESSON_BEGIN_TIME, Course.SEVENTH_LESSON_END_TIME,
			Course.EIGHTH_LESSON_BEGIN_TIME, Course.EIGHTH_LESSON_END_TIME, Course.NINTH_LESSON_BEGIN_TIME,
			Course.NINTH_LESSON_END_TIME, Course.TENTH_LESSON_BEGIN_TIME, Course.TENTH_LESSON_END_TIME,
			Course.ELEVENTH_LESSON_BEGIN_TIME, Course.ELEVENTH_LESSON_END_TIME };

	@PostConstruct
	public void init() {
		for (int i = 0; i < lessonTimePoints.length; i++) {
			final int j = i;
			Date lessonTime = lessonTimePoints[j];
			TimerTask task = new TimerTask() {
				public void run() {
					Calendar c = Calendar.getInstance();
					c.set(Calendar.MILLISECOND, 0);
					c.set(Calendar.SECOND, 0);
					c.set(Calendar.MINUTE, 0);
					c.set(Calendar.HOUR_OF_DAY, 0);
					Date currentDate = c.getTime();
					Date currentTime = lessonTime;
					List<Lesson> lessons = null;
					if (j % 2 == 0) {
						lessons = attendanceService.getLessonsNotDoYet(currentDate, currentTime);
						for (int k = 0; k < lessons.size(); k++) {
							lessons.get(k).setState(Lesson.DOING);
						}
					} else {
						lessons = attendanceService.getLessonsDoing(currentDate, currentTime);
						for (int k = 0; k < lessons.size(); k++) {
							lessons.get(k).setState(Lesson.DONE);
						}
					}
					if (lessons != null && lessons.size() > 0)
						attendanceService.saveLessons(lessons);
				}
			};
			Date taskStartDate = Calendar.getInstance().getTime();
			Date taskStartTime = lessonTime;
			new Timer().scheduleAtFixedRate(task, Helper.combineDateWithTime(taskStartDate, taskStartTime),
					24 * 60 * 60 * 1000);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/teacherLogin", method = RequestMethod.POST)
	public Object login(@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password, HttpServletRequest request,
			HttpServletResponse response) {
		Teacher teacher;
		if (username != null && password != null) {
			teacher = attendanceService.getTeacher(username, password);
			if (teacher == null) {
				return false;
			} else {
				teacher.setLastLoginTime(new Date());
				teacher.setToken(Helper.generateToken(teacher.getUsername()));
				attendanceService.saveTeacher(teacher);
				return Helper.toMap("data", teacher);
			}
		} else {
			String token = request.getHeader("Authorization");
			teacher = attendanceService.getTeacher(token);
			if (teacher == null) {
				return false;
			}
			teacher.setLastLoginTime(new Date());
			attendanceService.saveTeacher(teacher);
			return Helper.toMap("data", teacher);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/teacherLogout", method = RequestMethod.DELETE)
	public Object logout(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		Teacher teacher = attendanceService.getTeacher(token);
		if (teacher == null)
			return false;
		teacher.setToken(null);
		attendanceService.saveTeacher(teacher);
		return true;
	}

	@ResponseBody
	@RequestMapping(value = "/getAllCourses", method = RequestMethod.GET)
	public Object getAllCourses(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		Teacher teacher = attendanceService.getTeacher(token);
		if (teacher == null)
			return false;
		return Helper.toMap("data", attendanceService.getCourses(teacher.getUsername()));
	}

	@ResponseBody
	@RequestMapping(value = "/getLessons", method = RequestMethod.GET)
	public Object getLessons(@RequestParam(value = "course_id", required = true) Integer course_id,
			HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		Teacher teacher = attendanceService.getTeacher(token);
		if (teacher == null)
			return false;
		Course course = attendanceService.getCourse(course_id);
		if (course == null)
			return false;
		return Helper.toMap("data", attendanceService.getLessons(course_id));
	}

	@ResponseBody
	@RequestMapping(value = "/getAttendanceStates", method = RequestMethod.GET)
	public Object getAttendanceStates(HttpServletRequest request,
			@RequestParam(value = "lesson_id", required = true) Integer lesson_id) {
		String token = request.getHeader("Authorization");
		Teacher teacher = attendanceService.getTeacher(token);
		if (teacher == null)
			return false;
		Lesson lesson = attendanceService.getLesson(lesson_id);
		return Helper.toMap("data", attendanceService.getAttendanceStates(lesson.getCourseId(), lesson_id));
	}

	@ResponseBody
	@RequestMapping(value = "/modifyLesson", method = RequestMethod.POST)
	public Object modifyLesson(HttpServletRequest request, @RequestParam(value = "Id") Integer Id,
			@RequestParam(value = "courseDate") Date courseDate,
			@RequestParam(value = "courseStartTime") Date courseStartTime,
			@RequestParam(value = "courseEndTime") Date courseEndTime) {
		String token = request.getHeader("Authorization");
		Teacher teacher = attendanceService.getTeacher(token);
		if (teacher == null)
			return false;
		Lesson lesson = attendanceService.getLesson(Id);
		if (!lesson.getState().equals(Lesson.NOTDOYET))
			return false;
		lesson.setCourseDate(courseDate);
		lesson.setCourseStartTime(courseStartTime);
		lesson.setCourseEndTime(courseEndTime);
		attendanceService.saveLesson(lesson);
		return true;
	}

	@ResponseBody
	@RequestMapping(value = "/modifyAttendanceRecord", method = RequestMethod.POST)
	public Object modifyAttendanceRecord(HttpServletRequest request, @RequestParam(value = "lessonId") Integer lessonId,
			@RequestParam(value = "studentId") Integer studentId, @RequestParam(value = "state") Boolean state) {
		String token = request.getHeader("Authorization");
		Teacher teacher = attendanceService.getTeacher(token);
		if (teacher == null)
			return false;
		AttendanceRecord attendanceRecord = attendanceService.getAttendanceRecord(lessonId, studentId);
		if (state && attendanceRecord != null || !state && attendanceRecord == null) {
			return false;
		}
		if (state) {
			attendanceService.saveAttendanceRecord(new AttendanceRecord(lessonId, studentId, new Date(), true));
		} else {
			attendanceService.removeAttendanceRecord(attendanceRecord);
		}
		return true;
	}

	@ResponseBody
	@RequestMapping(value = "/addCourse", method = RequestMethod.POST)
	public Object addCourse(@RequestParam(value = "name") String name,
			@RequestParam(value = "teacherId") Integer teacherId, @RequestParam(value = "venue") String venue,
			@RequestParam(value = "year") Integer year, @RequestParam(value = "term") Integer term,
			@RequestParam(value = "startDate") Date startDate, @RequestParam(value = "endDate") Date endDate,
			@RequestParam(value = "startTime") Integer startTimeIndex,
			@RequestParam(value = "endTime") Integer endTimeIndex, @RequestParam(value = "whatDay") Integer whatDay) {
		Date startTime = lessonTimePoints[startTimeIndex * 2];
		Date endTime = lessonTimePoints[endTimeIndex * 2 + 1];
		if (startDate.compareTo(endDate) >= 0 || startTime.compareTo(endTime) >= 0)
			return false;
		if (!(whatDay >= 0 && whatDay <= 6))
			return false;
		attendanceService.createCourse(name, teacherId, venue, year, term, startDate, endDate, startTime, endTime,
				whatDay);
		return true;
	}
}
