package es.ieci.tecdoc.fwktd.sir.api.dao.ibatis.handler;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.sqlmap.client.extensions.StringValuedEnumTypeHandlerCallback;
import es.ieci.tecdoc.fwktd.sir.core.types.DocumentacionFisicaEnum;

/**
 * Callback para el tratamiento de enumerados de tipo
 * <code>DocumentacionFisicaEnum</code>.
 *
 * @author IECISA
 * @see DocumentacionFisicaEnum
 *
 */
public class DocumentacionFisicaEnumHandlerCallback extends
		StringValuedEnumTypeHandlerCallback<DocumentacionFisicaEnum> {

	public DocumentacionFisicaEnumHandlerCallback() {
		super(DocumentacionFisicaEnum.class, true);
	}

}
