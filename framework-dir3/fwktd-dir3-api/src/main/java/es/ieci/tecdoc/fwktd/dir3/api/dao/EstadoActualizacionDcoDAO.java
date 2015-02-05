package es.ieci.tecdoc.fwktd.dir3.api.dao;

import es.ieci.tecdoc.fwktd.dir3.api.vo.EstadoActualizacionDcoVO;
import es.ieci.tecdoc.fwktd.server.dao.BaseDao;

public interface EstadoActualizacionDcoDAO extends
		BaseDao<EstadoActualizacionDcoVO, String> {

	/**
	 *
	 * Obtiene la información con la fecha de la última actualización correcta
	 *
	 * @return EstadoActualizacionDcoVO
	 */
	public EstadoActualizacionDcoVO getLastSuccessUpdate();
}
