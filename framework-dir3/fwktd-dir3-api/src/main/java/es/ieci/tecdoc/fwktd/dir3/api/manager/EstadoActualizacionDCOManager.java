package es.ieci.tecdoc.fwktd.dir3.api.manager;

import es.ieci.tecdoc.fwktd.dir3.api.vo.EstadoActualizacionDcoVO;
import es.ieci.tecdoc.fwktd.server.manager.BaseManager;

public interface EstadoActualizacionDCOManager extends BaseManager<EstadoActualizacionDcoVO, String> {

	/**
	 *
	 * Obtiene la información de la última actualización correcta
	 *
	 * @return EstadoActualizacionDcoVO
	 */
	public EstadoActualizacionDcoVO getLastSuccessUpdate();

}
