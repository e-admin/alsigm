/**
 *
 */
package com.ieci.tecdoc.common.repository;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.adapter.IRepositoryManager;
import com.ieci.tecdoc.common.entity.dao.DBEntityDAOFactory;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.repository.dao.ISRepositoryConfigurationDAO;
import com.ieci.tecdoc.common.repository.helper.ISRepositoryManagerHelper;
import com.ieci.tecdoc.common.repository.vo.ISRepositoryConfigurationVO;
import com.ieci.tecdoc.common.repository.vo.ISRepositoryCreateDocumentVO;
import com.ieci.tecdoc.common.repository.vo.ISRepositoryRetrieveDocumentVO;
import com.ieci.tecdoc.common.repository.vo.ISRepositorySignDocumentVO;
import com.ieci.tecdoc.common.repository.vo.ISRepositoryUpdateDocumentVO;
import com.ieci.tecdoc.common.repository.vo.ISSignDocumentVO;
import com.ieci.tecdoc.common.utils.ISFileUtil;
import com.ieci.tecdoc.common.utils.SignTiff;

import es.ieci.tecdoc.isicres.document.connector.ISicresDocumentConnector;
import es.ieci.tecdoc.isicres.document.connector.vo.ISicresAbstractDocumentVO;
import es.ieci.tecdoc.isicres.document.connector.vo.ISicresBasicDatosEspecificosVO;
import es.ieci.tecdoc.isicres.document.connector.vo.ISicresBasicDocumentVO;
import es.ieci.tecdoc.isicres.document.connector.vo.IsicresBasicConnectorConfigurationVO;

/**
 * @author 66575267
 *
 */
public abstract class GenericRepositoryManager implements IRepositoryManager {

	protected static final Logger log = Logger
			.getLogger(GenericRepositoryManager.class);

	protected static String KEY_ENTIDAD = "Entidad";

	// KEYS para parametros de retrieve
	protected static String KEY_DOCUMENT_UID = "DocumentUID";

	// KEYS para parametros de Create o Update
	protected static String KEY_FILENAME = "FileName";
	protected static String KEY_FILEEXTENSION = "FileExtension";
	protected static String KEY_BOOK_ID = "BookID";
	protected static String KEY_BOOK_TYPE = "BookType";
	protected static String KEY_BOOK_NAME = "BookName";
	protected static String KEY_REGISTER_ID = "RegisterID";
	protected static String KEY_DOCUMENT_ISICRES_ID = "DocumentISicresID";
	protected static String KEY_IDOC_MISCFORMARID = "InvesDocMiscFormatID";
	protected static String KEY_REGISTER_NUMBER = "RegisterNumber";
	protected static String KEY_REGISTER_USER = "RegisterUser";
	protected static String KEY_REGISTER_WORKDATE = "RegisterWorkDate";
	protected static String KEY_REGISTER_OFIC_ID = "RegisterOficID";
	protected static String KEY_REGISTER_OFIC_NAME = "RegisterOficName";
	protected static String KEY_REGISTER_DATE = "RegisterDate";

	public static SimpleDateFormat  LONG_DATE_SDF=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	protected ISicresDocumentConnector connector;

	/**
	 * Método que convierte los datos para crear documentos de ISicres en datos
	 * del Conector con el Gestor Documental
	 *
	 * @param iSicresDocumentVO
	 * @return
	 */
	protected abstract ISicresAbstractDocumentVO getEspecificRepositoryDataForCreate(
			ISicresBasicDocumentVO iSicresDocumentVO);

	/**
	 * Método que convierte los datos para recuperar documentos de ISicres en
	 * datos del Conector con el Gestor Documental
	 *
	 * @param iSicresDocumentVO
	 * @return
	 */
	protected abstract ISicresAbstractDocumentVO getEspecificRepositoryDataForRetrieve(
			ISicresBasicDocumentVO iSicresDocumentVO);

	/**
	 * Método que convierte los datos para actualizar documentos de ISicres en
	 * datos del Conector con el Gestor Documental
	 *
	 * @param iSicresDocumentVO
	 * @return
	 */
	protected abstract ISicresAbstractDocumentVO getEspecificRepositoryDataForUpdate(
			ISicresBasicDocumentVO iSicresDocumentVO);

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ieci.tecdoc.common.adapter.IRepositoryManager#createDocument(com.
	 * ieci.tecdoc.common.repository.vo.ISicresRepositoryCreateDocumentVO)
	 */
	public ISRepositoryCreateDocumentVO createDocument(
			ISRepositoryCreateDocumentVO createVO) throws Exception {

		ISRepositoryCreateDocumentVO returnVO = new ISRepositoryCreateDocumentVO();

		if (createVO != null) {

			ISRepositoryConfigurationVO confVO = new ISRepositoryConfigurationVO(
					createVO.getBookType(), createVO.getEntidad());

			ISicresBasicDatosEspecificosVO datosEspecificosVO = getDatosEspecificosForCreateDocument(createVO);
			IsicresBasicConnectorConfigurationVO connectorConfigurationVO = getConfiguration(confVO);

			ISicresBasicDocumentVO iSicresDocumentVO = new ISicresBasicDocumentVO();
			iSicresDocumentVO.setName(getFileNameWithExtension(createVO
					.getFileName(), createVO.getFileExtension()));
			iSicresDocumentVO.setContent(ISFileUtil
					.inputStream2byteArray(createVO.getInputStream()));
			iSicresDocumentVO.setDatosEspecificos(datosEspecificosVO);
			iSicresDocumentVO.setConfiguration(connectorConfigurationVO);

			ISicresAbstractDocumentVO documentVO = getEspecificRepositoryDataForCreate(iSicresDocumentVO);

			documentVO = connector.create(documentVO);

			if (documentVO != null) {
				returnVO.setIsicresDocUID(documentVO.getId());
				returnVO.setFileContent(documentVO.getContent());
			}
		}

		return returnVO;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ieci.tecdoc.common.adapter.IRepositoryManager#retrieveDocument(com
	 * .ieci.tecdoc.common.repository.vo.ISicresRepositoryRetrieveDocumentVO)
	 */
	public ISRepositoryRetrieveDocumentVO retrieveDocument(
			ISRepositoryRetrieveDocumentVO retrieveVO) throws Exception {
		ISRepositoryRetrieveDocumentVO returnVO = new ISRepositoryRetrieveDocumentVO();

		if (retrieveVO != null) {

			retrieveVO = getDocUID(retrieveVO);

			String docUID = retrieveVO.getDocumentUID();

			if (StringUtils.isBlank(docUID)) {
				return returnVO;
			}

			ISRepositoryConfigurationVO confVO = new ISRepositoryConfigurationVO(
					retrieveVO.getBookType(), retrieveVO.getEntidad());

			ISicresBasicDatosEspecificosVO datosEspecificosVO = getDatosEspecificosForRetrieveDocument(
					docUID, retrieveVO.getEntidad());
			IsicresBasicConnectorConfigurationVO connectorConfigurationVO = getConfiguration(confVO);

			ISicresBasicDocumentVO iSicresDocumentVO = new ISicresBasicDocumentVO();
			iSicresDocumentVO.setId(docUID);
			iSicresDocumentVO.setDatosEspecificos(datosEspecificosVO);
			iSicresDocumentVO.setConfiguration(connectorConfigurationVO);

			ISicresAbstractDocumentVO documentVO = getEspecificRepositoryDataForRetrieve(iSicresDocumentVO);
			documentVO = connector.retrieve(documentVO);

			if (documentVO != null) {
				returnVO.setFileContent(documentVO.getContent());
			}
		}

		return returnVO;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ieci.tecdoc.common.adapter.IRepositoryManager#signDocument(com.ieci
	 * .tecdoc.common.repository.vo.ISicresRepositorySingDocumentVO)
	 */
	public ISRepositorySignDocumentVO signDocument(
			ISRepositorySignDocumentVO signVO) throws Exception {

		//Se permite configurar el sello de autenticación, para que solamente se selle la primera página de cada documentación.
		ISRepositoryRetrieveDocumentVO retrieveVO = new ISRepositoryRetrieveDocumentVO();
		retrieveVO.setBookID(signVO.getBookID());
		retrieveVO.setFdrid(signVO.getFdrid());
		retrieveVO.setPageID(signVO.getPageID());
		retrieveVO.setEntidad(signVO.getEntidad());

		retrieveVO = getDocUID(retrieveVO);

		String docUID = retrieveVO.getDocumentUID();

		if (StringUtils.isBlank(docUID)) {
			return signVO;
		}

		ISRepositoryConfigurationVO confVO = new ISRepositoryConfigurationVO(
				signVO.getBookType(), signVO.getEntidad());

		IsicresBasicConnectorConfigurationVO connectorConfigurationVO = getConfiguration(confVO);

		ISicresAbstractDocumentVO documentRetrieveVO = retrieveDocumentForSign(
				signVO, docUID, connectorConfigurationVO);

		if (documentRetrieveVO != null) {

			SignTiff signTiff = new SignTiff();
			byte[] signFileContent = signTiff.saveTiffWithText(signVO
					.getBookTypeConf(), signVO.getBookConf(), documentRetrieveVO.getContent());

			ISicresBasicDocumentVO iSicresUpdateDocumentVO = getSignDocumentForUpdate(
					signVO, docUID, documentRetrieveVO.getName(),
					signFileContent, connectorConfigurationVO);
			ISicresAbstractDocumentVO documentUpdateVO = getEspecificRepositoryDataForUpdate(iSicresUpdateDocumentVO);
			connector.update(documentUpdateVO);
		}

		return signVO;

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.ieci.tecdoc.common.adapter.IRepositoryManager#signDocument(com.ieci
	 * .tecdoc.common.repository.vo.ISicresSingDocumentVO)
	 */
	public ISSignDocumentVO signDocument(ISSignDocumentVO signVO)
			throws Exception {
		ISSignDocumentVO returnVO = new ISSignDocumentVO();

		if (signVO != null) {
			byte[] fileForSign = ISFileUtil.inputStream2byteArray(signVO
					.getInputStream());

			SignTiff signTiff = new SignTiff();
			byte[] signFileContent = signTiff.saveTiffWithText(signVO
					.getBookTypeConf(), signVO.getBookConf(), fileForSign);

			returnVO.setFileContent(signFileContent);
		}

		return returnVO;

	}

	/**
	 *
	 * {@inheritDoc}
	 *
	 * @see com.ieci.tecdoc.common.adapter.IRepositoryManager#saveExistDocument(com.ieci.tecdoc.common.repository.vo.ISRepositorySavedDocumentVO)
	 */
	public ISRepositoryRetrieveDocumentVO saveExistDocument(
			ISRepositoryRetrieveDocumentVO saveExistVO) throws Exception {

		ISRepositoryConfigurationVO confVO = new ISRepositoryConfigurationVO(
				saveExistVO.getBookType(), saveExistVO.getEntidad());

		ISicresBasicDatosEspecificosVO datosEspecificosVO = getDatosEspecificosForRetrieveDocument(
				saveExistVO.getDocumentUID(), saveExistVO.getEntidad());
		IsicresBasicConnectorConfigurationVO connectorConfigurationVO = getConfiguration(confVO);

		ISicresBasicDocumentVO iSicresDocumentVO = new ISicresBasicDocumentVO();
		iSicresDocumentVO.setId(saveExistVO.getDocumentUID());
		iSicresDocumentVO.setDatosEspecificos(datosEspecificosVO);
		iSicresDocumentVO.setConfiguration(connectorConfigurationVO);

		ISicresAbstractDocumentVO documentVO = getEspecificRepositoryDataForRetrieve(iSicresDocumentVO);
		documentVO = connector.retrieve(documentVO);

		if ((documentVO != null) && (documentVO.getContent() != null)
				&& (documentVO.getContent().length > 0)) {
			saveExistVO.setFileContent(documentVO.getContent());
			return saveExistVO;
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see com.ieci.tecdoc.common.adapter.IRepositoryManager#getDocUID(com.ieci.tecdoc.common.repository.vo.ISRepositoryRetrieveDocumentVO)
	 */
	public ISRepositoryRetrieveDocumentVO getDocUID(
			ISRepositoryRetrieveDocumentVO retrieveVO) throws Exception {

		// va a obtener el docUID a la tabla scr_pageRepository
		String docUID = DBEntityDAOFactory.getCurrentDBEntityDAO().getDocUID(
				retrieveVO.getBookID(), retrieveVO.getFdrid(),
				retrieveVO.getPageID(), retrieveVO.getEntidad());

		retrieveVO.setDocumentUID(docUID);

		//si lo obtenido es nulo o vacio quiere decir que en esa tabla no hay valores
		//entoces se intenta hacer como una especie de migracion bajo demanda
		if (StringUtils.isBlank(docUID)) {

			//como el docUID es vacio o nulo pues se hace la peticion a este metodo
			//que lo tendra sobreEscrito el manager correspondiente
			retrieveVO = getEspecificDocUID(retrieveVO);

			//actualiza  la tabla scr_pageRepository con los nuevos valores
			// de esta manera se van migrando los datos
			updatedDocUID(retrieveVO);
		}

		return retrieveVO;
	}

	/**
	 * Método que obtiene la configuracion del repositorio documental
	 *
	 * @param configurationVO
	 * @return
	 */
	protected IsicresBasicConnectorConfigurationVO getConfiguration(
			ISRepositoryConfigurationVO configurationVO) {
		IsicresBasicConnectorConfigurationVO connectoConfigVO = new IsicresBasicConnectorConfigurationVO();

		if (configurationVO != null) {
			String configXML = ISRepositoryConfigurationDAO
					.getRepositoryConfiguration(configurationVO.getBookType(),
							configurationVO.getEntidad());
			connectoConfigVO.setConfigXml(configXML);
		}

		return connectoConfigVO;
	}

	/**
	 * Método que obtiene los datos del registro
	 *
	 * @param axsf
	 * @return
	 */
	protected Map getAxSfValues(AxSf axsf) {
		String regNumber = axsf.getAttributeValueAsString("fld1");
		String regUser = axsf.getAttributeValueAsString("fld3");
		String regWorkDate = LONG_DATE_SDF.format(axsf.getAttributeValue("fld4"));
		String regOficId = axsf.getAttributeValueAsString("fld5");
		String regOficName = axsf.getFld5Name();
		String regDate = LONG_DATE_SDF.format(axsf.getAttributeValue("fld2"));

		Map axsfValues = new HashMap();
		axsfValues.put(KEY_REGISTER_NUMBER, regNumber);
		axsfValues.put(KEY_REGISTER_USER, regUser);
		axsfValues.put(KEY_REGISTER_WORKDATE, regWorkDate);
		axsfValues.put(KEY_REGISTER_OFIC_ID, regOficId);
		axsfValues.put(KEY_REGISTER_OFIC_NAME, regOficName);
		axsfValues.put(KEY_REGISTER_DATE, regDate);

		return axsfValues;
	}

	/**
	 * Metodo que nos devuelve un VO que contiene un Mapa con los datos pasados
	 * como parametro
	 *
	 * @param documentUID
	 * @param entidad
	 * @return
	 */
	protected ISicresBasicDatosEspecificosVO getDatosEspecificosForRetrieveDocument(
			String documentUID, String entidad) {

		Map values = new HashMap();

		values.put(KEY_ENTIDAD, entidad);
		values.put(KEY_DOCUMENT_UID, documentUID);

		return ISRepositoryManagerHelper.getMapDatosEspecificosValueVO(values);
	}

	/**
	 * Método que nos devuelve un VO que contiene un Mapa con los datos pasados
	 * como parametro
	 *
	 * @param createVO
	 * @return
	 */
	protected ISicresBasicDatosEspecificosVO getDatosEspecificosForCreateDocument(
			ISRepositoryCreateDocumentVO createVO) {

		Map values = new HashMap();
		values.put(KEY_ENTIDAD, createVO.getEntidad());
		values.put(KEY_IDOC_MISCFORMARID, String.valueOf(createVO
				.getMiscFormatId()));
		values.put(KEY_DOCUMENT_ISICRES_ID, createVO.getDocId().toString());
		values.put(KEY_REGISTER_ID, createVO.getFdrid().toString());

		values.put(KEY_BOOK_NAME, createVO.getBookName());
		values.put(KEY_BOOK_TYPE, createVO.getBookType().toString());
		values.put(KEY_BOOK_ID, createVO.getBookID().toString());
		values.put(KEY_FILENAME, getFileNameWithExtension(createVO
				.getFileName(), createVO.getFileExtension()));
		values.put(KEY_FILEEXTENSION, createVO.getFileExtension());

		values.putAll(getAxSfValues(createVO.getAxsf()));

		return ISRepositoryManagerHelper.getMapDatosEspecificosValueVO(values);
	}

	/**
	 * Método que nos devuelve un VO que contiene un Mapa con los datos pasados
	 * como parametro
	 *
	 * @param documentUID
	 * @param fileName
	 * @param bookID
	 * @param fdrid
	 * @param docId
	 * @param miscFormatId
	 * @param entidad
	 * @return
	 */
	protected ISicresBasicDatosEspecificosVO getDatosEspecificosForUpdateDocument(
			ISRepositoryUpdateDocumentVO updateVO) {

		Map values = new HashMap();

		values.put(KEY_ENTIDAD, updateVO.getEntidad());
		values.put(KEY_DOCUMENT_UID, updateVO.getDocumentUID());
		values.put(KEY_FILENAME, getFileNameWithExtension(updateVO
				.getFileName(), updateVO.getFileExtension()));
		values.put(KEY_BOOK_ID, updateVO.getBookID().toString());
		values.put(KEY_BOOK_TYPE, updateVO.getBookType().toString());
		values.put(KEY_BOOK_NAME, updateVO.getBookName());
		values.put(KEY_REGISTER_ID, updateVO.getFdrid().toString());
		values.put(KEY_DOCUMENT_ISICRES_ID, updateVO.getDocId().toString());
		values.put(KEY_IDOC_MISCFORMARID, String.valueOf(updateVO
				.getMiscFormatId()));

		return ISRepositoryManagerHelper.getMapDatosEspecificosValueVO(values);

	}

	/**
	 * Método que nos devuelve los datos completos para obtener un documento
	 *
	 * @param retrieveVO
	 * @return
	 */
	protected ISRepositoryRetrieveDocumentVO getEspecificDocUID(
			ISRepositoryRetrieveDocumentVO retrieveVO) {
		return retrieveVO;
	}

	private ISicresAbstractDocumentVO retrieveDocumentForSign(
			ISRepositorySignDocumentVO signVO, String docUID,
			IsicresBasicConnectorConfigurationVO connectorConfigurationVO)
			throws Exception {

		ISicresBasicDatosEspecificosVO retrieveDatosEspecificosVO = getDatosEspecificosForRetrieveDocument(
				docUID, signVO.getEntidad());

		ISicresBasicDocumentVO iSicresRetrieveDocumentVO = new ISicresBasicDocumentVO();
		iSicresRetrieveDocumentVO.setId(docUID);
		iSicresRetrieveDocumentVO
				.setDatosEspecificos(retrieveDatosEspecificosVO);
		iSicresRetrieveDocumentVO.setConfiguration(connectorConfigurationVO);

		ISicresAbstractDocumentVO documentRetrieveVO = getEspecificRepositoryDataForRetrieve(iSicresRetrieveDocumentVO);
		documentRetrieveVO = connector.retrieve(documentRetrieveVO);

		return documentRetrieveVO;
	}

	private ISicresBasicDocumentVO getSignDocumentForUpdate(
			ISRepositorySignDocumentVO signVO, String docUID, String fileName,
			byte[] signFileContent,
			IsicresBasicConnectorConfigurationVO connectorConfigurationVO)
			throws Exception {
		ISRepositoryUpdateDocumentVO updateVO = new ISRepositoryUpdateDocumentVO();
		updateVO.setBookID(signVO.getBookID());
		updateVO.setBookName(signVO.getBookName());
		updateVO.setBookType(signVO.getBookType());
		updateVO.setDocId(signVO.getDocId());
		updateVO.setFdrid(signVO.getFdrid());
		updateVO.setDocumentUID(docUID);
		updateVO.setEntidad(signVO.getEntidad());
		updateVO.setFileName(fileName);
		updateVO.setMiscFormatId(signVO.getMiscFormatId());

		ISicresBasicDatosEspecificosVO updateDatosEspecificosVO = getDatosEspecificosForUpdateDocument(updateVO);

		ISicresBasicDocumentVO iSicresUpdateDocumentVO = new ISicresBasicDocumentVO();
		iSicresUpdateDocumentVO.setContent(signFileContent);
		iSicresUpdateDocumentVO.setId(docUID);
		iSicresUpdateDocumentVO.setName(fileName);
		iSicresUpdateDocumentVO.setDatosEspecificos(updateDatosEspecificosVO);
		iSicresUpdateDocumentVO.setConfiguration(connectorConfigurationVO);

		return iSicresUpdateDocumentVO;
	}

	private String getFileNameWithExtension(String fileName, String extension) {
		if (StringUtils.isNotBlank(extension)) {
			if (fileName.lastIndexOf(".") >= 0) {
				String currentExtension = fileName.substring(fileName
						.lastIndexOf(".") + 1, fileName.length());

				if (StringUtils.isBlank(currentExtension)) {
					fileName = fileName + extension;
				}
			} else {
				fileName = fileName + "." + extension;
			}
		}

		return fileName;
	}

	/**
	 * Método que actualiza el docuid en la tabla scr_pagerepository
	 *
	 * @param retrieveVO
	 * @return
	 */
	private void updatedDocUID(
			ISRepositoryRetrieveDocumentVO retrieveVO) {
		try {
			DBEntityDAOFactory.getCurrentDBEntityDAO().insertScrPageRepository(
					retrieveVO.getBookID().intValue(),
					retrieveVO.getFdrid().intValue(),
					retrieveVO.getPageID().intValue(), retrieveVO.getDocumentUID(),
					retrieveVO.getEntidad());
		} catch (SQLException e) {
			log.error("Error actualizando el documentUID", e);
		} catch (Exception e) {
			log.error("Error actualizando el documentUID", e);
		}
	}

}
