package es.ieci.tecdoc.isicres.api.business.manager.impl;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.ISicresAPerms;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

import es.ieci.tecdoc.isicres.api.business.manager.ContextoAplicacionManager;
import es.ieci.tecdoc.isicres.api.business.manager.ContextoAplicacionManagerFactory;
import es.ieci.tecdoc.isicres.api.business.manager.LibroManager;
import es.ieci.tecdoc.isicres.api.business.manager.PermisosManager;
import es.ieci.tecdoc.isicres.api.business.vo.BaseFiltroRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.BaseRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.OficinaVO;
import es.ieci.tecdoc.isicres.api.business.vo.PermisosAplicacionVO;
import es.ieci.tecdoc.isicres.api.business.vo.PermisosLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.PermisosRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.PermisosUsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import gnu.trove.THashMap;

public class PermisosManagerImpl extends PermisosManager {

	public PermisosLibroVO getPermisosLibro(BaseLibroVO libro, UsuarioVO usuario) {

		PermisosLibroVO result = null;

		UsuarioVO usuarioActual = getContextoAplicacionManager()
				.getUsuarioActual();

		PermisosUsuarioVO permisos = usuarioActual.getPermisos();
		String idLibro = libro.getId();

		if (usuarioActual.getPermisos().getPermisosAplicacion()
				.isSuperUsuario()) {
			result = new PermisosLibroVO(true);
		} else {

			String sessionID = usuarioActual.getConfiguracionUsuario()
					.getSessionID();

			if (permisos != null) {
				result = permisos.getPermisoLibro(idLibro);
			}

			// si no tiene seteado permisos para este libro para el usuario
			// actual
			// se intenta obtener
			if (result == null) {
				// intentamos obtenerlos si el libro ha sido abierto
				result = getLegacyPermisosLibroAdapter(idLibro, sessionID);

			}

			// si sigue siendo nulo, igual no se ha abierto el libro que es el
			// que
			// obtiene los permisos
			if (result == null) {

				try {
					libroManager.abrirLibro(usuarioActual, libro);
					// volvemos a intentar obtener el libro
					result = getLegacyPermisosLibroAdapter(idLibro, sessionID);

				} catch (Exception ex) {
					// TODO ver como tratar esta excepcion
					if (logger.isDebugEnabled()) {
						logger
								.debug(
										"Error abriendo el libro para obtener persmisos",
										ex);
					}

				}
			}
		}

		// seteamos los permisos al usuario actual
		permisos.setPermisoLibro(idLibro, result);

		return result;

	}

	protected ContextoAplicacionManager getContextoAplicacionManager() {
		return ContextoAplicacionManagerFactory.getInstance();
	}

	/**
	 * 
	 * @param idLibro
	 * @param sessionID
	 * @return
	 */
	protected PermisosLibroVO getLegacyPermisosLibroAdapter(String idLibro,
			String sessionID) {

		PermisosLibroVO result = null;
		CacheBag cacheBag = null;
		ISicresAPerms aPerms = null;
		try {
			cacheBag = CacheFactory.getCacheInterface()
					.getCacheEntry(sessionID);

			// La app vieja trata los ids como Integer
			THashMap bookInformation = (THashMap) cacheBag.get(Integer
					.valueOf(idLibro));
			aPerms = (ISicresAPerms) bookInformation
					.get(ServerKeys.APERMS_USER);
		} catch (Exception ex) {
			// TODO que hacer con esta excepción
			if (logger.isDebugEnabled()) {
				logger.debug("Error obteniendo permisos del libro " + idLibro,
						ex);
			}
		}

		if (aPerms != null) {
			result = new PermisosLibroVO();
			result.setConsulta(aPerms.canQuery());
			result.setCreacion(aPerms.canCreate());
			result.setModificacion(aPerms.canModify());
			result.setAdministrador(aPerms.isBookAdmin());

		}

		return result;

	}

	public PermisosLibroVO getPermisosLibro(BaseLibroVO libro, OficinaVO oficina) {
		throw new UnsupportedOperationException();
	}

	public PermisosRegistroVO getPermisosRegistro(BaseRegistroVO registro,
			UsuarioVO usuario) {
		throw new UnsupportedOperationException();
	}

	public PermisosAplicacionVO getPermisosAplicacionVO(UsuarioVO usuario) {
		throw new UnsupportedOperationException();
	}

	public BaseFiltroRegistroVO getFiltrosLibro(BaseLibroVO libro,
			UsuarioVO usuario) {
		throw new UnsupportedOperationException();
	}

	public BaseFiltroRegistroVO getFiltrosLibro(BaseLibroVO libro,
			OficinaVO oficina) {
		throw new UnsupportedOperationException();
	}

	public LibroManager getLibroManager() {
		return libroManager;
	}

	public void setLibroManager(LibroManager libroManager) {
		this.libroManager = libroManager;
	}

	// Members
	/**
	 * Logger for this class
	 */
	protected LibroManager libroManager;

	private static final Logger logger = Logger
			.getLogger(PermisosManagerImpl.class);

}
