package es.ieci.tecdoc.fwktd.csv.api.dao.impl;

import es.ieci.tecdoc.fwktd.csv.api.dao.AplicacionDao;
import es.ieci.tecdoc.fwktd.csv.api.vo.AplicacionVO;
import es.ieci.tecdoc.fwktd.server.dao.ibatis.IbatisGenericDaoImpl;

/**
 * DAO de aplicaciones.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class AplicacionDaoImpl extends
		IbatisGenericDaoImpl<AplicacionVO, String> implements AplicacionDao {

	protected static final String GET_APLICACION_BY_CODIGO = "AplicacionVO.getAplicacionVOByCodigo";
	protected static final String DELETE_APLICACION_BY_CODIGO = "AplicacionVO.deleteAplicacionVOByCodigo";

	/**
	 * Constructor con parámetros de la clase. Establece el tipo de entidad a
	 * persistir/recuperar.
	 *
	 * @param aPersistentClass
	 *            tipo de objeto a persistir/recuperar
	 */
	public AplicacionDaoImpl(Class<AplicacionVO> aPersistentClass) {
		super(aPersistentClass);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.server.dao.ibatis.IbatisGenericReadOnlyDaoImpl#get(java.io.Serializable)
	 */
	public AplicacionVO get(String id) {

		logger.info("Obteniendo la aplicación [{}]", id);

		return (AplicacionVO) getSqlMapClientTemplate().queryForObject(
				getFindQuery(getPersistentClass()), id);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see es.ieci.tecdoc.fwktd.csv.api.dao.AplicacionDao#getAplicacionByCodigo(java.lang.String)
	 */
	public AplicacionVO getAplicacionByCodigo(String codigo) {

		logger.info("Obteniendo la aplicación a partir del código [{}]", codigo);

		return (AplicacionVO) getSqlMapClientTemplate().queryForObject(
				GET_APLICACION_BY_CODIGO, codigo);
	}

	public void deleteAplicacionByCodigo(String codigo) {

		logger.info("Eliminando la aplicación a partir del código [{}]", codigo);

		getSqlMapClientTemplate().delete(DELETE_APLICACION_BY_CODIGO, codigo);
	}

}
