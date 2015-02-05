package es.ieci.tecdoc.isicres.api.business.dao;

import es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.OficinaVO;
import es.ieci.tecdoc.isicres.api.business.vo.PermisosLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;

public interface PermisoDAO {
	public PermisosLibroVO getPermisosLibro(BaseLibroVO libro, UsuarioVO usuario);
	public PermisosLibroVO getPermisosLibro(BaseLibroVO libro, OficinaVO oficina);

}
