package es.ieci.tecdoc.fwktd.sir.api.dao.ibatis.handler;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.sqlmap.client.extensions.StringValuedEnumTypeHandlerCallback;
import es.ieci.tecdoc.fwktd.sir.core.types.TipoTransporteEnum;

/**
 * Callback para el tratamiento de enumerados de tipo
 * <code>TipoTransporteEnum</code>.
 *
 * @author IECISA
 * @see TipoTransporteEnum
 *
 */
public class TipoTransporteEnumHandlerCallback extends
		StringValuedEnumTypeHandlerCallback<TipoTransporteEnum> {

	public TipoTransporteEnumHandlerCallback() {
		super(TipoTransporteEnum.class, true);
	}

}
