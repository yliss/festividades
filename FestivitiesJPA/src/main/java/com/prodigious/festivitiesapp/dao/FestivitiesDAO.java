package com.prodigious.festivitiesapp.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.exception.ConstraintViolationException;

import com.prodigious.festivitiesapp.entities.Festivitie;

/**
 * 
 * @author YanithLisset
 *
 */
public class FestivitiesDAO 
{
	@SuppressWarnings("unchecked")
	public List<Festivitie> getAll(EntityManager entityManager)
	{
		Query queryGetByName = entityManager.createNamedQuery("Festivitie.findAll");
		
		return queryGetByName.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Festivitie> getByName(String name,EntityManager entityManager)
	{
		Query queryGetByName = entityManager.createNamedQuery("Festivitie.getByName");
		queryGetByName.setParameter("name", name);
		
		return queryGetByName.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Festivitie> getByPlace(String place,EntityManager entityManager)
	{
		Query queryGetByPlace = entityManager.createNamedQuery("Festivitie.getByPlace");
		
		queryGetByPlace.setParameter("place", place);
		
		return queryGetByPlace.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Festivitie> getByStartDate(Date startDate,EntityManager entityManager)
	{
		Query queryGetByPlace = entityManager.createNamedQuery("Festivitie.getByStartDate");
		
		queryGetByPlace.setParameter("start", startDate);
		
		return queryGetByPlace.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Festivitie> getByDateRange(Date startDate,Date endDate,EntityManager entityManager)
	{
		Query queryGetByPlace = entityManager.createNamedQuery("Festivitie.getByRange");
		
		queryGetByPlace.setParameter("startDate", startDate);
		queryGetByPlace.setParameter("endDate", endDate);
		
		return queryGetByPlace.getResultList();
	}
	
	public boolean persist(Festivitie entity,EntityManager entityManager) throws ConstraintViolationException,PersistenceException,Exception
	{
		boolean respuesta = false;
		
		try 
		{
			entityManager.persist(entity);
			
			entityManager.flush();
			
			respuesta = true;
		}
		catch(ConstraintViolationException e)
		{	
			respuesta = false;
			throw e;
		}
		catch(PersistenceException e)
		{	
			respuesta = false;
			throw e;
		}
		catch (Exception e) 
		{	
			respuesta = false;
		}
		
		return respuesta;
	}

	public boolean merge(Festivitie entity,EntityManager entityManager)  throws ConstraintViolationException,PersistenceException,Exception{		
		boolean respuesta = false;
		
		try 
		{
			entityManager.merge(entity);
			
			respuesta = true;
		}
		catch(ConstraintViolationException e)
		{
			respuesta = false;
			throw e;
		}
		catch(PersistenceException e)
		{
			respuesta = false;
			throw e;
		}
		catch (Exception e) 
		{
			respuesta = false;
		}
		
		return respuesta;
	}
	
	public boolean remove(Festivitie entity,EntityManager entityManager)  throws ConstraintViolationException,PersistenceException,Exception{
		boolean respuesta = false;
		
		try 
		{
			entityManager.remove(entityManager.merge(entity));

			respuesta = true;
		}
		catch(ConstraintViolationException e){
			respuesta = false;
			throw e;
		}
		catch(PersistenceException e)
		{
			respuesta = false;
			throw e;
		}
		catch (Exception e) 
		{
			respuesta = false;
		}
		return respuesta;
	}

	@SuppressWarnings("unchecked")
	public <K, E> E findById(K id,EntityManager entityManager)  throws ConstraintViolationException,PersistenceException,Exception
	{
		E object = null;
		try
		{
			object = (E) entityManager.find(Festivitie.class, id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return object; 
	}
}
