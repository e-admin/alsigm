package es.ieci.tecdoc.fwktd.sir.api.dao.ibatis.handler;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.sqlmap.client.extensions.StringValuedEnumTypeHandlerCallback;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoDocumentoIdentificacionEnum;

/**
 * Callback para el tratamiento de enumerados de tipo
 * <code>TipoDocumentoIdentificacionEnum</code>.
 *
 * @author IECISA
 * @see TipoDocumentoIdentificacionEnum
 *
 */
public class TipoDocumentoIdentificacionEnumHandlerCallback extends
		StringValuedEnumTypeHandlerCallback<TipoDocumentoIdentificacionEnum> {

	public TipoDocumentoIdentificacionEnumHandlerCallback() {
		super(TipoDocumentoIdentificacionEnum.class, true);
	}

}
