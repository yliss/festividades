package com.prodigious.festivitiesapp.utilidades;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.bind.JAXB;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class UtilidadesGlobales 
{
	public String dateToStringUTC(Date date)
	{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		return format.format(date);
	}
	
	public Date StringUTCToDate(String dateString) throws ParseException
	{
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		Date date = format.parse(dateString);
		
		return date;
	}
	
	private final String LINESEPARATOR = "line.separator";
	private final String JACKSONPARSER = "JACKSON";
	private final String JAXBPARSER = "JAXB";
	
	public String getLineSeparator()
	{
		return System.getProperty(LINESEPARATOR);
	}
	
	/**
	 * MEtodo usado para realizar una transformacion de xmlstring en un modelo de objectos,
	 * si se va a usar jackson el modelo debe de tener las notaciones de jaxson y si 
	 * se quiere usar jaxb el modelo debe de tener las notaciones de jaxb
	 * @param xml XML que se convertira en el modelo
	 * @param classe clase modelo
	 * @param typeParser string que identificara si que usara jackson o jaxb
	 * @return se retornara una instancia de la clase modelos con los valores del xml
	 * @throws JsonParseException error en el parseo del xml
	 * @throws JsonMappingException error en la mapeo de los valores de xml a la clase
	 * @throws IOException excepcion en error de la lectura del archivo
	 */
	public Object parseXmlStringToModel(String xml,Class<?> classe,String typeParser) throws JsonParseException, JsonMappingException, IOException
	{
		Object object = null;
		if(typeParser.equals(JACKSONPARSER))
		{
			XmlMapper xmlMapper = new XmlMapper();
			object = xmlMapper.readValue(xml,classe);
		}
		else
			if(typeParser.equals(JAXBPARSER))
			{
				object = JAXB.unmarshal(new StringReader(xml), classe);
			}
		
		return object;
	}
	
	public String ProcesarArchivio(InputStream file,String nombreArchivo)
	{
		String xml = null;
		
		try 
		{
			xml = leerArchivo(file);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return xml;
	}
	
	private String leerArchivo(InputStream inputStream) throws IOException 
	{
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
	    try 
	    {
	        String line = "";
	        while ((line = br.readLine()) != null)
	        {
	            sb.append(line);
	        }
	        System.out.println(line);
	    } 
	    catch(Exception e)
	    {
	    	System.out.println(e.getMessage());
	    }
	    
	    return sb.toString();
	}
}
