package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.input;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.vo.DocumentoFisicoVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSParamDocument;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSParamDocument;

/**
 * Instancia de <code>Mapper</code> que transforma objetos de tipo
 * <code>ArrayOfWsParamDocument</code> a una <code>List</code> de
 * <code>DocumentoFisicoVO</code>.
 * 
 * @see ArrayOfWSParamDocument
 * @see DocumentoFisicoVO
 * 
 * @author IECISA
 * 
 */
public class ArrayOfWsParamDocumentToListOfDocumentoFisicoVOMapper implements
		Mapper {

	public Object map(Object obj) {
		Assert.isInstanceOf(ArrayOfWSParamDocument.class, obj);

		ArrayOfWSParamDocument arrayOfWSParamDocument = (ArrayOfWSParamDocument) obj;

		List<DocumentoFisicoVO> result = new ArrayList<DocumentoFisicoVO>();

		for (WSParamDocument paramDocument : arrayOfWSParamDocument
				.getWSParamDocument()) {
			result
					.add((DocumentoFisicoVO) new WSParamDocumentToDocumentoFisicoVOMapper()
							.map(paramDocument));
		}

		return result;
	}

}
