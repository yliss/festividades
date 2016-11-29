package com.prodigious.festivitiesapp.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * dto para retornar la respuesta de busqueda al cliente
 * @author YanithLisset
 *
 */
public class ResponseSearchDTO implements Serializable
{
	private static final long serialVersionUID = -6387747552744228622L;
	private StandardMessageDTO message;
	private List<FestivitiesDTO> festivities;
	
	public ResponseSearchDTO()
	{
		festivities = new ArrayList<>();
	}

	public ResponseSearchDTO(StandardMessageDTO message, List<FestivitiesDTO> festivities) 
	{
		super();
		this.message = message;
		this.festivities = festivities;
	}

	public StandardMessageDTO getMessage() {
		return message;
	}

	public void setMessage(StandardMessageDTO message) {
		this.message = message;
	}

	public List<FestivitiesDTO> getFestivities() {
		return festivities;
	}

	public void setFestivities(List<FestivitiesDTO> festivities) 
	{
		this.festivities = festivities;
	}
	
	public void setFestivities(FestivitiesDTO festivitie) 
	{
		if(this.festivities != null)
			this.festivities.add(festivitie);
	}
}
