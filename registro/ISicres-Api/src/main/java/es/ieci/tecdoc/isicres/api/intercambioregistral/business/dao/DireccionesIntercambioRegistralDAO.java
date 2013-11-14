/**
 *
 */
package es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao;

import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.CiudadExReg;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.PaisExReg;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.ProvinciaExReg;

/**
 *
 * DAO para gestionar las provincias y municipios que se mapean al hacer
 * intercambios registrales.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public interface DireccionesIntercambioRegistralDAO {

	/**
	 * Devuelve una provincia a partir del código que se utiliza en el
	 * intercambio registral.
	 *
	 * @param codigo
	 * @return ProvinciaDCO
	 */
	public ProvinciaExReg getProvinciaExRegByCodigo(String codigo);

	/**
	 * Devuelve una ciudad a partir del código que se utiliza en el intercambio
	 * registral.
	 *
	 * @param codigoProvincia
	 * @param codigoMunicipio
	 * @return CiudadDCO
	 */
	public CiudadExReg getCiudadExRegByCodigo(String codigoProvincia,
			String codigoMunicipio);

	/**
	 * Devuelve una ciudad a partir del código del país que se utiliza en el intercambio registral.
	 *
	 * @param codigo
	 * @return Pais
	 */
	public PaisExReg getPaisExRegByCodigo(String codigo);

}
