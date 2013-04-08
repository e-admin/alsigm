package es.ieci.tecdoc.fwktd.sir.api.dao.impl;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.IbatisGenericDaoImpl;
import es.ieci.tecdoc.fwktd.sir.api.dao.InteresadoDao;
import es.ieci.tecdoc.fwktd.sir.core.vo.InteresadoVO;

/**
 * DAO de interesados de asientos registrales.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class InteresadoDaoImpl extends
		IbatisGenericDaoImpl<InteresadoVO, String> implements
		InteresadoDao {

	protected static final String DELETE_INTERESADOS_BY_ID_ASIENTO_REGISTRAL = "InteresadoVO.deleteInteresadosByIdAsientoRegistral";

	/**
	 * Constructor con parámetros de la clase. Establece el tipo de entidad a
	 * persistir/recuperar.
	 *
	 * @param aPersistentClass
	 *            tipo de objeto a persistir/recuperar
	 */
	public InteresadoDaoImpl(Class<InteresadoVO> aPersistentClass) {
		super(aPersistentClass);
	}

	/**
	 * {@inheritDoc}
	 * @see es.ieci.tecdoc.fwktd.sir.api.dao.InteresadoDao#deleteByIdAsientoRegistral(java.lang.String)
	 */
	public void deleteByIdAsientoRegistral(String idAsientoRegistral) {

		logger.info("Eliminando interesados del asiento registral con identificador [{}]", idAsientoRegistral);

		getSqlMapClientTemplate().delete(DELETE_INTERESADOS_BY_ID_ASIENTO_REGISTRAL, idAsientoRegistral);
	}

}
