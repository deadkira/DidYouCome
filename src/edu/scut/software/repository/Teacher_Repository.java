package edu.scut.software.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import edu.scut.software.entity.Student;
import edu.scut.software.entity.Teacher;

public interface Teacher_Repository extends JpaRepository<Teacher, Integer>, JpaSpecificationExecutor<Teacher> {

	Teacher getByUsernameIgnoreCaseAndPassword(String username, String password);

	Teacher getByToken(String token);

}
