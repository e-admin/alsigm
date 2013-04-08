package es.ieci.tecdoc.isicres.api.documento.electronico.business.dao.impl.typehandler;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.sqlmap.client.extensions.ValuedEnumTypeHandlerCallback;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.TipoValidezDocumentoAnexoEnumVO;

public class TipoValidezDocumentoAnexoEnumTypeHandlerCallback extends ValuedEnumTypeHandlerCallback<TipoValidezDocumentoAnexoEnumVO>{

	
	public TipoValidezDocumentoAnexoEnumTypeHandlerCallback() {
		this(TipoValidezDocumentoAnexoEnumVO.class,true);
	}
	public TipoValidezDocumentoAnexoEnumTypeHandlerCallback(
			Class<TipoValidezDocumentoAnexoEnumVO> aClass,
			boolean aStoreAsOrdinal) {
		super(aClass, true);
	}

}
