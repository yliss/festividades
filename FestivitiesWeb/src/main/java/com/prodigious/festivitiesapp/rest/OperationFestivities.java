package com.prodigious.festivitiesapp.rest;

import java.io.InputStream;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jettison.json.JSONException;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.prodigious.festivitiesapp.dto.CurrentLocateDTO;
import com.prodigious.festivitiesapp.dto.FestivitiesDTO;
import com.prodigious.festivitiesapp.dto.RequestCreateDTO;
import com.prodigious.festivitiesapp.dto.RequestSearchDTO;
import com.prodigious.festivitiesapp.dto.RequestUpdateDTO;
import com.prodigious.festivitiesapp.dto.ResponseCreateDTO;
import com.prodigious.festivitiesapp.dto.ResponseSearchDTO;
import com.prodigious.festivitiesapp.dto.ResponseUpdateDTO;
import com.prodigious.festivitiesapp.ejb.FestivitiesManagerRemote;
import com.prodigious.festivitiesapp.modelo.Festivities;
import com.prodigious.festivitiesapp.modelo.Festivity;
import com.prodigious.festivitiesapp.modelo.MessagesWindow;
import com.prodigious.festivitiesapp.modelo.ParserModelsAndDTO;
import com.prodigious.festivitiesapp.modelo.RequestCreate;
import com.prodigious.festivitiesapp.modelo.RequestSearch;
import com.prodigious.festivitiesapp.modelo.RequestUpdate;
import com.prodigious.festivitiesapp.modelo.ResponseCreate;
import com.prodigious.festivitiesapp.modelo.ResponseFileLoader;
import com.prodigious.festivitiesapp.modelo.ResponseMessagesWindow;
import com.prodigious.festivitiesapp.modelo.ResponseSearch;
import com.prodigious.festivitiesapp.modelo.StandardMessage;
import com.prodigious.festivitiesapp.utilities.MessageManager;
import com.prodigious.festivitiesapp.validations.SpecificValidations;
import com.prodigious.festivitiesapp.utilidades.UtilidadesGlobales;
import com.fasterxml.jackson.annotation.JsonInclude;

@Path("/Festivities")
public class OperationFestivities 
{
	private ParserModelsAndDTO parser;
	private final String COUNTRY = "CO";
	private final String LANGUAGUE = "sp";

	private final String jndiEjb = "java:app/FestivitiesEE-0.0.1-SNAPSHOT/FestivitiesManager!com.prodigious.festivitiesapp.ejb.FestivitiesManagerRemote";
	
	public OperationFestivities()
	{
		parser = new ParserModelsAndDTO();
	}
	
	private FestivitiesManagerRemote getFestivitiesManagerEjb() throws NamingException
	{
		InitialContext initialContext = new InitialContext();
		FestivitiesManagerRemote ejb = (FestivitiesManagerRemote) initialContext.lookup(jndiEjb);
		
		return ejb;
	}
	
	private String getFormatAccept(HttpHeaders headers)
	{
		String formatAcept = "";
		if(headers != null)
		{
			formatAcept = headers.getRequestHeader("Accept").get(0);
			if(formatAcept == null)
				formatAcept = headers.getRequestHeader("accept").get(0);
		}
		
		if(formatAcept.equals(""))
			return null;
		else
			return formatAcept;
	}
	
	private String getProducer(String headerAccept)
	{
		if(headerAccept.equals("application/json"))
			return MediaType.APPLICATION_JSON;
		else
			if(headerAccept.equals("application/xml"))
				return MediaType.APPLICATION_XML;
		
		return null;
	}
	
	private Response generateResponse(boolean isError,Status status,Object entity,String type)
	{
		if(isError)
			return Response.serverError().status(Response.Status.INTERNAL_SERVER_ERROR).build();
		else
			return Response.ok(entity,type).status(status).build();
	}
	
	@POST @Path("/LoadFile")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response LoadFile(
			@Context HttpHeaders headers,
			@FormDataParam("fleXpdl") InputStream uploadedInputStream,
			@FormDataParam("fleXpdl") FormDataContentDisposition fileDetail,
			@FormDataParam("hdnCountry") String country,
			@FormDataParam("hdnLanguague") String languague
			) throws JSONException 
	{
		Status status = null;
		FestivitiesManagerRemote festivitiesEjb = null;
		String headerAccept = getFormatAccept(headers);
		String producer = null;
		
		if(headerAccept != null)
			producer = getProducer(headerAccept);
		else
			return generateResponse(true, Status.INTERNAL_SERVER_ERROR, null, null);
		
		if(producer == null)
			return generateResponse(true, Status.INTERNAL_SERVER_ERROR, null, null);
		
		ResponseFileLoader response = new ResponseFileLoader();
		
		MessageManager messageManager = null;
		
		if(country != null && languague != null)
			messageManager = new MessageManager(languague,country);
		else
			messageManager = new MessageManager(LANGUAGUE,COUNTRY);
		
		try
		{
			festivitiesEjb = getFestivitiesManagerEjb();
			if(festivitiesEjb != null)
			{
				UtilidadesGlobales utilidadesGlobales = new UtilidadesGlobales();
				String nombreArchivo = fileDetail.getFileName();
				
				String xml = utilidadesGlobales.ProcesarArchivio(uploadedInputStream, nombreArchivo);
				
				Festivities festivities = (Festivities) utilidadesGlobales.parseXmlStringToModel(xml, Festivities.class, "JAXB");
				
				if(festivities != null)
				{
					int tamanioInicial = 0,
							cantidadRegistrosJuguetes = 0;
					
					tamanioInicial = festivities.getFestivity().size();
					
					for(Festivity festivity : festivities.getFestivity())
					{
						RequestCreateDTO requestCreate = new RequestCreateDTO();
						
						FestivitiesDTO festivitiesDto = new FestivitiesDTO();
						CurrentLocateDTO currentDto = new CurrentLocateDTO(messageManager.getCountry(),messageManager.getLanguague());
						
						festivitiesDto = parser.parseModelToDTOFestivity(festivity);
						
						requestCreate.setCurrentLocateDto(currentDto);
						requestCreate.setFestivity(festivitiesDto);
						
						ResponseCreateDTO responseDto = festivitiesEjb.create(requestCreate);
						
						if(responseDto.getMessage().getCode().equals("00"))
						{
							cantidadRegistrosJuguetes += 1;
						}
					}
					
					if(tamanioInicial == cantidadRegistrosJuguetes)
					{
						status = Response.Status.OK;
						response.setMessage("01", messageManager.getApplicationMessage("MSG_01"));
					}
					else
						if(cantidadRegistrosJuguetes > 0)
						{
							status = Response.Status.OK;
							response.setMessage("03", messageManager.getApplicationMessage("MSG_03"));
						}
						else
						{
							status = Response.Status.OK;
							response.setMessage("03", messageManager.getApplicationMessage("MSG_03"));
						}
				}
				else
				{
					status = Response.Status.INTERNAL_SERVER_ERROR;
					System.out.println("Hubo un error con la connexion con el ejb,verifique la correcta configuraciond el servidor");
					response.setMessage("70", messageManager.getApplicationMessage("MSG_70"));
				}	
				
			}
			else
			{
				status = Response.Status.INTERNAL_SERVER_ERROR;
				System.out.println("Hubo un error con la connexion con el ejb,verifique la correcta configuraciond el servidor");
				response.setMessage("100", messageManager.getApplicationMessage("MSG_100"));
			}
		}
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
			status = Response.Status.INTERNAL_SERVER_ERROR;
			response.setMessage("100", messageManager.getApplicationMessage("MSG_100"));
		}
		
		return generateResponse(false, status, response, producer); 
	}
	
	@GET @Path("/SearchAll/{country}&{languague}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public Response getSearchAll(@Context HttpHeaders headers,@PathParam("country") String country,@PathParam("languague") String languague) throws JSONException  
	{
		Status status = null;
		FestivitiesManagerRemote festivitiesEjb = null;
		String headerAccept = getFormatAccept(headers);
		String producer = null;
		
		if(headerAccept != null)
			producer = getProducer(headerAccept);
		else
			return generateResponse(true, Status.INTERNAL_SERVER_ERROR, null, null);
		
		if(producer == null)
			return generateResponse(true, Status.INTERNAL_SERVER_ERROR, null, null);
		
		ResponseSearch response = new ResponseSearch();
		
		MessageManager messageManager = null;
		
		if(country != null && languague != null)
			messageManager = new MessageManager(languague,country);
		else
			messageManager = new MessageManager(LANGUAGUE,COUNTRY);
		
		try
		{
			StandardMessage message = new StandardMessage();
			
			festivitiesEjb = getFestivitiesManagerEjb();
			if(festivitiesEjb != null)
			{
				CurrentLocateDTO currentLocateDto = new CurrentLocateDTO(country,languague);
				RequestSearchDTO request = new RequestSearchDTO();
				request.setOperation("all");
				request.setCurrentLocateDto(currentLocateDto);
				
				ResponseSearchDTO responseEjb = festivitiesEjb.search(request);
				if(responseEjb.getMessage() != null && responseEjb.getMessage().getCode().equals("00"))
				{
					List<Festivity> festivities = null;
					
					festivities = parser.parseDTOToModelFestivity(responseEjb.getFestivities());
					
					response.setFestivities(festivities);
					
					status = Response.Status.OK;
				}
				else
					if(responseEjb.getMessage() != null && responseEjb.getMessage().getCode().equals("50"))
						status = Response.Status.NOT_FOUND;
				
				message = parser.parseDTOToModelStandardMessage(responseEjb.getMessage());
				response.setMessage(message);
			}
			else
			{
				status = Response.Status.INTERNAL_SERVER_ERROR;
				System.out.println("Hubo un error con la connexion con el ejb,verifique la correcta configuraciond el servidor");
				response.setMessage("100", messageManager.getApplicationMessage("MSG_100"));
			}
		}
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
			status = Response.Status.INTERNAL_SERVER_ERROR;
			response.setMessage("100", messageManager.getApplicationMessage("MSG_100"));
		}
		
		return generateResponse(false, status, response, producer); 
	}
	
	@POST @Path("/SearchByParam")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({"application/xml", "application/json"})
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public Response getSearchByParam(@Context HttpHeaders headers,RequestSearch request) throws JSONException  
	{
		Status status = null;
		FestivitiesManagerRemote festivitiesEjb = null;
		String headerAccept = getFormatAccept(headers);
		String producer = null;
		
		if(headerAccept != null)
			producer = getProducer(headerAccept);
		else
			return generateResponse(true, Status.INTERNAL_SERVER_ERROR, null, null);
		
		if(producer == null)
			return generateResponse(true, Status.INTERNAL_SERVER_ERROR, null, null);
		
		ResponseSearch response = new ResponseSearch();
		MessageManager messageManager = null;
		
		if(request.getCurrentLocation() == null)
			messageManager = new MessageManager(request.getCurrentLocation().getLanguague(), request.getCurrentLocation().getCountry());
		else
			messageManager = new MessageManager(LANGUAGUE,COUNTRY);
		
		try
		{
			StandardMessage message = new StandardMessage();
			
			festivitiesEjb = getFestivitiesManagerEjb();
			if(festivitiesEjb != null)
			{
				UtilidadesGlobales utilidadesGlobales = new UtilidadesGlobales();
				RequestSearchDTO requestdto = new RequestSearchDTO();
				CurrentLocateDTO currentLocateDto = parser.parseModelToDTO(request.getCurrentLocation());
				
				requestdto.setOperation(request.getType());
				requestdto.setCurrentLocateDto(currentLocateDto);
				
				FestivitiesDTO festivityDto = null;
				switch (request.getType()) {
				case "name":
					festivityDto = new FestivitiesDTO();
					festivityDto.setName(request.getValue());
					break;
				case "place":
					festivityDto = new FestivitiesDTO();
					festivityDto.setPlace(request.getValue());
					break;
				case "start":
					festivityDto = new FestivitiesDTO();
					festivityDto.setStart(utilidadesGlobales.StringUTCToDate(request.getValue()));
					break;
				case "range":
					festivityDto = new FestivitiesDTO();
					festivityDto.setStart(utilidadesGlobales.StringUTCToDate(request.getValue()));
					festivityDto.setEnd(utilidadesGlobales.StringUTCToDate(request.getValue2()));
					break;
				default:
					break;
				}
				
				if(festivityDto != null)
					requestdto.setFestivitie(festivityDto);
				
				ResponseSearchDTO responseEjb = festivitiesEjb.search(requestdto);
				if(responseEjb.getMessage() != null && responseEjb.getMessage().getCode().equals("00"))
				{
					List<Festivity> festivities = null;
					
					festivities = parser.parseDTOToModelFestivity(responseEjb.getFestivities());
					
					response.setFestivities(festivities);
					
					status = Response.Status.OK;
				}
				else
					if(responseEjb.getMessage() != null && responseEjb.getMessage().getCode().equals("50"))
						status = Response.Status.NOT_FOUND;
				
				message = parser.parseDTOToModelStandardMessage(responseEjb.getMessage());
				response.setMessage(message);
			}
			else
			{
				status = Response.Status.INTERNAL_SERVER_ERROR;
				System.out.println("Hubo un error con la connexion con el ejb,verifique la correcta configuraciond el servidor");
				response.setMessage("100", messageManager.getApplicationMessage("MSG_100"));
			}
		}
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
			status = Response.Status.INTERNAL_SERVER_ERROR;
			response.setMessage("100", messageManager.getApplicationMessage("MSG_100"));
		}
		
		return generateResponse(false, status, response, producer); 
	}
	
	@POST
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({"application/xml", "application/json"})
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public Response Crear(@Context HttpHeaders headers,RequestCreate request) throws JSONException  
	{
		Status status = null;
		FestivitiesManagerRemote festivitiesEjb = null;
		String headerAccept = getFormatAccept(headers);
		String producer = null;
		
		if(headerAccept != null)
			producer = getProducer(headerAccept);
		else
			return generateResponse(true, Status.INTERNAL_SERVER_ERROR, null, null);
		
		if(producer == null)
			return generateResponse(true, Status.INTERNAL_SERVER_ERROR, null, null);
		
		ResponseCreate response = new ResponseCreate();
		MessageManager messageManager = new MessageManager(request.getCurrentLocate().getLanguague(),request.getCurrentLocate().getCountry());
		
		try
		{
			StandardMessage message = new StandardMessage();
			
			festivitiesEjb = getFestivitiesManagerEjb();
			if(festivitiesEjb != null)
			{
				SpecificValidations validations = new SpecificValidations();
				
				FestivitiesDTO festivityDto = parser.parseModelToDTOFestivity(request.getFestivity());
				CurrentLocateDTO currentLocateDto = parser.parseModelToDTO(request.getCurrentLocate());
				
				boolean validationIsOk = validations.validateRangeDate(festivityDto.getStart(), festivityDto.getEnd());
				if(!validationIsOk)
				{
					status = Response.Status.INTERNAL_SERVER_ERROR;
					response.setMessage("60", messageManager.getApplicationMessage("MSG_60"));
					
					return generateResponse(false, status, response, producer); 
				}
				
				RequestCreateDTO requestCreate = new RequestCreateDTO();
				requestCreate.setFestivity(festivityDto);
				requestCreate.setCurrentLocateDto(currentLocateDto);
				
				ResponseCreateDTO responseDto = festivitiesEjb.create(requestCreate);
				
				message = parser.parseDTOToModelStandardMessage(responseDto.getMessage());
				response.setMessage(message);
				status = Response.Status.OK;
			}
			else
			{
				status = Response.Status.INTERNAL_SERVER_ERROR;
				System.out.println("Hubo un error con la connexion con el ejb,verifique la correcta configuraciond el servidor");
				response.setMessage("100", messageManager.getApplicationMessage("MSG_100"));
			}
		}
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
			status = Response.Status.INTERNAL_SERVER_ERROR;
			response.setMessage("100", messageManager.getApplicationMessage("MSG_100"));
		}
		
		return generateResponse(false, status, response, producer); 
	}
	
	@PUT
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({"application/xml", "application/json"})
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public Response Actualizar(@Context HttpHeaders headers,RequestUpdate request) throws JSONException  
	{
		Status status = null;
		FestivitiesManagerRemote festivitiesEjb = null;
		String headerAccept = getFormatAccept(headers);
		String producer = null;
		
		if(headerAccept != null)
			producer = getProducer(headerAccept);
		else
			return generateResponse(true, Status.INTERNAL_SERVER_ERROR, null, null);
		
		if(producer == null)
			return generateResponse(true, Status.INTERNAL_SERVER_ERROR, null, null);
		
		ResponseCreate response = new ResponseCreate();
		MessageManager messageManager = new MessageManager(request.getCurrentLocate().getLanguague(),request.getCurrentLocate().getCountry());
		
		try
		{
			StandardMessage message = new StandardMessage();
			
			festivitiesEjb = getFestivitiesManagerEjb();
			if(festivitiesEjb != null)
			{
				SpecificValidations validations = new SpecificValidations();
				
				FestivitiesDTO festivityDto = parser.parseModelToDTOFestivity(request.getFestivity());
				CurrentLocateDTO currentLocateDto = parser.parseModelToDTO(request.getCurrentLocate());
				
				boolean validationIsOk = validations.validateRangeDate(festivityDto.getStart(), festivityDto.getEnd());
				if(!validationIsOk)
				{
					status = Response.Status.INTERNAL_SERVER_ERROR;
					response.setMessage("60", messageManager.getApplicationMessage("MSG_60"));
					
					return generateResponse(false, status, response, producer); 
				}
				
				RequestUpdateDTO requesUpdate = new RequestUpdateDTO();
				requesUpdate.setFestivity(festivityDto);
				requesUpdate.setCurrentLocateDto(currentLocateDto);
				
				ResponseUpdateDTO responseDto = festivitiesEjb.update(requesUpdate);
				
				message = parser.parseDTOToModelStandardMessage(responseDto.getMessage());
				response.setMessage(message);
				status = Response.Status.OK;
			}
			else
			{
				status = Response.Status.INTERNAL_SERVER_ERROR;
				System.out.println("Hubo un error con la connexion con el ejb,verifique la correcta configuraciond el servidor");
				response.setMessage("100", messageManager.getApplicationMessage("MSG_100"));
			}
		}
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
			status = Response.Status.INTERNAL_SERVER_ERROR;
			response.setMessage("100", messageManager.getApplicationMessage("MSG_100"));
		}
		
		return generateResponse(false, status, response, producer); 
	}
	
	@GET @Path("/MessagesApp/{country}&{languague}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public Response getMessages(@Context HttpHeaders headers,@PathParam("country") String country,@PathParam("languague") String languague) throws JSONException  
	{
		Status status = null;
		String headerAccept = getFormatAccept(headers);
		String producer = null;
		
		if(headerAccept != null)
			producer = getProducer(headerAccept);
		else
			return generateResponse(true, Status.INTERNAL_SERVER_ERROR, null, null);
		
		if(producer == null)
			return generateResponse(true, Status.INTERNAL_SERVER_ERROR, null, null);
		
		ResponseMessagesWindow response = new ResponseMessagesWindow();
		
		MessageManager messageManager = null;
		
		if(country != null && languague != null)
			messageManager = new MessageManager(languague,country);
		else
			messageManager = new MessageManager(LANGUAGUE,COUNTRY);
		
		try
		{
			List<String> variables = messageManager.getAllByPattern("WIN");
			for(String string : variables)
			{
				response.setMessagesWindow(new MessagesWindow(string, messageManager.getApplicationMessage(string)));
			}
			
			status = Response.Status.OK;
		}
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
			status = Response.Status.INTERNAL_SERVER_ERROR;
		}
		
		return generateResponse(false, status, response, producer); 
	}
}
