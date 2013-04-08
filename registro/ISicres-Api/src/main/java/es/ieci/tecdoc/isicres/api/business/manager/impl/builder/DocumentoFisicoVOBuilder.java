package es.ieci.tecdoc.isicres.api.business.manager.impl.builder;

import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.isicres.AxPageh;
import com.ieci.tecdoc.common.repository.helper.ISRepositoryDocumentHelper;
import com.ieci.tecdoc.common.repository.vo.ISRepositoryRetrieveDocumentVO;
import com.ieci.tecdoc.isicres.repository.RepositoryFactory;
import com.ieci.tecdoc.utils.HibernateUtil;

import es.ieci.tecdoc.isicres.api.business.exception.RegistroException;
import es.ieci.tecdoc.isicres.api.business.manager.impl.mapper.AxpagehToDocumentoFisicoVO;
import es.ieci.tecdoc.isicres.api.business.vo.DocumentoFisicoVO;

public class DocumentoFisicoVOBuilder {

	private static final Logger logger = Logger
	.getLogger(DocumentoFisicoVOBuilder.class);
	
	/**
	 * Metodo que obtiene un {@link DocumentoFisicoVO} a partir de un objeto {@link AxPageh}, un bookId y la entidad
	 * 
	 * @param axPageh
	 * @return {@link DocumentoFisicoVO}
	 */
	public DocumentoFisicoVO build(int bookId, AxPageh axPageh, String entidad){
		DocumentoFisicoVO result = null;
		
		Transaction tran = null;
		
		try {
			ISRepositoryRetrieveDocumentVO findDocumentRetrieveVO = ISRepositoryDocumentHelper
			.getRepositoryRetrieveDocumentVO(new Integer(bookId), new Integer(axPageh.getFdrId()), new Integer(axPageh.getId()),
					entidad, true);

			Session session = HibernateUtil.currentSession(entidad);
			tran = session.beginTransaction();
			
			//obtenemos la informacion del documento fisico
			ISRepositoryRetrieveDocumentVO retrieveDocumentVO = RepositoryFactory
					.getCurrentPolicy().retrieveDocument(findDocumentRetrieveVO);
			
			if (retrieveDocumentVO != null){
				findDocumentRetrieveVO.setFileContent(retrieveDocumentVO.getFileContent());
			}

			//adaptamos la informacion que tenemos para obtener un DocumentoFisicoVO
			result = new AxpagehToDocumentoFisicoVO().map(axPageh, findDocumentRetrieveVO);
			
			HibernateUtil.commitTransaction(tran);
		} catch (Exception e) {
			logger.error("Error al obtener la información para el documento fisico con id [" + axPageh.getId() + "] " + e.getMessage(), e);
			throw new RegistroException(
					"Error al obtener la información para el documento fisico con id ["
							+ axPageh.getId() + "]", e);
		} finally{
			HibernateUtil.closeSession(entidad);
		}
		return result;
		
	}
}
