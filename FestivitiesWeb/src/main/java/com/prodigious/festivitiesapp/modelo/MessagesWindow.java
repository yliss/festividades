package com.prodigious.festivitiesapp.modelo;

import java.io.Serializable;

public class MessagesWindow implements Serializable 
{
	private static final long serialVersionUID = -2487566154801390053L;
	private String name;
	private String value;
	
	public MessagesWindow(){}
	
	public MessagesWindow(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}	
}
