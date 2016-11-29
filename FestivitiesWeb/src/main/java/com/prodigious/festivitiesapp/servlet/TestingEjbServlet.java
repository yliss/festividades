package com.prodigious.festivitiesapp.servlet;

import java.io.IOException;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.prodigious.festivitiesapp.dto.FestivitiesDTO;
import com.prodigious.festivitiesapp.dto.RequestSearchDTO;
import com.prodigious.festivitiesapp.dto.ResponseSearchDTO;
import com.prodigious.festivitiesapp.ejb.FestivitiesManagerRemote;

/*
import com.prodigious.festivitiesapp.dto.FestivitiesDTO;
import com.prodigious.festivitiesapp.dto.RequestSearchDTO;
import com.prodigious.festivitiesapp.dto.ResponseSearchDTO;
import com.prodigious.festivitiesapp.ejb.FestivitiesManagerRemote;
*/

/**
 * Servlet implementation class TestingEjbServlet
 */
@WebServlet("/TestingEjbServlet")
public class TestingEjbServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestingEjbServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			InitialContext initialContext = new InitialContext();
			//String valorDeBusqueda = "java:global/FestivitiesEJB/FestivitiesEJB";
			//String valorDeBusqueda = "ejb/FestivitiesEJB";
			//String valorDeBusqueda = "java:module/FestivitiesEJB";
			//String valorDeBusqueda = "java:module/FestivitiesWebApp/FestivitiesEJB";
			//String valorDeBusqueda = "java:module/FestivitiesWebApp.FestivitiesEJB";
			//String valorDeBusqueda = "java:global/FestivitiesEJB/FestivitiesManager";
			//String valorDeBusqueda = "java:module/FestivitiesEJB/FestivitiesManager";
			//String valorDeBusqueda = "java:app/FestivitiesEE-0.0.1-SNAPSHOT/FestivitiesManager!com.prodigious.festivitiesapp.ejb.FestivitiesManager";
			//String valorDeBusqueda = "java:module/FestivitiesManager!com.prodigious.festivitiesapp.ejb.FestivitiesManager";
			//String valorDeBusqueda = "java:global/FestivitiesEAR-0.0.1-SNAPSHOT/FestivitiesEE-0.0.1-SNAPSHOT/FestivitiesManager!com.prodigious.festivitiesapp.ejb.FestivitiesManager";
			//String valorDeBusqueda = "java:module/FestivitiesManager!com.prodigious.festivitiesapp.ejb.FestivitiesManagerRemote";
			String valorDeBusqueda = "java:app/FestivitiesEE-0.0.1-SNAPSHOT/FestivitiesManager!com.prodigious.festivitiesapp.ejb.FestivitiesManagerRemote";//OK
			System.out.println("EJB buscado," + valorDeBusqueda);
			Object object = initialContext.lookup(valorDeBusqueda);
			FestivitiesManagerRemote festivitiesManager = (FestivitiesManagerRemote) object;
			
			RequestSearchDTO requestSearch = new RequestSearchDTO();
			requestSearch.setOperation("all");
			
			ResponseSearchDTO resonse = festivitiesManager.search(requestSearch);
			System.out.println(resonse.getMessage().getCode());
			System.out.println(resonse.getMessage().getMessage());
			for(FestivitiesDTO festivities : resonse.getFestivities())
			{
				System.out.println(festivities.getName());
				System.out.println(festivities.getPlace());
				System.out.println(festivities.getStart());
				System.out.println(festivities.getEnd());
			}
		}
		catch(Exception e)
		{
			System.out.println("Error en el seervlet," + e.getMessage());
		}
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
