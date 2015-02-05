package es.ieci.tecdoc.isicres.terceros.business.dao.callback;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.sqlmap.client.extensions.ValuedEnumTypeHandlerCallback;
import es.ieci.tecdoc.isicres.terceros.business.vo.enums.DireccionType;

/**
 * <code>ValuedEnumTypeHandlerCallback</code> para objetos de tipo
 * <code>DireccionType</code>.
 *
 * @author IECISA
 *
 */
public class DireccionTypeCallbackHandler extends
		ValuedEnumTypeHandlerCallback<DireccionType> {

	public DireccionTypeCallbackHandler() {
		this(DireccionType.class, true);
	}

	public DireccionTypeCallbackHandler(Class<DireccionType> aClass,
			boolean aStoreAsOrdinal) {
		super(aClass, aStoreAsOrdinal);
	}

}
