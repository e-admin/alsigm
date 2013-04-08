package es.ieci.tecdoc.fwktd.sir.api.dao.ibatis.handler;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.sqlmap.client.extensions.ValuedEnumTypeHandlerCallback;
import es.ieci.tecdoc.fwktd.sir.core.types.EstadoAsientoRegistralEnum;

/**
 * Callback para el tratamiento de enumerados de tipo
 * <code>EstadoAsientoRegistralEnum</code>.
 *
 * @author IECISA
 * @see EstadoAsientoRegistralEnum
 *
 */
public class EstadoAsientoRegistralEnumHandlerCallback extends
		ValuedEnumTypeHandlerCallback<EstadoAsientoRegistralEnum> {

	public EstadoAsientoRegistralEnumHandlerCallback() {
		super(EstadoAsientoRegistralEnum.class, true);
	}

}
