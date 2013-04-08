package es.ieci.tecdoc.fwktd.sir.api.dao.ibatis.handler;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.sqlmap.client.extensions.ValuedEnumTypeHandlerCallback;
import es.ieci.tecdoc.fwktd.sir.api.types.TipoIntercambioEnum;

/**
 * Callback para el tratamiento de enumerados de tipo
 * <code>TipoIntercambioEnum</code>.
 *
 * @author IECISA
 * @see TipoIntercambioEnum
 *
 */
public class TipoIntercambioEnumHandlerCallback extends
		ValuedEnumTypeHandlerCallback<TipoIntercambioEnum> {

	public TipoIntercambioEnumHandlerCallback() {
		super(TipoIntercambioEnum.class, true);
	}

}
