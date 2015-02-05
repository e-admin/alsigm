package es.ieci.tecdoc.isicres.terceros.business.dao;

import es.ieci.tecdoc.fwktd.server.dao.BaseReadOnlyDao;
import es.ieci.tecdoc.isicres.terceros.business.vo.ProvinciaVO;

/**
 *
 * @author IECISA
 *
 */
public interface ProvinciaDao extends BaseReadOnlyDao<ProvinciaVO, String> {

	public ProvinciaVO findByCodigo(String codigo);

	public ProvinciaVO findByNombre(String nombre);

}
