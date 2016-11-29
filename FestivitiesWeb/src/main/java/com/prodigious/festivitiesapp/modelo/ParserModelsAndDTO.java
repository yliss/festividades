package com.prodigious.festivitiesapp.modelo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.prodigious.festivitiesapp.dto.CurrentLocateDTO;
import com.prodigious.festivitiesapp.dto.FestivitiesDTO;
import com.prodigious.festivitiesapp.dto.StandardMessageDTO;
import com.prodigious.festivitiesapp.utilidades.UtilidadesGlobales;

public class ParserModelsAndDTO 
{
	private UtilidadesGlobales utilidades;
	public ParserModelsAndDTO()
	{
		utilidades = new UtilidadesGlobales();
	}
	
	public List<Festivity> parseDTOToModelFestivity(List<FestivitiesDTO> festivitiesDto)
	{
		List<Festivity> modelList = new ArrayList<>();
		
		for(FestivitiesDTO festivityDto : festivitiesDto)
		{
			Festivity festivity = parseDTOToModelFestivity(festivityDto);
			if(festivity != null)
				modelList.add(festivity);
		}
		
		return modelList;
	}
	
	public Festivity parseDTOToModelFestivity(FestivitiesDTO festivityDto)
	{
		Festivity festivity = new Festivity();
		
		String end = utilidades.dateToStringUTC(festivityDto.getEnd());
		String start = utilidades.dateToStringUTC(festivityDto.getStart());
		
		festivity.setEnd(end);
		festivity.setStart(start);
		festivity.setName(festivityDto.getName());
		festivity.setPlace(festivityDto.getPlace());
		
		return festivity;
	}
	
	public FestivitiesDTO parseModelToDTOFestivity(Festivity festivity) throws ParseException
	{
		FestivitiesDTO festivityDto = new FestivitiesDTO();
		
		Date end = utilidades.StringUTCToDate(festivity.getEnd());
		Date start = utilidades.StringUTCToDate(festivity.getStart());
		
		festivityDto.setEnd(end);
		festivityDto.setStart(start);
		festivityDto.setName(festivity.getName());
		festivityDto.setPlace(festivity.getPlace());
		
		return festivityDto;
	}
	
	public StandardMessage parseDTOToModelStandardMessage(StandardMessageDTO standardMessageDto)
	{
		StandardMessage message = new StandardMessage();
		
		message.setCode(standardMessageDto.getCode());
		message.setMessage(standardMessageDto.getMessage());
		
		return message;
	}
	
	public CurrentLocateDTO parseModelToDTO(CurrentLocate currentLocate)
	{
		CurrentLocateDTO currentLocateDto = new CurrentLocateDTO();
		
		currentLocateDto.setCountry(currentLocate.getCountry());
		currentLocateDto.setLanguague(currentLocate.getLanguague());
		
		return currentLocateDto;
	}
}
