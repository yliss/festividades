package com.prodigious.festivitiesapp.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResponseMessagesWindow implements Serializable 
{
	private static final long serialVersionUID = -4423554290435855753L;
	private List<MessagesWindow> messagesWindow;
	
	public ResponseMessagesWindow(){
		messagesWindow = new  ArrayList<>();
	}
	
	public ResponseMessagesWindow(List<MessagesWindow> messagesWindow) {
		super();
		this.messagesWindow = messagesWindow;
	}

	public List<MessagesWindow> getMessagesWindow() {
		return messagesWindow;
	}

	public void setMessagesWindow(List<MessagesWindow> messagesWindow) {
		this.messagesWindow = messagesWindow;
	}
	
	public void setMessagesWindow(MessagesWindow messagesWindow) {
		if(this.messagesWindow != null)
			this.messagesWindow.add(messagesWindow);
	}
}
