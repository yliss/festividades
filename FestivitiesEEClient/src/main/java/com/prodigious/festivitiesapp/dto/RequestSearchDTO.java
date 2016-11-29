package com.prodigious.festivitiesapp.dto;

import java.io.Serializable;

/**
 * dto para realizar l BUSQUEDA DE UNA FESTIVIDAD por vlos diferentes filtro de busqueda
 * @author YanithLisset
 *
 */
public class RequestSearchDTO implements Serializable 
{
	private static final long serialVersionUID = -6081064368812836978L;
	
	private CurrentLocateDTO currentLocateDto;
	private String operation;
	private FestivitiesDTO festivitie;
	
	public RequestSearchDTO(){}

	public RequestSearchDTO(String operation, FestivitiesDTO festivitie) {
		super();
		this.operation = operation;
		this.festivitie = festivitie;
	}

	public RequestSearchDTO(CurrentLocateDTO currentLocateDto, String operation, FestivitiesDTO festivitie) {
		super();
		this.currentLocateDto = currentLocateDto;
		this.operation = operation;
		this.festivitie = festivitie;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public FestivitiesDTO getFestivitie() {
		return festivitie;
	}

	public void setFestivitie(FestivitiesDTO festivitie) {
		this.festivitie = festivitie;
	}

	public CurrentLocateDTO getCurrentLocateDto() {
		return currentLocateDto;
	}

	public void setCurrentLocateDto(CurrentLocateDTO currentLocateDto) {
		this.currentLocateDto = currentLocateDto;
	}
	
	
}
