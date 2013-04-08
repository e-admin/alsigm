package es.ieci.tecdoc.isicres.terceros.business.manager;

import java.util.List;

import es.ieci.tecdoc.fwktd.server.manager.BaseManager;
import es.ieci.tecdoc.isicres.terceros.business.vo.InteresadoVO;

/**
 *
 * @author IECISA
 *
 */
public interface InteresadoManager extends BaseManager<InteresadoVO, String> {

	/**
	 * Devuelve todos los interesados asociados a la combinación
	 * idLibro+idRegistro que se recibe por parámetro.
	 *
	 * @param idLibro
	 * @param idRegitro
	 * @return
	 */
	public List<InteresadoVO> getAll(String idLibro, String idRegistro);

	/**
	 * Elimina todos los interesados existentes para la combinación
	 * idLibro+idRegistro que recibe por parámetro.
	 *
	 * @param idLibro
	 * @param idRegistro
	 */
	public abstract void deleteAll(String idLibro, String idRegistro);

}
