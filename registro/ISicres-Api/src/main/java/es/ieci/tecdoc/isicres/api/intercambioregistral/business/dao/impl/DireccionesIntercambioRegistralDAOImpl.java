/**
 *
 */
package es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.ibatis.sqlmap.client.SqlMapClient;

import es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao.DireccionesIntercambioRegistralDAO;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.CiudadExReg;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.PaisExReg;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.vo.ProvinciaExReg;

/**
 *
 * Implementacion Ibatis del DAO para gestionar los países, las provincias y municipios que se
 * mapean al hacer intercambios registrales.
 *
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class DireccionesIntercambioRegistralDAOImpl implements DireccionesIntercambioRegistralDAO {

	private static final String GET_CIUDAD_EXREG_BY_CODIGO_SQLMAP = "DireccionesExReg.getCiudadExRegByCodigo";
	private static final String GET_PROVINCIA_EXREG_BY_CODIGO_SQLMAP = "DireccionesExReg.getProvinciaExRegByCodigo";
	private static final String GET_PAIS_EXREG_BY_CODIGO_SQLMAP= "DireccionesExReg.getPaisExRegByCodigo";

	private static final Logger logger = Logger
			.getLogger(DireccionesIntercambioRegistralDAOImpl.class);

	protected SqlMapClientTemplate sqlMapClientTemplate = new SqlMapClientTemplate();

	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao.DireccionesIntercambioRegistralDAO#getProvinciaExRegByCodigo(java.lang.String)
	 */
	public ProvinciaExReg getProvinciaExRegByCodigo(String codigo) {
		if (logger.isDebugEnabled()) {
			logger.debug("getProvinciaDCOByCodigo(String) - start - Codigo: ["
					+ codigo + "]");
		}

		ProvinciaExReg provincia = (ProvinciaExReg) getSqlMapClientTemplate()
				.queryForObject(GET_PROVINCIA_EXREG_BY_CODIGO_SQLMAP, codigo);

		if (logger.isDebugEnabled()) {
			logger.debug("getProvinciaDCOByCodigo(String) - end");
		}
		return provincia;
	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao.DireccionesIntercambioRegistralDAO#getCiudadExRegByCodigo(java.lang.String,
	 *      java.lang.String)
	 */
	public CiudadExReg getCiudadExRegByCodigo(String codigoProvincia,
			String codigoMunicipio) {
		if (logger.isDebugEnabled()) {
			logger.debug("getCiudadDCOByCodigo(String) - start - Codigo Provincia: ["
					+ codigoProvincia + "] Código Municipio: ["+codigoMunicipio+"]");
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put("codigoProvincia", codigoProvincia);
		map.put("codigoMunicipio", codigoMunicipio);
		CiudadExReg ciudadDCO = (CiudadExReg) getSqlMapClientTemplate()
				.queryForObject(GET_CIUDAD_EXREG_BY_CODIGO_SQLMAP, map);

		if (logger.isDebugEnabled()) {
			logger.debug("getCiudadDCOByCodigo(String) - end");
		}
		return ciudadDCO;
	}



	/**
	 *
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.isicres.api.intercambioregistral.business.dao.DireccionesIntercambioRegistralDAO#getPaisExRegByCodigo(java.lang.String)
	 */
	public PaisExReg getPaisExRegByCodigo(String codigo) {
		if (logger.isDebugEnabled()) {
			logger.debug("getPaisExRegByCodigo(String) - start - Codigo: ["+codigo+"]");
		}

		PaisExReg pais = (PaisExReg) getSqlMapClientTemplate().queryForObject(GET_PAIS_EXREG_BY_CODIGO_SQLMAP, codigo);

		if (logger.isDebugEnabled()) {
			logger.debug("getPaisExRegByCodigo(String) - end");
		}
		return pais;
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(
			SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	public final void setSqlMapClient(SqlMapClient aSqlMapClient) {
		this.sqlMapClientTemplate.setSqlMapClient(aSqlMapClient);
	}

}
