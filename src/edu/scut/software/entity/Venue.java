package edu.scut.software.entity;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Cacheable
@Table(name = "Venue")
@Entity
public class Venue {
	private Integer id;
	private String name;
	private Double venueLongitude;
	private Double venueLatitude;

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

	public Double getVenueLongitude() {
		return venueLongitude;
	}

	public void setVenueLongitude(Double venueLongitude) {
		this.venueLongitude = venueLongitude;
	}

	public Double getVenueLatitude() {
		return venueLatitude;
	}

	public void setVenueLatitude(Double venueLatitude) {
		this.venueLatitude = venueLatitude;
	}

	public Venue(String name, Double venueLongitude, Double venueLatitude) {
		super();
		this.name = name;
		this.venueLongitude = venueLongitude;
		this.venueLatitude = venueLatitude;
	}

	public Venue() {
		super();
	}
}
