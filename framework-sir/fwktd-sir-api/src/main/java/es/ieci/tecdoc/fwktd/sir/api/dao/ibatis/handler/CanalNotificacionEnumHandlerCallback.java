package es.ieci.tecdoc.fwktd.sir.api.dao.ibatis.handler;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.sqlmap.client.extensions.StringValuedEnumTypeHandlerCallback;
import es.ieci.tecdoc.fwktd.sir.core.types.CanalNotificacionEnum;

/**
 * Callback para el tratamiento de enumerados de tipo
 * <code>CanalNotificacionEnum</code>.
 *
 * @author IECISA
 * @see CanalNotificacionEnum
 *
 */
public class CanalNotificacionEnumHandlerCallback extends
		StringValuedEnumTypeHandlerCallback<CanalNotificacionEnum> {

	public CanalNotificacionEnumHandlerCallback() {
		super(CanalNotificacionEnum.class, true);
	}

}
