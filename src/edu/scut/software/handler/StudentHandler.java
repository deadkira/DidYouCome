package edu.scut.software.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.scut.software.entity.AttendanceRecord;
import edu.scut.software.entity.Course;
import edu.scut.software.entity.CurrentLesson;
import edu.scut.software.entity.Lesson;
import edu.scut.software.entity.Student;
import edu.scut.software.entity.Teacher;
import edu.scut.software.service.AttendanceService;
import edu.scut.software.tool.Helper;

@Controller
public class StudentHandler {
	@Autowired
	private AttendanceService attendanceService;

	@ResponseBody
	@RequestMapping(value = "/studentLogin", method = RequestMethod.POST)
	public Object login(@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Student student;
		
		if (username != null && password != null) {
			student = attendanceService.getStudent(username.trim(), password);
			if (student == null) {
				return false;
			} else {
				student.setLastLoginTime(new Date());
				student.setToken(Helper.generateToken(student.getUsername()));
				attendanceService.saveStudent(student);
			}
		} else {
			String token = request.getHeader("Authorization");
			student = attendanceService.getStudent(token);
			if (student == null) {
				return false;
			}
			student.setLastLoginTime(new Date());
			attendanceService.saveStudent(student);
		}
		String url = request.getServletContext().getRealPath("/WEB-INF/image/" + student.getUsername() + ".jpg");
		FileInputStream fi = new FileInputStream(url);
		byte[] bytes = new byte[fi.available()];
		fi.read(bytes);
		String avatarStr = new String(Base64.encodeBase64(bytes));
		Map<String, Object> map = Helper.toMap("data", student);
		map.put("icon", avatarStr);
		return map;
	}

	@ResponseBody
	@RequestMapping(value = "/getCurrentLesson", method = RequestMethod.POST)
	public Object getCurrentLesson(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		Student student = attendanceService.getStudent(token);
		if (student == null)
			return false;
		Lesson lesson = attendanceService.getcurrentlesson(student.getId());
		if (lesson == null)
			return false;
		Course course = attendanceService.getCourse(lesson.getCourseId());
		Teacher teacher = attendanceService.getTeacher(course.getTeacherId());
		Integer attendingTimes = attendanceService.getAttendingTimes(student.getId(), lesson.getCourseId());
		AttendanceRecord attendanceRecord = attendanceService.getAttendanceRecord(student.getId(), lesson.getId());
		Boolean isAttended = attendanceRecord != null ? true : false;
		return new CurrentLesson(lesson.getId(), lesson.getCourseId(), course.getName(), teacher.getName(),
				lesson.getCourseStartTime(), lesson.getCourseEndTime(), attendingTimes, lesson.getSequence(),
				course.getVenue(), course.getNumber(), isAttended);
	}

	@ResponseBody
	@RequestMapping(value = "/attend", method = RequestMethod.POST)
	public Object studentAttend(HttpServletRequest request,
			@RequestParam(value = "lessonId", required = true) Integer lessonId) {
		String token = request.getHeader("Authorization");
		Student student = attendanceService.getStudent(token);
		if (student == null)
			return false;
		attendanceService.attend(student.getId(), lessonId);
		return true;
	}

	@ResponseBody
	@RequestMapping(value = "/studentLogout", method = RequestMethod.POST)
	public Object studentLogout(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		Student student = attendanceService.getStudent(token);
		if (student == null)
			return false;
		student.setToken(null);
		attendanceService.saveStudent(student);
		return true;
	}
}
