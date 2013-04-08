package es.ieci.tecdoc.isicres.api.business.manager.impl.builder;

import java.util.List;

import com.ieci.tecdoc.common.isicres.AxDoch;

import es.ieci.tecdoc.isicres.api.business.vo.DocumentoRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.IdentificadorRegistroVO;

public class DocumentoRegistroVOBuilder {

	/**
	 * Metodo que adapta un objeto {@link AxDoch} a un objeto {@link DocumentoRegistroVO}
	 * @param axDoch 
	 * @return {@link DocumentoRegistroVO}
	 */
	public DocumentoRegistroVO build(AxDoch axDoch, String entidad){
		DocumentoRegistroVO result = new DocumentoRegistroVO();
		
		result.setId(Integer.toString(axDoch.getId()));
		
		IdentificadorRegistroVO idRegistro = new IdentificadorRegistroVO();
		idRegistro.setIdLibro(Integer.toString(axDoch.getArchId()));
		idRegistro.setIdRegistro(Integer.toString(axDoch.getFdrId()));
		result.setIdRegistro(idRegistro);
		
		result.setName(axDoch.getName());

		List listPaginaDocumentoRegistroVO = (new ListPaginaDocumentoRegistroVOBuilder()).build(
				axDoch, entidad);
		
		result.setPaginas(listPaginaDocumentoRegistroVO);
		
		return result;
	}
}
