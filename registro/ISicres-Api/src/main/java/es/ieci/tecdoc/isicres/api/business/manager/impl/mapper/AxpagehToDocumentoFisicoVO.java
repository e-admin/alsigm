package es.ieci.tecdoc.isicres.api.business.manager.impl.mapper;

import com.ieci.tecdoc.common.isicres.AxPageh;
import com.ieci.tecdoc.common.repository.vo.ISRepositoryRetrieveDocumentVO;

import es.ieci.tecdoc.isicres.api.business.vo.DocumentoFisicoVO;

/**
 * Clase que se encarga de mapear las propiedades para obtener un objeto
 * {@link DocumentoFisicoVO}
 * 
 * @author IECISA
 * 
 */
public class AxpagehToDocumentoFisicoVO {

	/**
	 * Metodo que con el objeto {@link AxPageh} y el objeto
	 * {@link ISRepositoryRetrieveDocumentVO} se obtiene un objeto tipo
	 * {@link DocumentoFisicoVO}
	 * 
	 * @param axPageh
	 * @param findDocumentRetrieveVO
	 * @return DocumentoFisicoVO
	 */
	public DocumentoFisicoVO map(AxPageh axPageh,ISRepositoryRetrieveDocumentVO findDocumentRetrieveVO){
		DocumentoFisicoVO result = new DocumentoFisicoVO();
		
		result.setContent(findDocumentRetrieveVO.getFileContent());
		result.setExtension(axPageh.getLoc());
		result.setId(findDocumentRetrieveVO.getPageID().toString());
		result.setLocation(findDocumentRetrieveVO.getDocumentUID());
		result.setName(axPageh.getName());
		
		return result;
	}
}
