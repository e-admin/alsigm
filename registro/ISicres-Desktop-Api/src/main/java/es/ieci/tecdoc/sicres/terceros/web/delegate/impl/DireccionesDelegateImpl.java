package es.ieci.tecdoc.sicres.terceros.web.delegate.impl;

import java.util.List;

import es.ieci.tecdoc.fwktd.server.manager.BaseManager;
import es.ieci.tecdoc.fwktd.web.delegate.impl.CRUDDelegateImpl;
import es.ieci.tecdoc.isicres.terceros.business.manager.DireccionManager;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseDireccionVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseTerceroVO;
import es.ieci.tecdoc.sicres.terceros.web.delegate.DireccionesDelegate;

/**
 *
 * @author IECISA
 *
 */
public class DireccionesDelegateImpl extends CRUDDelegateImpl<BaseDireccionVO>
		implements DireccionesDelegate {

	public DireccionesDelegateImpl(BaseManager<BaseDireccionVO, String> manager) {
		super(manager);
	}

	public List<BaseDireccionVO> getAll() {
		return getManager().getAll();
	}

	public void selectAsPrincipal(BaseDireccionVO direccion,
			BaseTerceroVO tercero) {
		((DireccionManager) getManager()).selectAsPrincipal(direccion, tercero);
	}
}
