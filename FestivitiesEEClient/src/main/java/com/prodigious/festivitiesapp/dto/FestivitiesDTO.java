package com.prodigious.festivitiesapp.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * dto para tranferirir un objeto de una festividad hacia el servicio web
 * @author YanithLisset
 *
 */
public class FestivitiesDTO implements Serializable 
{
	private static final long serialVersionUID = -5397383005216909673L;
	private String name;
	private String place;
	private Date start;
	private Date end;
	
	public FestivitiesDTO(){}

	public FestivitiesDTO(String name, String place, Date start, Date end) {
		super();
		this.name = name;
		this.place = place;
		this.start = start;
		this.end = end;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
}
