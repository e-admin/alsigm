package es.ieci.tecdoc.isicres.document.manager.invesdoc;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.ieci.tecdoc.common.entity.AxPagehEntity;
import com.ieci.tecdoc.common.isicres.AxPKById;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.repository.GenericRepositoryManager;
import com.ieci.tecdoc.common.repository.vo.ISRepositoryConfigurationVO;
import com.ieci.tecdoc.common.repository.vo.ISRepositoryRetrieveDocumentVO;

import es.ieci.tecdoc.isicres.document.connector.invesdoc.InvesDocDocumentConnector;
import es.ieci.tecdoc.isicres.document.connector.invesdoc.vo.InvesDocDatosEspecificosVO;
import es.ieci.tecdoc.isicres.document.connector.vo.ISicresAbstractDocumentVO;
import es.ieci.tecdoc.isicres.document.connector.vo.ISicresBasicDatosEspecificosVO;
import es.ieci.tecdoc.isicres.document.connector.vo.ISicresBasicDatosEspecificosValueVO;
import es.ieci.tecdoc.isicres.document.connector.vo.ISicresBasicDocumentVO;
import es.ieci.tecdoc.isicres.document.connector.vo.IsicresBasicConnectorConfigurationVO;

/**
 *
 * @author 66575267
 *
 * Implementación del Manager de Repositorio documental para InvesDoc
 *
 */
public class InvesDocRepositoryManager extends GenericRepositoryManager {

	public InvesDocRepositoryManager() {
		super();
		this.connector = new InvesDocDocumentConnector();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.repository.GenericRepositoryManager#getAxSfValues(com.ieci.tecdoc.common.isicres.AxSf)
	 *
	 * Este método sobreescribe al generico porque este gestor documental no
	 * necesita los datos que proporciona un registro
	 */
	protected Map getAxSfValues(AxSf axsf) {
		return new HashMap();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.repository.GenericRepositoryManager#getConfiguration(com.ieci.tecdoc.common.repository.vo.ISicresRepositoryConfigurationVO)
	 *      Este método sobreescribe al generico porque este gestor documental
	 *      no necesita optener la configuración de la misma forma que el resto
	 *      de gestores documentales
	 *
	 */
	protected IsicresBasicConnectorConfigurationVO getConfiguration(
			ISRepositoryConfigurationVO configurationVO) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.repository.GenericRepositoryManager#getEspecificRepositoryDataForCreate(es.ieci.tecdoc.isicres.document.connector.vo.ISicresBasicDocumentVO)
	 */
	protected ISicresAbstractDocumentVO getEspecificRepositoryDataForCreate(
			ISicresBasicDocumentVO sicresDocumentVO) {
		ISicresBasicDatosEspecificosVO basicDatEspVO = (ISicresBasicDatosEspecificosVO) sicresDocumentVO
				.getDatosEspecificos();
		Map values = basicDatEspVO.getValues();

		if (values != null && !values.isEmpty()) {

			ISicresBasicDatosEspecificosValueVO bookId = (ISicresBasicDatosEspecificosValueVO) values
					.get(KEY_BOOK_ID);
			ISicresBasicDatosEspecificosValueVO docId = (ISicresBasicDatosEspecificosValueVO) values
					.get(KEY_DOCUMENT_ISICRES_ID);
			ISicresBasicDatosEspecificosValueVO entidad = (ISicresBasicDatosEspecificosValueVO) values
					.get(KEY_ENTIDAD);
			ISicresBasicDatosEspecificosValueVO fdrid = (ISicresBasicDatosEspecificosValueVO) values
					.get(KEY_REGISTER_ID);
			ISicresBasicDatosEspecificosValueVO miscFormatId = (ISicresBasicDatosEspecificosValueVO) values
					.get(KEY_IDOC_MISCFORMARID);
			ISicresBasicDatosEspecificosValueVO extension = (ISicresBasicDatosEspecificosValueVO) values
					.get(KEY_FILEEXTENSION);

			InvesDocDatosEspecificosVO datosEspecificosVO = new InvesDocDatosEspecificosVO();
			if (bookId != null) {
				datosEspecificosVO.setBookId(Integer
						.parseInt(bookId.getValue()));
			}
			if (docId != null) {
				datosEspecificosVO.setDocId(Integer.parseInt(docId.getValue()));
			}
			if (entidad != null) {
				datosEspecificosVO.setEntidad(entidad.getValue());
			}
			if (fdrid != null) {
				datosEspecificosVO.setFdrid(Integer.parseInt(fdrid.getValue()));
			}
			if (extension != null) {
				datosEspecificosVO.setExtension(extension.getValue());
			}
			if (miscFormatId != null) {
				datosEspecificosVO.setListId(Integer.parseInt(miscFormatId
						.getValue()));
			}

			sicresDocumentVO.setDatosEspecificos(datosEspecificosVO);
		}

		return sicresDocumentVO;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.repository.GenericRepositoryManager#getEspecificRepositoryDataForRetrieve(es.ieci.tecdoc.isicres.document.connector.vo.ISicresBasicDocumentVO)
	 */
	protected ISicresAbstractDocumentVO getEspecificRepositoryDataForRetrieve(
			ISicresBasicDocumentVO sicresDocumentVO) {
		ISicresBasicDatosEspecificosVO basicDatEspVO = (ISicresBasicDatosEspecificosVO) sicresDocumentVO
				.getDatosEspecificos();
		Map values = basicDatEspVO.getValues();

		if (values != null && !values.isEmpty()) {
			ISicresBasicDatosEspecificosValueVO entidad = (ISicresBasicDatosEspecificosValueVO) values
					.get(KEY_ENTIDAD);

			InvesDocDatosEspecificosVO datosEspecificosVO = new InvesDocDatosEspecificosVO();
			if (entidad != null) {
				datosEspecificosVO.setEntidad(entidad.getValue());
			}

			sicresDocumentVO.setDatosEspecificos(datosEspecificosVO);
		}

		return sicresDocumentVO;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ieci.tecdoc.common.repository.GenericRepositoryManager#getEspecificRepositoryDataForUpdate(es.ieci.tecdoc.isicres.document.connector.vo.ISicresBasicDocumentVO)
	 */
	protected ISicresAbstractDocumentVO getEspecificRepositoryDataForUpdate(
			ISicresBasicDocumentVO sicresDocumentVO) {
		ISicresBasicDatosEspecificosVO basicDatEspVO = (ISicresBasicDatosEspecificosVO) sicresDocumentVO
				.getDatosEspecificos();
		Map values = basicDatEspVO.getValues();

		if (values != null && !values.isEmpty()) {
			ISicresBasicDatosEspecificosValueVO bookId = (ISicresBasicDatosEspecificosValueVO) values
					.get(KEY_BOOK_ID);
			ISicresBasicDatosEspecificosValueVO docId = (ISicresBasicDatosEspecificosValueVO) values
					.get(KEY_DOCUMENT_ISICRES_ID);
			ISicresBasicDatosEspecificosValueVO entidad = (ISicresBasicDatosEspecificosValueVO) values
					.get(KEY_ENTIDAD);
			ISicresBasicDatosEspecificosValueVO fdrid = (ISicresBasicDatosEspecificosValueVO) values
					.get(KEY_REGISTER_ID);
			ISicresBasicDatosEspecificosValueVO miscFormatId = (ISicresBasicDatosEspecificosValueVO) values
					.get(KEY_IDOC_MISCFORMARID);
			ISicresBasicDatosEspecificosValueVO extensionVO = (ISicresBasicDatosEspecificosValueVO) values
					.get(KEY_FILEEXTENSION);
			String fileName = sicresDocumentVO.getName();
			String extension = null;

			InvesDocDatosEspecificosVO datosEspecificosVO = new InvesDocDatosEspecificosVO();

			if (bookId != null) {
				datosEspecificosVO.setBookId(Integer
						.parseInt(bookId.getValue()));
			}
			if (docId != null) {
				datosEspecificosVO.setDocId(Integer.parseInt(docId.getValue()));
			}
			if (entidad != null) {
				datosEspecificosVO.setEntidad(entidad.getValue());
			}
			if (fdrid != null) {
				datosEspecificosVO.setFdrid(Integer.parseInt(fdrid.getValue()));
			}
			if (entidad != null) {
				datosEspecificosVO.setListId(Integer.parseInt(miscFormatId
						.getValue()));
			}
			if (extensionVO != null) {
				extension = extensionVO.getValue();
			}
			if (StringUtils.isNotEmpty(fileName)
					&& StringUtils.isEmpty(extension)) {
				extension = fileName.substring(fileName.indexOf(".") + 1,
						fileName.length());
			}
			datosEspecificosVO.setExtension(extension);

			sicresDocumentVO.setDatosEspecificos(datosEspecificosVO);
		}

		return sicresDocumentVO;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see com.ieci.tecdoc.common.repository.GenericRepositoryManager#getEspecificDocUID(com.ieci.tecdoc.common.repository.vo.ISRepositoryRetrieveDocumentVO)
	 */
	protected ISRepositoryRetrieveDocumentVO getEspecificDocUID(
			ISRepositoryRetrieveDocumentVO retrieveVO) {

		if (log.isDebugEnabled()) {
			log.debug("obteniedo page pageID  => "
					+ retrieveVO.getPageID().toString());
		}

		AxPKById pagePk = new AxPKById(retrieveVO.getBookID().toString(),
				retrieveVO.getFdrid().intValue(), retrieveVO.getPageID()
						.intValue());
		AxPagehEntity axPagehEntity = new AxPagehEntity();
		try {
			axPagehEntity.load(pagePk, retrieveVO.getEntidad());
		} catch (Exception e) {
			return retrieveVO;
		}

		if ((axPagehEntity != null) && (axPagehEntity.getFileId() != 0)
				&& (axPagehEntity.getFileId() != (Integer.MAX_VALUE - 2))) {
			retrieveVO
					.setDocumentUID(String.valueOf(axPagehEntity.getFileId()));
		}

		return retrieveVO;
	}


}