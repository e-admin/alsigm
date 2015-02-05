package es.ieci.tecdoc.fwktd.sir.api.dao.ibatis.handler;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.sqlmap.client.extensions.StringValuedEnumTypeHandlerCallback;
import es.ieci.tecdoc.fwktd.sir.api.types.TipoMensajeEnum;

/**
 * Callback para el tratamiento de enumerados de tipo
 * <code>TipoMensajeEnum</code>.
 *
 * @author IECISA
 * @see TipoMensajeEnum
 *
 */
public class TipoMensajeEnumHandlerCallback extends
		StringValuedEnumTypeHandlerCallback<TipoMensajeEnum> {

	public TipoMensajeEnumHandlerCallback() {
		super(TipoMensajeEnum.class, true);
	}

}
