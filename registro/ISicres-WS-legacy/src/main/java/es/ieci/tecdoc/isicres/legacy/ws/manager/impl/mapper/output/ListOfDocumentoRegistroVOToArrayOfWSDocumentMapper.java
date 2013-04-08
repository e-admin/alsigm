package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output;

import java.util.Iterator;
import java.util.List;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.vo.DocumentoRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.PaginaDocumentoRegistroVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSDocument;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSPage;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSDocument;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSPage;

/**
 * Instancia de <code>Mapper</code> que transforma una <code>List</code> de
 * <code>DocumentoRegistroVO</code> en una instancia de
 * <code>ArrayOfWSDocument</code>.
 * 
 * @see DocumentoRegistroVO
 * @see ArrayOfWSDocument
 * 
 * @author IECISA
 * 
 */
public class ListOfDocumentoRegistroVOToArrayOfWSDocumentMapper implements
		Mapper {

	@SuppressWarnings("unchecked")
	public Object map(Object obj) {
		Assert.isInstanceOf(List.class, obj);

		List<DocumentoRegistroVO> documentos = (List<DocumentoRegistroVO>) obj;

		ArrayOfWSDocument result = new ArrayOfWSDocument();

		for (DocumentoRegistroVO d : documentos) {
			WSDocument doc = new WSDocument();
			doc.setId(Integer.valueOf(d.getId()).intValue());
			doc.setName(d.getName());

			ArrayOfWSPage pages = new ArrayOfWSPage();
			Iterator<PaginaDocumentoRegistroVO> iterator = d.getPaginas()
					.iterator();
			while (iterator.hasNext()) {
				PaginaDocumentoRegistroVO pag = iterator.next();

				pages.getWSPage().add(
						(WSPage) new PaginaDocumentoRegistroVOToWSPageMapper()
								.map(pag));
			}
			doc.setPages(pages);

			result.getWSDocument().add(doc);
		}

		return result;
	}
}
