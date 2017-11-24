package edu.scut.software.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import edu.scut.software.entity.Course;
import edu.scut.software.entity.CourseMessage;

public interface CourseMessage_Repository
		extends JpaRepository<CourseMessage, Integer>, JpaSpecificationExecutor<CourseMessage> {

	List<CourseMessage> getByCourseId(Integer id);
}
