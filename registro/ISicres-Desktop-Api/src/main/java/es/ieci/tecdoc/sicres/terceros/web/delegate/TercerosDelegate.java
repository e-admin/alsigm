package es.ieci.tecdoc.sicres.terceros.web.delegate;

import es.ieci.tecdoc.fwktd.web.delegate.CRUDDelegate;
import es.ieci.tecdoc.fwktd.web.delegate.ListDelegate;
import es.ieci.tecdoc.isicres.terceros.business.vo.TerceroValidadoVO;

/**
 *
 * @author IECISA
 *
 */
public interface TercerosDelegate extends ListDelegate<TerceroValidadoVO>,
		CRUDDelegate<TerceroValidadoVO> {

}
