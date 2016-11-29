package com.prodigious.festivitiesapp.modelo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;

@XmlRootElement(name = "RequestSearch",namespace="http://www.wfmc.org/2008/XPDL2.1")
@XmlAccessorType(XmlAccessType.PROPERTY)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestSearch implements Serializable 
{
	private static final long serialVersionUID = 7346197148869446073L;
	private CurrentLocate currentLocation;
	private String type;
	private String value;
	private String value2;
	
	public RequestSearch(){}

	public RequestSearch(String type, String value2) {
		super();
		this.type = type;
		this.value2 = value2;
	}

	public RequestSearch(CurrentLocate currentLocation, String type, String value, String value2) {
		super();
		this.currentLocation = currentLocation;
		this.type = type;
		this.value = value;
		this.value2 = value2;
	}

	public CurrentLocate getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(CurrentLocate currentLocation) {
		this.currentLocation = currentLocation;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}
}
