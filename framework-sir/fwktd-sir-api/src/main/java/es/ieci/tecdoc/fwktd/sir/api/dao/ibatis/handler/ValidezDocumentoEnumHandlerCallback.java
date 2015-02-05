package es.ieci.tecdoc.fwktd.sir.api.dao.ibatis.handler;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.sqlmap.client.extensions.StringValuedEnumTypeHandlerCallback;
import es.ieci.tecdoc.fwktd.sir.core.types.ValidezDocumentoEnum;

/**
 * Callback para el tratamiento de enumerados de tipo
 * <code>ValidezDocumentoEnum</code>.
 *
 * @author IECISA
 * @see ValidezDocumentoEnum
 *
 */
public class ValidezDocumentoEnumHandlerCallback extends
		StringValuedEnumTypeHandlerCallback<ValidezDocumentoEnum> {

	public ValidezDocumentoEnumHandlerCallback() {
		super(ValidezDocumentoEnum.class, true);
	}

}
