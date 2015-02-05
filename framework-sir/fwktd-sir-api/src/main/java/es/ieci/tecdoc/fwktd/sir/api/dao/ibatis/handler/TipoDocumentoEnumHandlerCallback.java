package es.ieci.tecdoc.fwktd.sir.api.dao.ibatis.handler;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.sqlmap.client.extensions.StringValuedEnumTypeHandlerCallback;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoEnum;

/**
 * Callback para el tratamiento de enumerados de tipo
 * <code>TipoDocumentoEnum</code>.
 *
 * @author IECISA
 * @see TipoDocumentoEnum
 *
 */
public class TipoDocumentoEnumHandlerCallback extends
		StringValuedEnumTypeHandlerCallback<TipoDocumentoEnum> {

	public TipoDocumentoEnumHandlerCallback() {
		super(TipoDocumentoEnum.class, true);
	}

}
