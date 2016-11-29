package com.prodigious.festivitiesapp.dto;

import java.io.Serializable;

/**
 * 
 * @author YanithLisset
 *
 */
public class StandardMessageDTO implements Serializable 
{
	private static final long serialVersionUID = -2882449421007165738L;
	
	private String code;
	private String message;
	
	public StandardMessageDTO(){}
	
	public StandardMessageDTO(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
