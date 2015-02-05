package es.ieci.tecdoc.fwktd.dir3.api.manager.impl;

import es.ieci.tecdoc.fwktd.dir3.api.dao.EstadoActualizacionDcoDAO;
import es.ieci.tecdoc.fwktd.dir3.api.manager.EstadoActualizacionDCOManager;
import es.ieci.tecdoc.fwktd.dir3.api.vo.EstadoActualizacionDcoVO;
import es.ieci.tecdoc.fwktd.server.dao.BaseDao;
import es.ieci.tecdoc.fwktd.server.manager.impl.BaseManagerImpl;

public class EstadoActualizacionDCOManagerImpl extends
		BaseManagerImpl<EstadoActualizacionDcoVO, String> implements EstadoActualizacionDCOManager {


	public EstadoActualizacionDCOManagerImpl(
			BaseDao<EstadoActualizacionDcoVO, String> aGenericDao) {
		super(aGenericDao);
	}

	/*
	 * (non-Javadoc)
	 * @see es.ieci.tecdoc.fwktd.dir3.api.manager.EstadoActualizacionDCOManager#getLastSuccessUpdate()
	 */
	public EstadoActualizacionDcoVO getLastSuccessUpdate() {
		return ((EstadoActualizacionDcoDAO) getDao()).getLastSuccessUpdate();
	}
}
