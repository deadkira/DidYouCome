package edu.scut.software.handler;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.scut.software.entity.User;
import edu.scut.software.service.BackendService;
import edu.scut.software.tool.Helper;

@Controller
public class BackendHandler {
	@Autowired
	private BackendService backendService;

	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Object login(@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password, HttpServletRequest request) {
		User user = backendService.getUser(username, password);
		if (user == null) {
			return false;
		} else {
			request.getSession().setAttribute("user", user);
			request.getSession().setAttribute("username", username.trim().toLowerCase());
			request.getSession().setAttribute("logined", true);
			user.setLastLoginTime(new Date());
			backendService.saveUser(user);
			return true;
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("logined", false);
		return "index";
	}

	@ResponseBody
	@RequestMapping(value = "/getCourses", method = RequestMethod.POST)
	public Object getCourseBycondition(@RequestParam(value = "year") Integer year,
			@RequestParam(value = "term") Integer term, @RequestParam(value = "name") String name,
			HttpServletRequest request) {
		return backendService.getCoursesByCondition(year, term, name, (User) request.getSession().getAttribute("user"));
	}

	@ResponseBody
	@RequestMapping(value = "/getCourseMessage", method = RequestMethod.GET)
	public Object getCourseMessage(HttpServletRequest request) {
		return backendService.getCourseMessage(Integer.parseInt(request.getParameter("id")));
	}

	@ResponseBody
	@RequestMapping(value = "/getAttendanceState", method = RequestMethod.GET)
	public Object getAttendanceState(HttpServletRequest request) {
		Integer courseId = Integer.parseInt(request.getParameter("id"));
		Date CourseDate = Helper.pareseString("yyyy-MM-dd", request.getParameter("date"));
		return backendService.getAttendanceState(courseId, CourseDate);
	}
}
