package com.prodigious.festivitiesapp.modelo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;

@XmlRootElement(name = "RequestUpdate",namespace="http://www.wfmc.org/2008/XPDL2.1")
@XmlAccessorType(XmlAccessType.PROPERTY)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestUpdate implements Serializable
{
	private static final long serialVersionUID = -3472381479336728522L;
	private CurrentLocate currentLocate;
	private Festivity festivity;
	
	public RequestUpdate(){}

	public RequestUpdate(CurrentLocate currentLocate, Festivity festivity) {
		super();
		this.currentLocate = currentLocate;
		this.festivity = festivity;
	}

	public CurrentLocate getCurrentLocate() {
		return currentLocate;
	}

	public void setCurrentLocate(CurrentLocate currentLocate) {
		this.currentLocate = currentLocate;
	}

	public Festivity getFestivity() {
		return festivity;
	}

	public void setFestivity(Festivity festivity) {
		this.festivity = festivity;
	}
}
