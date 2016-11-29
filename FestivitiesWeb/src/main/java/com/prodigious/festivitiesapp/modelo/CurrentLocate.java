package com.prodigious.festivitiesapp.modelo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;

@XmlRootElement(name = "CurrentLocate",namespace="http://www.wfmc.org/2008/XPDL2.1")
@XmlAccessorType(XmlAccessType.PROPERTY)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CurrentLocate implements Serializable 
{
	private static final long serialVersionUID = -4645168427646883355L;
	private String country;
	private String languague;
	
	public CurrentLocate(){}

	public CurrentLocate(String country, String languague) {
		super();
		this.country = country;
		this.languague = languague;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLanguague() {
		return languague;
	}

	public void setLanguague(String languague) {
		this.languague = languague;
	}
}
