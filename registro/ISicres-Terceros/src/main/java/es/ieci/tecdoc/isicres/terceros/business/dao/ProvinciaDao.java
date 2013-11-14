package es.ieci.tecdoc.isicres.terceros.business.dao;

import java.util.List;

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

	public List<ProvinciaVO> getProvincias(int from, int to);

	public Integer getProvinciasCount();

}
