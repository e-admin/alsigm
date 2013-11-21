package es.ieci.tecdoc.isicres.api.business.manager;

import java.util.List;
import java.util.Locale;

import es.ieci.tecdoc.isicres.api.business.vo.OficinaVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */

public abstract class OficinaManager {

	/**
	 * Obtiene una Oficina segun su codigo
	 * @param usuario
	 * @param codigoOficina
	 * @return
	 */
	public abstract OficinaVO getOficinaByCodigo(UsuarioVO usuario,String codigoOficina);

	/**
	 * Obtiene las oficinas a las que está asociado un usuario
	 *
	 * @param usuario
	 * @return
	 */
	public abstract List<OficinaVO> findOficinasByUsuario(UsuarioVO usuario);

	/**
	 * TODO ¿Oficinas administradas?
	 * @param usuario
	 * @return
	 */
	public abstract List<OficinaVO> findOficinasAdministradasByUsuario(UsuarioVO usuario);

	/**
	 * Devuelve todas las oficinas
	 *
	 * @return
	 */
	public abstract List<OficinaVO> getOficinas(Locale locale);

	/**
	 * Metodo que obtiene una oficina por su id
	 *
	 * @param locale
	 * @param idOficina
	 * @return
	 */
	public abstract OficinaVO getOficinaById(Locale locale, String idOficina);

}
