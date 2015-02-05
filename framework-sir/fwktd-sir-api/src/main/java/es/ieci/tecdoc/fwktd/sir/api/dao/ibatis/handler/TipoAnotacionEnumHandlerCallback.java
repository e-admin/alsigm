package es.ieci.tecdoc.fwktd.sir.api.dao.ibatis.handler;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.sqlmap.client.extensions.StringValuedEnumTypeHandlerCallback;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoAnotacionEnum;

/**
 * Callback para el tratamiento de enumerados de tipo
 * <code>TipoAnotacionEnum</code>.
 *
 * @author IECISA
 * @see TipoAnotacionEnum
 *
 */
public class TipoAnotacionEnumHandlerCallback extends
		StringValuedEnumTypeHandlerCallback<TipoAnotacionEnum> {

	public TipoAnotacionEnumHandlerCallback() {
		super(TipoAnotacionEnum.class, true);
	}

}
