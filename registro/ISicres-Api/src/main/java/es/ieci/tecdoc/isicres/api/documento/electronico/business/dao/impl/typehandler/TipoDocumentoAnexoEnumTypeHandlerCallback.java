package es.ieci.tecdoc.isicres.api.documento.electronico.business.dao.impl.typehandler;

import es.ieci.tecdoc.fwktd.server.dao.ibatis.sqlmap.client.extensions.ValuedEnumTypeHandlerCallback;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.vo.TipoDocumentoAnexoEnumVO;


public class TipoDocumentoAnexoEnumTypeHandlerCallback extends ValuedEnumTypeHandlerCallback<TipoDocumentoAnexoEnumVO> {

	public TipoDocumentoAnexoEnumTypeHandlerCallback(){
		this(TipoDocumentoAnexoEnumVO.class,true);
	}
	public TipoDocumentoAnexoEnumTypeHandlerCallback(
			Class<TipoDocumentoAnexoEnumVO> aClass, boolean aStoreAsOrdinal) {
		super(aClass, true);
	}

}
