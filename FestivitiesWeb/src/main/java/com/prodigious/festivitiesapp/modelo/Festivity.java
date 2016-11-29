package com.prodigious.festivitiesapp.modelo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;

@XmlRootElement(name = "Festivity",namespace="http://www.wfmc.org/2008/XPDL2.1")
@XmlAccessorType(XmlAccessType.PROPERTY)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Festivity implements Serializable
{
	private static final long serialVersionUID = -663991409162277426L;
	private String name;
	private String place;
	private String start;
	private String end;
	
	public Festivity(){}

	public Festivity(String name, String place, String start, String end) {
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

	public String  getStart() {
		return start;
	}

	public void setStart(String  start) {
		this.start = start;
	}

	public String  getEnd() {
		return end;
	}

	public void setEnd(String  end) {
		this.end = end;
	}
}
