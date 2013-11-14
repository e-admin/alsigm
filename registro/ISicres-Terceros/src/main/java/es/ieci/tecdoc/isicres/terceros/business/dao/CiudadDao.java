package es.ieci.tecdoc.isicres.terceros.business.dao;

import java.util.List;

import es.ieci.tecdoc.fwktd.server.dao.BaseReadOnlyDao;
import es.ieci.tecdoc.isicres.terceros.business.vo.CiudadVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.ProvinciaVO;

/**
 *
 */
public interface CiudadDao extends BaseReadOnlyDao<CiudadVO, String> {

	public List<CiudadVO> getCiudades(int from, int to);

	public Integer getCiudadesCount();


	public CiudadVO findByCodigo(String codigo);

	public CiudadVO findByNombre(String nombre);


	public Integer getCiudadesByProvinciaCount(ProvinciaVO provincia);
	/**
	 * Devuelve las ciudades de la provincia <code>provincia</code>.
	 *
	 * @param provincia
	 * @return
	 */
	public List<CiudadVO> getCiudadesByProvincia(ProvinciaVO provincia);


	public List<CiudadVO> getCiudadesByProvincia(ProvinciaVO provincia, int from, int to);
}
