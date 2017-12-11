package edu.scut.software.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import edu.scut.software.entity.Course;

public interface Course_Repository extends JpaRepository<Course, Integer>, JpaSpecificationExecutor<Course> {

	Course getById(Integer id);

	List<Course> getByTeacherId(Integer teacherId);

}
