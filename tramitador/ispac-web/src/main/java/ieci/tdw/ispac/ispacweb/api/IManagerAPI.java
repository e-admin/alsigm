/*
 * Created on 16-feb-2005
 *
 */
package ieci.tdw.ispac.ispacweb.api;

import ieci.tdw.ispac.api.errors.ISPACException;

import java.util.Map;

/**
 * @author juanin
 *
 * Gestiona los estados de tramitación y proporciona acceso al resto de interfaces.
 * Todos los interfaces de tramitación incluido IManagerAPI intentan obtener toda la
 * información posible a partir del estado {@link ieci.tdw.ispac.ispacweb.api.IState}
 * en que se encuentre.
 *
 */
public interface IManagerAPI
{
	/**
	 * Obtiene el esquema de tramitación de un expediente.
	 * @return IScheme el esquema
	 */
	public abstract IScheme getSchemeAPI();

	/**
	 * Obtiene las posibles acciones a realizar en la tramitación de un expediente.
	 * @return Actions Acciones a realizar
	 */
	public IActions getActionsAPI();


	/**
	 * Obtiene la lista de trabajo dependiendo del estado de tramitación.
	 * @return IScheme el esquema
	 */
	public IWorklist getWorklistAPI();

	/**
	 * Transiciona a un nuevo estado de tramitación.
	 * @param stateticket ticket del estado actual
	 * @param newstateID identificador del nuevo estado
	 * @param params parámetros necesarios para la creación del estado
	 * @return IState
	 * @throws ISPACException
	 */
	public IState enterState(String stateticket, int newstateID, Map params) throws ISPACException;

	/**
	 * Transiciona a un nuevo estado de tramitación.
	 * @param stateticket ticket del estado actual
	 * @param newstateID identificador del nuevo estado
	 * @param params parámetros necesarios para la creación del estado
	 * @return IState
	 * @throws ISPACException
	 */
	//public IState enterState(IState currentState, int newstateID, Map params) throws ISPACException;

	/**
	 * Obtiene el estado en el que se encuentra la tramitación.
	 * @param stateticket ticket del estado
	 * @return IState
	 * @throws ISPACException
	 */
	public IState currentState(String stateticket) throws ISPACException;

	/**
	 * Crea un nuevo estado de tramitación pero no entra en el mismo ya que no llama al método
	 * IState.enter()
	 * @param stateticket ticket del estado actual
	 * @param newstateID identificador del nuevo estado
	 * @param params parámetros necesarios para la creación del estado
	 * @return IState
	 * @throws ISPACException
	 */
	public IState newState(int newstateID, Map params) throws ISPACException;

}