package es.ieci.tecdoc.sicres.terceros.web.delegate.impl;

import java.util.List;

import es.ieci.tecdoc.fwktd.server.manager.BaseManager;
import es.ieci.tecdoc.fwktd.web.delegate.impl.CRUDDelegateImpl;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoVO;
import es.ieci.tecdoc.sicres.terceros.web.delegate.TercerosDelegate;

/**
 *
 * @author IECISA
 *
 */
public class TercerosDelegateImpl extends CRUDDelegateImpl<TerceroValidadoVO>
		implements TercerosDelegate {

	public TercerosDelegateImpl(BaseManager<TerceroValidadoVO, String> manager) {
		super(manager);
	}

	public List<TerceroValidadoVO> getAll() {
		return getManager().getAll();
	}

}
