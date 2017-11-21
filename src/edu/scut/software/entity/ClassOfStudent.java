package edu.scut.software.entity;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Cacheable
@Table(name = "ClassOfStudent")
@Entity
public class ClassOfStudent {
	private Integer id;
	private String name;
	private boolean valid;

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

	public boolean getValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public ClassOfStudent(String name, boolean valid) {
		super();
		this.name = name;
		this.valid = valid;
	}

	public ClassOfStudent() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
