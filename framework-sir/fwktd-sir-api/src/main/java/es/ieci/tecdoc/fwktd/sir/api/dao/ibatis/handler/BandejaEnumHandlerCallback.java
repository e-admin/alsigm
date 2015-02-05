package es.ieci.tecdoc.fwktd.sir.api.dao.ibatis.handler;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.sqlmap.client.extensions.ValuedEnumTypeHandlerCallback;
import es.ieci.tecdoc.fwktd.sir.api.types.BandejaEnum;

/**
 * Callback para el tratamiento de enumerados de tipo
 * <code>BandejaEnum</code>.
 *
 * @author IECISA
 * @see BandejaEnum
 *
 */
public class BandejaEnumHandlerCallback extends
		ValuedEnumTypeHandlerCallback<BandejaEnum> {

	public BandejaEnumHandlerCallback() {
		super(BandejaEnum.class, true);
	}

}
