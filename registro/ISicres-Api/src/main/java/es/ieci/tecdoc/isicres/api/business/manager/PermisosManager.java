package es.ieci.tecdoc.isicres.api.business.manager;

import es.ieci.tecdoc.isicres.api.business.vo.BaseFiltroRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.BaseRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.OficinaVO;
import es.ieci.tecdoc.isicres.api.business.vo.PermisosAplicacionVO;
import es.ieci.tecdoc.isicres.api.business.vo.PermisosLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.PermisosRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;

public abstract class PermisosManager {

	/**
	 * Los usuarios tienen configurado unos permisos de libro a nivel de libro
	 * @param libro
	 * @param usuario
	 * @return
	 */
	public abstract PermisosLibroVO getPermisosLibro(BaseLibroVO libro, UsuarioVO usuario);
	
	
	/**
	 * Obtiene los filtros de visualización de registros de un libro asociados a nivel de usuario
	 * @param libro
	 * @param usuario
	 * @return
	 */
	public abstract BaseFiltroRegistroVO getFiltrosLibro(BaseLibroVO libro, UsuarioVO usuario);
	
	
	/**
	 * Obtiene los filtros de visualización de registros de un libro asociados a nivel de oficina
	 * @param libro
	 * @param oficina
	 * @return
	 */
	public abstract BaseFiltroRegistroVO getFiltrosLibro(BaseLibroVO libro, OficinaVO oficina);
		
	
	/**
	 * Las oficinas tienen configurado unos permisos de libro a nivel de libro
	 * @param libro
	 * @param oficina
	 * @return
	 */
	public abstract PermisosLibroVO getPermisosLibro(BaseLibroVO libro, OficinaVO oficina);
	
	/**
	 * Los usuarios tienen configurado unos permisos de aplicación 
	 * @param libro
	 * @param usuario
	 * @return
	 */
	public abstract PermisosAplicacionVO getPermisosAplicacionVO( UsuarioVO usuario);
	
	
	/**
	 * En runtime los registros puedan no permitir realizar alguna operación aunque el usuario tenga permiso para ello debido a alguna restrición
	 * @param registro
	 * @param usuario
	 * @return
	 */
	public abstract PermisosRegistroVO getPermisosRegistro(BaseRegistroVO registro, UsuarioVO usuario);
	

}
