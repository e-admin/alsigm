package es.ieci.tecdoc.isicres.api.documento.electronico.business.dao.impl.typehandler;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.sqlmap.client.extensions.ValuedEnumTypeHandlerCallback;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.TipoDocumentalSicresEnumVO;

public class TipoDocumentalSicresEnumTypeHandlerCallback extends ValuedEnumTypeHandlerCallback<TipoDocumentalSicresEnumVO> {
	public TipoDocumentalSicresEnumTypeHandlerCallback() {
		super(TipoDocumentalSicresEnumVO.class, true);
	}
	
	public TipoDocumentalSicresEnumTypeHandlerCallback(Class<TipoDocumentalSicresEnumVO> aClass, boolean aStoreAsOrdinal) {
		super(aClass, aStoreAsOrdinal);
	}
}
