package com.prodigious.festivitiesapp.dto;

import java.io.Serializable;
import java.util.List;

/**
 * retornar la informacion de la actualizacion al cliente
 * @author YanithLisset
 *
 */
public class ResponseUpdateDTO implements Serializable
{
	private static final long serialVersionUID = -7438754941094704785L;
	private StandardMessageDTO message;
	
	public ResponseUpdateDTO()
	{	
	}

	public ResponseUpdateDTO(StandardMessageDTO message, List<FestivitiesDTO> festivities) {
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
