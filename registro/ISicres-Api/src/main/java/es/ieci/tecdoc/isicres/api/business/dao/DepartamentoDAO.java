package es.ieci.tecdoc.isicres.api.business.dao;

import es.ieci.tecdoc.isicres.api.business.vo.DepartamentoOficinaVO;
import es.ieci.tecdoc.isicres.api.business.vo.DepartamentoUsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.OficinaVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;

public interface DepartamentoDAO {
	
	public DepartamentoUsuarioVO getDepartamentoUsuario(UsuarioVO usuario);
	public DepartamentoOficinaVO getDepartamentoOficina (OficinaVO oficina);
	
	
	

}
