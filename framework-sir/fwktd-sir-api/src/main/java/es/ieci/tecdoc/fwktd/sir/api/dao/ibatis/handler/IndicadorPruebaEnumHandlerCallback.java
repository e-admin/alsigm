package es.ieci.tecdoc.fwktd.sir.api.dao.ibatis.handler;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.sqlmap.client.extensions.StringValuedEnumTypeHandlerCallback;
import es.ieci.tecdoc.fwktd.sir.core.types.IndicadorPruebaEnum;

/**
 * Callback para el tratamiento de enumerados de tipo
 * <code>IndicadorPruebaEnum</code>.
 *
 * @author IECISA
 * @see IndicadorPruebaEnum
 *
 */
public class IndicadorPruebaEnumHandlerCallback extends
		StringValuedEnumTypeHandlerCallback<IndicadorPruebaEnum> {

	public IndicadorPruebaEnumHandlerCallback() {
		super(IndicadorPruebaEnum.class, true);
	}

}
