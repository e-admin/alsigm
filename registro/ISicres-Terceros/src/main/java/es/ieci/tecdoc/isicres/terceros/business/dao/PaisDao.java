package es.ieci.tecdoc.isicres.terceros.business.dao;

import es.ieci.tecdoc.fwktd.server.dao.BaseReadOnlyDao;
import es.ieci.tecdoc.isicres.terceros.business.vo.PaisVO;

/**
 *
 * @author IECISA
 *
 */
public interface PaisDao extends BaseReadOnlyDao<PaisVO, String> {

	public PaisVO findByCodigo(String codigo);

	public PaisVO findByNombre(String nombre);

}
