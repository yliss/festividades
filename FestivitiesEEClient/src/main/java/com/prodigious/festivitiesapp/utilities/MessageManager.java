package com.prodigious.festivitiesapp.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * Clase para realizar la busqueda de los mensaajes segun localizacion informada en el constructor de la clase. 
 * @author YanithLisset
 *
 */
public class MessageManager 
{
	private Locale currentLocale;
	private ResourceBundle resourceBundle;
	private String languague;
	private String country;
	
	/**
	 * constructor que recibe los strings del pais e idioma
	 * @param languague maneja el palicativo
	 * @param country donde hacen el llamado al aplicativo
	 */
	public MessageManager(String languague,String country)
	{
		this.languague = languague;
		this.country = country;
		
		currentLocale = new Locale(languague, country);

		resourceBundle = ResourceBundle.getBundle("com.prodigious.festivitiesapp.utilities.MessagesBundle", currentLocale);
	}
	
	/**
	 * Constructor que recibe el objeto instalacion de  localizacion y el control de los mensajes. 
	 * @param currentLocale: objeto que almacena la localizacion y lenguaje
	 * @param resourceBundle: objeto que carga y administra los archivos propiedades de manejo de localizacion
	 */
	public MessageManager(Locale currentLocale,ResourceBundle resourceBundle){
		this.currentLocale = currentLocale;
		this.resourceBundle = resourceBundle;
	}
	
	/**
	 * metodo que retorna una variable del archivo por ID
	 * @param idMessage id mensaje
	 * @return valor de una variable del archivo.
	 */
	public String getApplicationMessage(String idMessage)
	{
		if(resourceBundle != null)
			return resourceBundle.getString(idMessage);
		
		return null;
	}
	
	/** 
	 * metodo usado para obtener todos los parametros del archivo por u patron
	 * @param pattern patron con el que se buscara
	 * @return reornara la lista de los nombres de los parametros
	 */
	public List<String> getAllByPattern(String pattern)
	{
		List<String> variables = new ArrayList<>();
		Set<String> keys = resourceBundle.keySet();
		for (String key : keys) 
		{
			if(key.startsWith(pattern))
				variables.add(key);
		}
		
		return variables;
	}

	public String getLanguague() {
		return languague;
	}

	public void setLanguague(String languague) {
		this.languague = languague;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
