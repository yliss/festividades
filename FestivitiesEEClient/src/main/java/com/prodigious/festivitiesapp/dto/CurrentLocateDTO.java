package com.prodigious.festivitiesapp.dto;

import java.io.Serializable;

/**
 * Class used for location management 
 * @author YanithLisset
 *
 */
public class CurrentLocateDTO implements Serializable
{
	private static final long serialVersionUID = -1758449001481146700L;
	private String country;
	private String languague;
	
	public CurrentLocateDTO(){}
	
	public CurrentLocateDTO(String country, String languague) {
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
