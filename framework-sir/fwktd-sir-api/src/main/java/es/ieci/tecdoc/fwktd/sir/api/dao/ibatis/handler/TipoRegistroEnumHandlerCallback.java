package es.ieci.tecdoc.fwktd.sir.api.dao.ibatis.handler;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.sqlmap.client.extensions.StringValuedEnumTypeHandlerCallback;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoRegistroEnum;

/**
 * Callback para el tratamiento de enumerados de tipo
 * <code>TipoRegistroEnum</code>.
 *
 * @author IECISA
 * @see TipoRegistroEnum
 *
 */
public class TipoRegistroEnumHandlerCallback extends
		StringValuedEnumTypeHandlerCallback<TipoRegistroEnum> {

	public TipoRegistroEnumHandlerCallback() {
		super(TipoRegistroEnum.class, true);
	}

}
