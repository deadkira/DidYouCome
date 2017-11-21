package edu.scut.software.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import edu.scut.software.entity.User;

public interface User_Repository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

	User getByUsernameAndPassword(String username, String password);

}
