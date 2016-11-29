package com.prodigious.festivitiesapp.exceptions;

/**
 * Exepcion generial de l aplicativo para informar cualquier  error.
 * @author YanithLisset
 *
 */

public class ErroresException extends Exception 
{
	private static final long serialVersionUID = -8163645556382481886L;
	private String messageError;
	
	public ErroresException(String messageError)
	{
		super(messageError);
		this.messageError = messageError;
	}

	public String getMessageError() {
		return messageError;
	}

	public void setMessageError(String messageError) {
		this.messageError = messageError;
	}
}
