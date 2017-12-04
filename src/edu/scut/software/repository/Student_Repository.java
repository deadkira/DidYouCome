package edu.scut.software.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import edu.scut.software.entity.Student;
import edu.scut.software.entity.Teacher;

public interface Student_Repository extends JpaRepository<Student, Integer>, JpaSpecificationExecutor<Student> {

	Student getByUsernameAndPassword(String username, String password);

	Student getByToken(String token);

}
