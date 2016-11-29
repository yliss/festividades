package com.prodigious.festivitiesapp.modelo;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;

@XmlRootElement(name = "ResponseSearch",namespace="http://www.wfmc.org/2008/XPDL2.1")
@XmlAccessorType(XmlAccessType.PROPERTY)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseSearch implements Serializable
{
	private static final long serialVersionUID = 5047345701833134902L;
	private StandardMessage message;
	private List<Festivity> festivities;
	
	public ResponseSearch()
	{
		message =  new StandardMessage();
	}
	
	public ResponseSearch(StandardMessage message, List<Festivity> festivities) {
		super();
		this.message = message;
		this.festivities = festivities;
	}

	public void setMessage(StandardMessage message) {
		this.message = message;
	}
	
	public void setMessage(String message) 
	{
		if(this.message != null)
			this.message.setMessage(message);
	}
	
	public void setMessage(String code,String message) 
	{
		if(this.message != null)
			this.message.setMessage(message);
	}

	public List<Festivity> getFestivities() {
		return festivities;
	}

	public void setFestivities(List<Festivity> festivities) {
		this.festivities = festivities;
	}
	
}
