package es.ieci.tecdoc.isicres.legacy.ws.manager;

import es.ieci.tecdoc.isicres.ws.legacy.service.matters.Security;
import es.ieci.tecdoc.isicres.ws.legacy.service.matters.WSMatterType;
import es.ieci.tecdoc.isicres.ws.legacy.service.matters.WSMatterTypesResponse;

public interface ISWebServiceMattersManager {

	/**
	 * Metodo que obtiene un listado de Tipos de Asunto validos para el usuario
	 * @param initValue
	 * @param size
	 * @param security
	 * @return {@link WSMatterTypesResponse}
	 */
	public WSMatterTypesResponse loadMatterTypes(int initValue, int size,
			Security security);
	
	/**
	 * Metodo que obtiene un listado de Tipos de Asunto segun una condicion
	 * @param condition
	 * @param initValue
	 * @param size
	 * @param security
	 * @return {@link WSMatterTypesResponse}
	 */
	public WSMatterTypesResponse matterTypesFromCondition(String condition,
			int initValue, int size, Security security);
	
	/**
	 * Metodo que valida un Tipo de Asunto segun el codigo del mismo
	 * @param matterTypeCode
	 * @param security
	 * @return {@link WSMatterType}
	 */
	public WSMatterType validateMatterTypeCode(String matterTypeCode,
			Security security);
}
