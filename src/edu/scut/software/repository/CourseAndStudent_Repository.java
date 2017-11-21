package edu.scut.software.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.scut.software.entity.CourseAndStudent;


public interface CourseAndStudent_Repository extends JpaRepository<CourseAndStudent, Integer> {

	List<CourseAndStudent> getByCourseId(Integer courseId);

}
