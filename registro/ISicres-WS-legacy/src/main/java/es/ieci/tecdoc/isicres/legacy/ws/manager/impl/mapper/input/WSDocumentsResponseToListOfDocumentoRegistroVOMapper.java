package es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.input;

import java.util.Iterator;
import java.util.List;

import org.springframework.util.Assert;

import es.ieci.tecdoc.isicres.api.business.vo.DocumentoRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.PaginaDocumentoRegistroVO;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.Mapper;
import es.ieci.tecdoc.isicres.legacy.ws.manager.impl.mapper.output.PaginaDocumentoRegistroVOToWSPageMapper;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSDocument;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.ArrayOfWSPage;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSDocument;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSDocumentsResponse;
import es.ieci.tecdoc.isicres.ws.legacy.service.registers.WSPage;

/**
 * 
 * @author IECISA
 * 
 */
public class WSDocumentsResponseToListOfDocumentoRegistroVOMapper implements
		Mapper {

	@SuppressWarnings("unchecked")
	public Object map(Object obj) {
		Assert.isInstanceOf(List.class, obj);

		List<DocumentoRegistroVO> responseDocs = (List<DocumentoRegistroVO>) obj;

		WSDocumentsResponse wsdr = new WSDocumentsResponse();
		wsdr.setTotal(responseDocs.size());
		ArrayOfWSDocument awsd = new ArrayOfWSDocument();

		for (DocumentoRegistroVO dr : responseDocs) {
			WSDocument wsd = new WSDocument();
			wsd.setId(Integer.valueOf(dr.getId()));
			wsd.setName(dr.getName());
			wsd.setPages(new ArrayOfWSPage());
			Iterator<PaginaDocumentoRegistroVO> iterator = dr.getPaginas()
					.iterator();

			while (iterator.hasNext()) {

				PaginaDocumentoRegistroVO pdr = iterator.next();

				wsd.getPages().getWSPage().add(
						(WSPage) new PaginaDocumentoRegistroVOToWSPageMapper()
								.map(pdr));
			}

			awsd.getWSDocument().add(wsd);
		}
		wsdr.setList(awsd);

		return wsdr;
	}

}
