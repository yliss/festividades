package com.prodigious.festivitiesapp.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceException;

import org.hibernate.exception.ConstraintViolationException;

import com.prodigious.festivitiesapp.dao.FestivitiesDAO;
import com.prodigious.festivitiesapp.dto.FestivitiesDTO;
import com.prodigious.festivitiesapp.dto.RequestCreateDTO;
import com.prodigious.festivitiesapp.dto.RequestSearchDTO;
import com.prodigious.festivitiesapp.dto.RequestUpdateDTO;
import com.prodigious.festivitiesapp.dto.ResponseCreateDTO;
import com.prodigious.festivitiesapp.dto.ResponseSearchDTO;
import com.prodigious.festivitiesapp.dto.ResponseUpdateDTO;
import com.prodigious.festivitiesapp.dto.StandardMessageDTO;
import com.prodigious.festivitiesapp.entities.Festivitie;
import com.prodigious.festivitiesapp.exceptions.ErroresException;
import com.prodigious.festivitiesapp.utilities.MessageManager;

/**
 * ejb susado para manejo del controlador de las festividades
 * @author YanithLisset
 *
 */
@Stateless
@LocalBean
public class FestivitiesManager implements FestivitiesManagerRemote, FestivitiesManagerLocal 
{
	@PersistenceContext(unitName = "Festivities", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;
	
	public FestivitiesManager() 
	{
	}
	
	/**
	 * metodo para convertir de jpa al modelo dto
	 * @param festivities
	 * @return modelo festivities dto
	 */
	private FestivitiesDTO parseEntityToDTO(Festivitie festivities)
	{
		if(festivities != null)
		{
			FestivitiesDTO festivitieDto = new FestivitiesDTO();
		
			festivitieDto.setName(festivities.getFteName());
			festivitieDto.setPlace(festivities.getFtePlace());
			festivitieDto.setEnd(festivities.getFteEnd());
			festivitieDto.setStart(festivities.getFteStart());
			
			return festivitieDto;
		}
		else
			return null;
	}
	
	/**
	 * metoto convertir una lista de registro de jpa a una lista dtos
	 * @param lista registros bd festivities
	 * @return lista dtos
	 */
	private List<FestivitiesDTO> parseEntitiesToDTO(List<Festivitie> festivities)
	{
		if(!festivities.isEmpty())
		{
			List<FestivitiesDTO> festivitiesDto = new ArrayList<>();
			for(Festivitie festivity : festivities)
			{
				FestivitiesDTO festivitieDto = parseEntityToDTO(festivity); 
				if(festivitieDto != null)
					festivitiesDto.add(festivitieDto);
			}
			
			return festivitiesDto;
		}
		else
			return null;
	}
	
	@Override
	/**
	 * meto de busquedda de una festividad en la bd
	 */
	public ResponseSearchDTO search(RequestSearchDTO request) throws ErroresException 
	{
		ResponseSearchDTO response = new ResponseSearchDTO();
		StandardMessageDTO message = new StandardMessageDTO();
		MessageManager messageManager = new MessageManager(request.getCurrentLocateDto().getLanguague(),request.getCurrentLocateDto().getCountry());
		try
		{
			FestivitiesDAO dao = new FestivitiesDAO();
			List<FestivitiesDTO> festivitiesDto = null;
			List<Festivitie> festivities = null;
			
			switch (request.getOperation()) {
			case "all":
				festivities = dao.getAll(entityManager);
				festivitiesDto = parseEntitiesToDTO(festivities);
				break;
			case "name":
				festivities = dao.getByName(request.getFestivitie().getName(), entityManager);
				festivitiesDto = parseEntitiesToDTO(festivities);
				break;
			case "place":
				festivities = dao.getByPlace(request.getFestivitie().getPlace(), entityManager);
				festivitiesDto = parseEntitiesToDTO(festivities);
				break;
			case "start":
				festivities = dao.getByStartDate(request.getFestivitie().getStart(), entityManager);
				festivitiesDto = parseEntitiesToDTO(festivities);
				break;
			case "range":
				festivities = dao.getByDateRange(request.getFestivitie().getStart(),request.getFestivitie().getEnd(), entityManager);
				festivitiesDto = parseEntitiesToDTO(festivities);
				break;	
			default:
				break;
			}
			
			if(festivitiesDto != null)
			{
				response.setFestivities(festivitiesDto);
				message.setCode("00");
				message.setMessage(messageManager.getApplicationMessage("MSG_01"));
			}
			else
			{
				message.setCode("50");
				message.setMessage(messageManager.getApplicationMessage("MSG_50"));
			}
		}
		catch (Exception e) 
		{
			message.setCode("100");
			message.setMessage(messageManager.getApplicationMessage("MSG_100"));
		}
		
		response.setMessage(message);
		
		return response;
	}

	@Override
	/**
	 * meotod para crear una festividad en la base de
	 */
	public ResponseCreateDTO create(RequestCreateDTO request) throws ErroresException 
	{
		ResponseCreateDTO response = new ResponseCreateDTO();
		StandardMessageDTO message = new StandardMessageDTO();
		MessageManager messageManager = new MessageManager(request.getCurrentLocateDto().getLanguague(),request.getCurrentLocateDto().getCountry());
		
		try
		{
			FestivitiesDAO dao = new FestivitiesDAO();
			boolean isOkInsert = false;
			
			Festivitie entity = new Festivitie();
			entity.setFteName(request.getFestivity().getName());
			entity.setFtePlace(request.getFestivity().getPlace());
			entity.setFteStart(request.getFestivity().getStart());
			entity.setFteEnd(request.getFestivity().getEnd());
			
			isOkInsert = dao.persist(entity, entityManager);
			
			if(isOkInsert)
			{
				message.setCode("01");
				message.setMessage(messageManager.getApplicationMessage("MSG_01"));
			}
			else
			{
				message.setCode("51");
				message.setMessage(messageManager.getApplicationMessage("MSG_51"));
			}
		}
		catch(ConstraintViolationException e)
		{
			message.setCode("52");
			message.setMessage(messageManager.getApplicationMessage("MSG_52"));
		}
		catch(PersistenceException e)
		{	
			message.setCode("101");
			message.setMessage(messageManager.getApplicationMessage("MSG_101"));
		}
		catch (Exception e) 
		{
			message.setCode("100");
			message.setMessage(messageManager.getApplicationMessage("MSG_100"));
		}
		
		response.setMessage(message);
		
		return response;
	}

	@Override
	/**
	 * meotodo para actualizar una entidad en la base de datos
	 */
	public ResponseUpdateDTO update(RequestUpdateDTO request) throws ErroresException 
	{
		ResponseUpdateDTO response = new ResponseUpdateDTO();
		StandardMessageDTO message = new StandardMessageDTO();
		MessageManager messageManager = new MessageManager(request.getCurrentLocateDto().getLanguague(),request.getCurrentLocateDto().getCountry());
		
		try
		{
			FestivitiesDAO dao = new FestivitiesDAO();
			boolean isOkUpdate = false;
			
			Festivitie entity = dao.findById(request.getFestivity().getName(), entityManager);
			
			if(entity == null)
			{
				message.setCode("04");
				message.setMessage(messageManager.getApplicationMessage("MSG_04"));
				
				response.setMessage(message);
				
				return response;
			}
			
			entity.setFteName(request.getFestivity().getName());
			entity.setFtePlace(request.getFestivity().getPlace());
			entity.setFteStart(request.getFestivity().getStart());
			entity.setFteEnd(request.getFestivity().getEnd());
			
			isOkUpdate = dao.merge(entity, entityManager);
			
			if(isOkUpdate)
			{
				message.setCode("02");
				message.setMessage(messageManager.getApplicationMessage("MSG_02"));
			}
			else
			{
				message.setCode("51");
				message.setMessage(messageManager.getApplicationMessage("MSG_51"));
			}
		}
		catch(ConstraintViolationException e)
		{
			message.setCode("52");
			message.setMessage(messageManager.getApplicationMessage("MSG_52"));
		}
		catch(PersistenceException e)
		{	
			message.setCode("101");
			message.setMessage(messageManager.getApplicationMessage("MSG_101"));
		}
		catch (Exception e) 
		{
			message.setCode("100");
			message.setMessage(messageManager.getApplicationMessage("MSG_100"));
		}
		
		response.setMessage(message);
		
		return response;
	}

}