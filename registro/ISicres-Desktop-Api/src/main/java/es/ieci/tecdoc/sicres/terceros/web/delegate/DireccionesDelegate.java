package es.ieci.tecdoc.sicres.terceros.web.delegate;

import es.ieci.tecdoc.fwktd.web.delegate.CRUDDelegate;
import es.ieci.tecdoc.fwktd.web.delegate.ListDelegate;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseDireccionVO;
import es.ieci.tecdoc.isicres.terceros.business.vo.BaseTerceroVO;

/**
 *
 * @author IECISA
 *
 */
public interface DireccionesDelegate extends ListDelegate<BaseDireccionVO>,
		CRUDDelegate<BaseDireccionVO> {

	public void selectAsPrincipal(BaseDireccionVO direccion,
			BaseTerceroVO tercero);
}
