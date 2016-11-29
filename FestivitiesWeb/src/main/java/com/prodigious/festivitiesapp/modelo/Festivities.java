package com.prodigious.festivitiesapp.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;

@XmlRootElement(name = "festivities",namespace="http://www.wfmc.org/2008/XPDL2.1")
@XmlAccessorType(XmlAccessType.PROPERTY)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Festivities implements Serializable 
{
	private static final long serialVersionUID = 5457324368880764810L;
	private List<Festivity> festivity;
	
	public Festivities(){
		festivity = new ArrayList<>();
	}
	
	public Festivities(List<Festivity> festivity) {
		super();
		this.festivity = festivity;
	}

	public List<Festivity> getFestivity() {
		return festivity;
	}

	public void setFestivity(List<Festivity> festivity) {
		this.festivity = festivity;
	}
	
	public void setFestivity(Festivity festivity) 
	{
		if(this.festivity != null)
			this.festivity.add(festivity);
	}
}
