package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.input;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.vo.DocumentoFisicoVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSParamDocument;

/**
 * Instancia de <code>Mapper</code> que transforma un objeto de tipo
 * <code>WSParamDocument</code> en uno de tipo <code>DocumentoFisicoVO</code>.
 * 
 * @see WSParamDocument
 * @see DocumentoFisicoVO
 * 
 * @author IECISA
 * 
 */
public class WSParamDocumentToDocumentoFisicoVOMapper implements Mapper {

	public Object map(Object obj) {
		Assert.isInstanceOf(WSParamDocument.class, obj);

		WSParamDocument paramDocument = (WSParamDocument) obj;

		DocumentoFisicoVO result = new DocumentoFisicoVO();
		result.setName(paramDocument.getDocumentName());
		result.setContent(paramDocument.getDocumentContent());
		result.setLocation(paramDocument.getDocumentLocation());
		// TODO: ¿Este campo? paramDocument.getFileName()

		return result;
	}

}
