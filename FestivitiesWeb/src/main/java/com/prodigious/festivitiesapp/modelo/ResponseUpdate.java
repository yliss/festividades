package com.prodigious.festivitiesapp.modelo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;

@XmlRootElement(name = "ResponseUpdate",namespace="http://www.wfmc.org/2008/XPDL2.1")
@XmlAccessorType(XmlAccessType.PROPERTY)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUpdate implements Serializable 
{
	private static final long serialVersionUID = -6180183682418662307L;
	private StandardMessage message;
	
	public ResponseUpdate(){
		message = new StandardMessage();
	}

	public ResponseUpdate(StandardMessage message) {
		super();
		this.message = message;
	}

	public StandardMessage getMessage() {
		return message;
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
}
