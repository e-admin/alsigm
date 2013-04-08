package es.ieci.tecdoc.fwktd.sir.api.dao.ibatis.handler;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.sqlmap.client.extensions.ValuedEnumTypeHandlerCallback;
import es.ieci.tecdoc.fwktd.sir.api.types.TipoContadorEnum;

/**
 * Callback para el tratamiento de enumerados de tipo
 * <code>TipoContadorEnum</code>.
 *
 * @author IECISA
 * @see TipoContadorEnum
 *
 */
public class TipoContadorEnumHandlerCallback extends
		ValuedEnumTypeHandlerCallback<TipoContadorEnum> {

	public TipoContadorEnumHandlerCallback() {
		super(TipoContadorEnum.class, true);
	}

}
