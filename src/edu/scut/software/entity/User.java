package edu.scut.software.entity;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Cacheable
@Table(name = "User")
@Entity
public class User {
	private Integer id;
	private String name;
	// private String avatarPath;
	// private Integer privilege;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdTime;
	private String username;
	private String password;
	private Integer classOfStudent;
	private int identity;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastLoginTime;
	private boolean valid;

	public static final int ADMIN = 0;
	public static final int STUDENT = 1;
	public static final int TEACHER = 2;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getIdentity() {
		return identity;
	}

	public void setIdentity(int identity) {
		this.identity = identity;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public boolean getValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public User(String name, Date createdTime, String username, String password, Integer classOfStudent, int identity,
			Date lastLoginTime, boolean valid) {
		super();
		this.name = name;
		this.createdTime = createdTime;
		this.username = username;
		this.password = password;
		this.classOfStudent = classOfStudent;
		this.identity = identity;
		this.lastLoginTime = lastLoginTime;
		this.valid = valid;
	}

	public Integer getClassOfStudent() {
		return classOfStudent;
	}

	public void setClassOfStudent(Integer classOfStudent) {
		this.classOfStudent = classOfStudent;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
