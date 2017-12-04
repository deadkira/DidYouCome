package edu.scut.software.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import edu.scut.software.entity.Course;
import edu.scut.software.entity.Lesson;

public interface Lesson_Repository extends JpaRepository<Lesson, Integer>, JpaSpecificationExecutor<Lesson> {

	List<Lesson> getByCourseId(Integer courseId);

	Lesson getById(Integer id);

	List<Lesson> getByCourseDateAndCourseStartTimeAndState(Date courseDate, Date courseStartTime, String state);

	List<Lesson> getByCourseDateAndCourseEndTimeAndState(Date courseDate, Date courseEndTime, String state);

	@Query(value = "select l from Lesson l,CourseAndStudent c where l.courseId=c.courseId and c.studentId=?1 and l.state=?2")
	Lesson getLesson(Integer studentId,String state);
}
