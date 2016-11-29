package com.prodigious.festivitiesapp.dto;

import java.io.Serializable;

/**
 * dto para realizar la actulizacion de una festividad
 * @author YanithLisset
 *
 */
public class RequestUpdateDTO implements Serializable 
{
	private static final long serialVersionUID = -7668512435751184828L;
	
	private CurrentLocateDTO currentLocateDto;
	private FestivitiesDTO festivity;
	
	public RequestUpdateDTO(){}

	
	
	
	public RequestUpdateDTO(CurrentLocateDTO currentLocateDto, FestivitiesDTO festivity) {
		super();
		this.currentLocateDto = currentLocateDto;
		this.festivity = festivity;
	}




	public CurrentLocateDTO getCurrentLocateDto() {
		return currentLocateDto;
	}




	public void setCurrentLocateDto(CurrentLocateDTO currentLocateDto) {
		this.currentLocateDto = currentLocateDto;
	}




	public RequestUpdateDTO(FestivitiesDTO festivity) {
		super();
		this.festivity = festivity;
	}



	public FestivitiesDTO getFestivity() {
		return festivity;
	}

	public void setFestivity(FestivitiesDTO festivity) {
		this.festivity = festivity;
	}
}
