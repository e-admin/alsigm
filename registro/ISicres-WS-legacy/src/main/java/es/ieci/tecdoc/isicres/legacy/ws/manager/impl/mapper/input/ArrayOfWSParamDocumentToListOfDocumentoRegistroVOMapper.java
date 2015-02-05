package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.vo.DocumentoFisicoVO;
import es.ieci.tecdoc.isicres.api.business.vo.DocumentoRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.PaginaDocumentoRegistroVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSParamDocument;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSParamDocument;

/**
 * Instancia de <code>Mapper</code> que transforma objetos de tipo
 * <code>ArrayOfWSParamDocument</code> en objetos de tipo
 * <code>DocumentoRegistroVO</code>.
 *
 * @see ArrayOfWSParamDocument
 * @see DocumentoRegistroVO
 *
 * @author IECISA
 *
 */
public class ArrayOfWSParamDocumentToListOfDocumentoRegistroVOMapper implements
		Mapper {

	/**
	 * Transforma una instancia de <code>ArrayOfWSParamDocument</code> en una
	 * <code>List</code> de <code>DocumentoRegistroVO</code>.
	 */
	public Object map(Object obj) {
		Assert.isInstanceOf(ArrayOfWSParamDocument.class, obj);

		ArrayOfWSParamDocument documents = (ArrayOfWSParamDocument) obj;

		List<DocumentoRegistroVO> result = new ArrayList<DocumentoRegistroVO>();

		for (WSParamDocument doc : documents.getWSParamDocument()) {
			if(validateNotNullFile(doc)){
			DocumentoRegistroVO dr = new DocumentoRegistroVO();
			dr.setName(doc.getDocumentName());
			PaginaDocumentoRegistroVO p = new PaginaDocumentoRegistroVO();
			p.setName(doc.getFileName());

			DocumentoFisicoVO df = new DocumentoFisicoVO();
			df.setContent(doc.getDocumentContent());
			df.setLocation(doc.getDocumentLocation());
				df.setName(doc.getFileName());
			df.setExtension(StringUtils.substringAfterLast(doc.getFileName(),
					"."));
			p.setDocumentoFisico(df);

			dr.setPaginas(Arrays.asList(new PaginaDocumentoRegistroVO[] { p }));

			result.add(dr);
		}
		}

		return result;
	}

	/**
	 * Metodo que valida que el documento pasado como parametro no sea nulo
	 * @param doc
	 * @return boolean
	 */
	private boolean validateNotNullFile (WSParamDocument doc){
		boolean result = true;
		if (StringUtils.isBlank(doc.getDocumentLocation())
				&& StringUtils.isBlank(doc.getDocumentName())
				&& StringUtils.isBlank(doc.getFileName())
				&& (doc.getDocumentContent()==null || doc.getDocumentContent().length == 0)) {
			result = false;
		}
		return result;
	}
}
