package com.prodigious.festivitiesapp.ejb;

import javax.ejb.Remote;

import com.prodigious.festivitiesapp.dto.RequestCreateDTO;
import com.prodigious.festivitiesapp.dto.RequestSearchDTO;
import com.prodigious.festivitiesapp.dto.RequestUpdateDTO;
import com.prodigious.festivitiesapp.dto.ResponseCreateDTO;
import com.prodigious.festivitiesapp.dto.ResponseSearchDTO;
import com.prodigious.festivitiesapp.dto.ResponseUpdateDTO;
import com.prodigious.festivitiesapp.exceptions.ErroresException;

@Remote
public interface FestivitiesManagerRemote 
{
	/**
	 * Metodo usado para realziar la busqueda de un registro en la base de datos
	 * @param request recibe unos parametros para filtrar los registros 
	 * @return retorna una lista de registros y un mensaje del resultado de la busqueda
	 * @throws ErroresException excepcion genrica del aplicativo
	 */
	public ResponseSearchDTO search(RequestSearchDTO request) throws ErroresException;
	/**
	 * MEtodo usado para crear una festividad en la base de datos
	 * @param request:Objeto con la festividad a ser insertada
	 * @returnretorna la informacion del estado del insert.
	 * @throws ErroresException excepcion genrica del aplicativo
	 */
	public ResponseCreateDTO create(RequestCreateDTO request) throws ErroresException;
	/**
	 * Metodo usado para actualizar una festividad en la base de datos
	 * @param request: obejto con la festividad actualizar
	 * @return informacion del estatus del update.
	 * @throws ErroresException excepcion genrica del aplicativo
	 */
	/**
	 */
	public ResponseUpdateDTO update(RequestUpdateDTO request) throws ErroresException;
}
