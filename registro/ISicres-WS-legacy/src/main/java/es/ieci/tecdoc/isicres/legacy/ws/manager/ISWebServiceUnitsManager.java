package es.ieci.tecdoc.isicres.legacy.ws.manager;

import es.ieci.tecdoc.isicres.ws.legacy.service.units.WSUnit;
import es.ieci.tecdoc.isicres.ws.legacy.service.units.WSUnitTypesResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.units.WSUnitsResponse;

public interface ISWebServiceUnitsManager {

	/**
	 * Metodo que obtiene el tipo de Unidades administrativas
	 * @param initValue
	 * @param size
	 * @param security
	 * @return {@link WSUnitTypesResponse}
	 */
	public WSUnitTypesResponse loadUnitTypes(int initValue, int size,
			es.ieci.tecdoc.isicres.ws.legacy.service.units.Security security);

	/**
	 * Metodo que recupera las Unidades Administrativas segun una condicion SQL
	 * @param condition
	 * @param initValue
	 * @param size
	 * @param security
	 * @return {@link WSUnitsResponse}
	 */
	public WSUnitsResponse loadUnitsFromCondition(String condition,
			int initValue, int size,
			es.ieci.tecdoc.isicres.ws.legacy.service.units.Security security);

	/**
	 * Metodo que permite obtener una coleccion de unidades administrativas de
	 * nivel jerarquico superior pertenecientes a un tipo de institucion
	 * determinado
	 * 
	 * @param typeCode
	 * @param initValue
	 * @param size
	 * @param security
	 * @return {@link WSUnitsResponse}
	 */
	public WSUnitsResponse loadUnitsFromType(String typeCode, int initValue,
			int size,
			es.ieci.tecdoc.isicres.ws.legacy.service.units.Security security);

	/**
	 * Metodo que permite obtener una coleccion de unidades administrativas de
	 * nivel jerarquico inmediatamente inferior a una unidad dada.
	 * 
	 * @param unitCode
	 * @param initValue
	 * @param size
	 * @param security
	 * @return {@link WSUnitsResponse}
	 */
	public WSUnitsResponse loadUnitsFromUnit(String unitCode, int initValue,
			int size,
			es.ieci.tecdoc.isicres.ws.legacy.service.units.Security security);
	
	/**
	 * Metodo que permite validar la existencia de un codigo de unidad
	 * administrativa
	 * 
	 * @param unitCode
	 * @param security
	 * @return {@link WSUnit}
	 */
	public WSUnit validateUnitCode(String unitCode,
			es.ieci.tecdoc.isicres.ws.legacy.service.units.Security security);
}
