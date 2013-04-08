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
	
	public abstract List findOficinasByUsuario(UsuarioVO usuario);
	
	public abstract List findOficinasAdministradasByUsuario(UsuarioVO usuario);
	
	/**
	 * Metodo que obtiene una oficina por su id
	 * @param locale
	 * @param idOficina
	 * @return
	 */
	public abstract OficinaVO getOficinaById(Locale locale, String idOficina);

}
