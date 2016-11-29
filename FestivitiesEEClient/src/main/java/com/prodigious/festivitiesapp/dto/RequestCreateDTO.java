package com.prodigious.festivitiesapp.dto;

import java.io.Serializable;

/**
 * DTO para recibir una peticion de creacion de una festividad
 * @author YanithLisset
 *
 */
public class RequestCreateDTO implements Serializable 
{
	private static final long serialVersionUID = -7668512435751184828L;
	
	private FestivitiesDTO festivity;
	private CurrentLocateDTO currentLocateDto;
	
	public RequestCreateDTO(){}

	
	
	public RequestCreateDTO(FestivitiesDTO festivity) {
		super();
		this.festivity = festivity;
	}



	public FestivitiesDTO getFestivity() {
		return festivity;
	}

	public void setFestivity(FestivitiesDTO festivity) {
		this.festivity = festivity;
	}



	public CurrentLocateDTO getCurrentLocateDto() {
		return currentLocateDto;
	}



	public void setCurrentLocateDto(CurrentLocateDTO currentLocateDto) {
		this.currentLocateDto = currentLocateDto;
	}
	
	
}
