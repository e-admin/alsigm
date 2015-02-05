package es.ieci.tecdoc.isicres.api.business.manager.impl.builder;

import com.ieci.tecdoc.common.isicres.AxDoch;
import com.ieci.tecdoc.common.isicres.AxPageh;

import es.ieci.tecdoc.isicres.api.business.vo.DocumentoFisicoVO;
import es.ieci.tecdoc.isicres.api.business.vo.PaginaDocumentoRegistroVO;

public class PaginaDocumentoRegistroVOBuilder {
	
	/**
	 * Metodo que obtiene una {@link PaginaDocumentoRegistroVO} a partir de los
	 * objetos pasados como parametro
	 * 
	 * @param axDoch
	 * @param axPageh
	 * @param entidad
	 * @return {@link PaginaDocumentoRegistroVO}
	 */
	public PaginaDocumentoRegistroVO build(AxDoch axDoch, AxPageh axPageh, String entidad){
		PaginaDocumentoRegistroVO result = new PaginaDocumentoRegistroVO();
		
		result.setId(Integer.toString(axPageh.getId()));
		
		DocumentoFisicoVO documentoFisico = new DocumentoFisicoVOBuilder()
				.build(axDoch
						.getArchId(), axPageh, entidad);
		
		result.setDocumentoFisico(documentoFisico);
		
		result.setIdDocumentoRegistro(Integer.toString(axPageh.getDocId()));
		result.setName(axPageh.getName());
		result.setNumeroPagina(new Integer(axPageh.getSortOrder()));

		return result;
	}
}
