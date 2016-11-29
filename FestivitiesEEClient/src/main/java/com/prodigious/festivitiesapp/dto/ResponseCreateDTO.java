package com.prodigious.festivitiesapp.dto;

import java.io.Serializable;
import java.util.List;

/**
 * dto para retornar el estado de la creacion de una festividad al cliente. 
 * @author YanithLisset
 *
 */
public class ResponseCreateDTO implements Serializable
{
	private static final long serialVersionUID = -7438754941094704785L;
	private StandardMessageDTO message;
	
	public ResponseCreateDTO()
	{	
	}

	public ResponseCreateDTO(StandardMessageDTO message, List<FestivitiesDTO> festivities) {
		super();
		this.message = message;
	}

	public StandardMessageDTO getMessage() {
		return message;
	}

	public void setMessage(StandardMessageDTO message) {
		this.message = message;
	}
}
