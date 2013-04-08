package es.ieci.tecdoc.fwktd.dir3.api.dao.impl;

import es.ieci.tecdoc.fwktd.dir3.api.dao.EstadoActualizacionDcoDAO;
import es.ieci.tecdoc.fwktd.dir3.api.vo.EstadoActualizacionDcoVO;
import es.ieci.tecdoc.fwktd.server.dao.ibatis.IbatisGenericDaoImpl;

public class EstadoActualizacionDcoDaoImpl extends
		IbatisGenericDaoImpl<EstadoActualizacionDcoVO, String> implements EstadoActualizacionDcoDAO {

	protected static final String GET_LAST_SUCCESS_UPDATE = "EstadoActualizacionDcoVO.getLastSuccessUpdate";

	public EstadoActualizacionDcoDaoImpl(
			Class<EstadoActualizacionDcoVO> aPersistentClass) {
		super(aPersistentClass);

	}

	/*
	 * (non-Javadoc)
	 * @see es.ieci.tecdoc.fwktd.dir3.api.dao.EstadoActualizacionDcoDAO#getLastSuccessUpdate()
	 */
	public EstadoActualizacionDcoVO getLastSuccessUpdate() {
		return (EstadoActualizacionDcoVO)getSqlMapClientTemplate().queryForObject(GET_LAST_SUCCESS_UPDATE);
	}

}
