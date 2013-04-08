package es.ieci.tecdoc.isicres.api.business.manager;

import es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.BaseOficinaVO;
import es.ieci.tecdoc.isicres.api.business.vo.BaseRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.ContextoAplicacionVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;

/**
 * interfac para obtener el contexto en el que esta trabajando sicres, usuario actual, libro actual, oficina actual, ..
 * @author Iecisa
 * @version $Revision$ 
 *
 */
public interface ContextoAplicacionManager {
	
	public abstract UsuarioVO getUsuarioActual();
	public abstract void setUsuarioActual(UsuarioVO usuario);
	
	public abstract BaseLibroVO getLibroActual();
	public abstract void setLibroActual(BaseLibroVO libro);
	
	public abstract BaseOficinaVO getOficinaActual();
	public abstract void setOficinaActual(BaseOficinaVO oficina);
	
	public abstract BaseRegistroVO getRegistroActual();
	public abstract void setRegistroActual(BaseRegistroVO registro);
	
	public abstract ContextoAplicacionVO getContextoAplicacionVO();


}
