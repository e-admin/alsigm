/**
 *
 */
package es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.impl;

import es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao.DireccionesIntercambioRegistralDAO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.DireccionesIntercambioRegistralManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.CiudadExReg;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.PaisExReg;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.ProvinciaExReg;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */
public class DireccionesIntercambioRegistralManagerImpl implements DireccionesIntercambioRegistralManager {



	protected DireccionesIntercambioRegistralDAO direccionesIntercambioRegistralDAO;

	/**
	 * Código del país. Atributo según catálogo del INE
	 */
	protected final static String CODIGO_PAIS_SPAIN = "724";

	/**
	 * Nombre del país España
	 */
	private static final String NOMBRE_PAIS_SPAIN = "España";

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.DireccionesIntercambioRegistralManager#getProvinciaExRegByCodigo(java.lang.String)
	 */
	public ProvinciaExReg getProvinciaExRegByCodigo(String codigo) {

		return getDireccionesIntercambioRegistralDAO().getProvinciaExRegByCodigo(codigo);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.DireccionesIntercambioRegistralManager#getCiudadExRegByCodigo(java.lang.String)
	 */
	public CiudadExReg getCiudadExRegByCodigo(String codigoProvincia,
			String codigoMunicipio) {
		return getDireccionesIntercambioRegistralDAO().getCiudadExRegByCodigo(codigoProvincia,
				codigoMunicipio);
	}

	/**
	 *
	 * En esta implementación, el código del país es estático. No hay BBDD donde
	 * se almacena la relación entre los códigos y los nombres.
	 *
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.DireccionesIntercambioRegistralManager#getPaisExRegByCodigo(java.lang.String)
	 */
	public PaisExReg getPaisExRegByCodigo(String codigo) {
		PaisExReg pais = null;

		pais = getDireccionesIntercambioRegistralDAO().getPaisExRegByCodigo(codigo);

		return pais;
	}

	public DireccionesIntercambioRegistralDAO getDireccionesIntercambioRegistralDAO() {
		return direccionesIntercambioRegistralDAO;
	}

	public void setDireccionesIntercambioRegistralDAO(DireccionesIntercambioRegistralDAO direccionesExRegDAO) {
		this.direccionesIntercambioRegistralDAO = direccionesExRegDAO;
	}

}
