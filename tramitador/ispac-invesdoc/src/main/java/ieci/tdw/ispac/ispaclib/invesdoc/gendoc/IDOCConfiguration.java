package ieci.tdw.ispac.ispaclib.invesdoc.gendoc;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.XMLUtil.XMLDocUtil;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationHelper;
import ieci.tdw.ispac.ispaclib.messages.MessagesFormatter;
import ieci.tdw.ispac.ispaclib.session.OrganizationUser;
import ieci.tdw.ispac.ispaclib.session.OrganizationUserInfo;
import ieci.tdw.ispac.ispaclib.utils.XPathUtil;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
*  Documento XML que describe las propiedades de un documento.
* 
*	< ?xml version="1.0" encoding=" UTF8"? > 
*	<idoc_config>
*		<default_archive_id>1</default_archive_id>
*		<ctxpool_name>nombre de la agrupación de conexiones</ctxpool_name>
*		<idocuser_id>3</idocuser_id>
*		<temporary_path>path temporal</temporary_path>
*		<archive id="184">
*			<docsize_field_id>2</docsize_field_id>
*			<mimetype_field_id>3</mimetype_field_id>
*    		<fields_mapping>
* 				<field_mapping>
* 					<property_name>nombre</property_name>
* 					<property_type>string,integer,date</property_type>
* 					<field_id>identificador de campo</field_id>
* 				<field_mapping>
* 	 		</<fields_mapping>>
*		</archive>
*  </idoc_config>
**/

class IDOCConfiguration {
	
	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(IDOCConfiguration.class);
	
	private static IDOCConfiguration mInstance = null;

	private static Map mOrganizationInstances;
	private static final String DEFAULT_CONFIG_FILENAME = "IDocStorageCfg.xml";

	private HashMap mArchive = new HashMap();
	
	int mDefaultArchive;
	
	// Pool de conexiones
	private String msPoolName;
	// Identificador del usuario de invesdoc
	private int mUserId;
	// Directorio temporal
	private String msTemporaryPath;

	
	/**
	 * Constructor.
	 *
	 */
	private IDOCConfiguration() {
		super();
		
	}
	
	/**
	 * Obtiene una instancia de la clase.
	 * @return Instancia de la clase.
	 * @throws ISPACException si ocurre algún error.
	 */
	public static synchronized IDOCConfiguration getInstance() 
			throws ISPACException {
		
		OrganizationUserInfo info = OrganizationUser.getOrganizationUserInfo();
		if (info == null){
			if (mInstance == null) {
				mInstance = new IDOCConfiguration();
				mInstance.initiate(DEFAULT_CONFIG_FILENAME);
			}
			return mInstance;
		}else{
			if (mOrganizationInstances == null)
				mOrganizationInstances = new HashMap();
			IDOCConfiguration instance =  (IDOCConfiguration)mOrganizationInstances.get(info.getOrganizationId());
			if (instance == null){
				instance = new IDOCConfiguration();
				instance.initiate(DEFAULT_CONFIG_FILENAME);
				mOrganizationInstances.put(info.getOrganizationId(), instance);
			}
			return instance;
		}
			
	}

	protected void initiate(String configFileName) throws ISPACException {
		
		NodeList archiveList = null;
		NodeList mappingList = null;
		Node property = null;
		Node node = null;

		try
		{
			if (logger.isInfoEnabled()) {
				logger.info("Cargando fichero de configuración: " + configFileName);
			}

			InputStream in = ConfigurationHelper.getConfigFileInputStream(configFileName);

			StreamSource stream = new StreamSource(in);
			Document document = XMLDocUtil.newDocument(stream);

			String sValue = XPathUtil.get(document,
					"/idoc_config/default_archive_id/text()");
			if (sValue == null) {
				throw new ISPACException("Falta el tag <default_archive_id>");
			}
			mDefaultArchive = Integer.parseInt(sValue);
			msPoolName = XPathUtil.get(document, "/idoc_config/ctxpool_name/text()");
			if (msPoolName == null) {
				throw new ISPACException("Falta el tag <ctxpool_name>");
			}
			
			OrganizationUserInfo info = OrganizationUser.getOrganizationUserInfo();
			if (info != null){
				Object[]params = new Object[]{info.getOrganizationId()};
				msPoolName =  MessagesFormatter.format(msPoolName, params);				
			}

			sValue = XPathUtil.get(document, "/idoc_config/idocuser_id/text()");
			if (sValue == null) {
				throw new ISPACException("Falta el tag <idocuser_id>");
			}
			mUserId = Integer.parseInt(sValue);
			msTemporaryPath = XPathUtil.get(document,
					"/idoc_config/temporary_path/text()");
			if (msTemporaryPath == null) {
				throw new ISPACException("Falta el tag <temporary_path>");
			}
			// Lista de archivadores
			archiveList = XPathUtil.getNodeList(document,
					"/idoc_config/archive");
			if (archiveList == null) {
				throw new ISPACException("Falta el tag <archive>");
			}
			for (int i = 0; i < archiveList.getLength(); i++) {
				node = archiveList.item(i);

				String sAttribute = XMLDocUtil.getNodeAttribute(node, "id");
				if (sAttribute == null) {
					throw new ISPACException(
							"Falta el identificador de archivo <archive id='n'>");
				}
				// Identificador del archivador
				int archive = Integer.parseInt(sAttribute);
				
				// Identificador del campo tamaño
				sValue = XPathUtil.get(node, "docsize_field_id/text()");
//				if (sValue == null) {
//					throw new ISPACException(
//							"Falta el tag <docsize_field_id> en el archivador: "
//									+ archive);
//				}
				int sizeField = -1;
				if (sValue != null) {
					sizeField = Integer.parseInt(sValue);
				}
				
				// Identificador del campo mimetype
				sValue = XPathUtil.get(node, "mimetype_field_id/text()");
//				if (sValue == null) {
//					throw new ISPACException(
//							"Falta el tag <mimetype_field_id> en el archivador: "
//									+ archive);
//				}
				int mimetypeField = -1;
				if (sValue != null) {
					mimetypeField = Integer.parseInt(sValue);
				}

				ArchiveInfo archiveInfo = new ArchiveInfo(archive, sizeField,
						mimetypeField);

				// Lista de campos de los archivadores
				mappingList = XPathUtil.getNodeList(node,
						"fields_mapping/field_mapping");
				if (mappingList != null) {
					for (int k = 0; k < mappingList.getLength(); k++) {
						property = mappingList.item(k);
						String sName = XPathUtil.get(property,
								"property_name/text()");
						String sType = XPathUtil.get(property,
								"property_type/text()");

						archiveInfo.setType(sName, sType);

						String sIdentifier = XPathUtil.get(property,
								"field_id/text()");

						archiveInfo.setField(sName, sIdentifier);
					}

					mArchive.put(new Integer(archive), archiveInfo);
				}
			}
			in.close();
		} catch (Exception e) {
			logger.error("Error al inicializar el fichero de configuración: " + configFileName, e);
			throw new ISPACException(e);
		}
	}

	public int getUserId() {
		return mUserId;
	}

	public String getPoolName() {
		return msPoolName;
	}

	public String getTemporaryPath() {
		return msTemporaryPath;
	}

	public int getDefaultArchive() {
		return mDefaultArchive;
	}

	public int getSizeField(int archive) {
		ArchiveInfo info = (ArchiveInfo) mArchive.get(new Integer(archive));
		if (info != null) {
			return info.getSizeField();
		}
		return -1;
	}

	public int getMimetypeField(int archive) {
		ArchiveInfo info = (ArchiveInfo) mArchive.get(new Integer(archive));
		if (info != null) {
			return info.getMimetypeField();
		}
		return -1;
	}

	public int getField(int archive, String key) {
		ArchiveInfo info = (ArchiveInfo) mArchive.get(new Integer(archive));
		if (info != null) {
			return info.getField(key);
		}
		return -1;
	}

	public Map getFields(int archive) {
		ArchiveInfo info = (ArchiveInfo) mArchive.get(new Integer(archive));
		if (info != null) {
			return info.getFields();
		}
		return null;
	}

	public String getType(int archive, String key) {
		ArchiveInfo info = (ArchiveInfo) mArchive.get(new Integer(archive));
		if (info != null) {
			return info.getType(key);
		}
		return null;
	}

	protected class ArchiveInfo {
		private HashMap mType = new HashMap();

		private HashMap mField = new HashMap();

		private int mArchive;

		// Identificador del campo asociado al tamaño del documento
		private int mSizeField;

		// Identificador del campo asociado al mimetype del documento
		private int mMimetypeField;

		public ArchiveInfo(int archive, int sizeField, int mimetypeField) {
			mArchive = archive;
			mSizeField = sizeField;
			mMimetypeField = mimetypeField;
		}

		public int getArchive() {
			return mArchive;
		}

		public int getSizeField() {
			return mSizeField;
		}

		public int getMimetypeField() {
			return mMimetypeField;
		}

		public int getField(String key) {
			String sValue = (String) mField.get(key);
			if (sValue != null)
				return Integer.parseInt(sValue);
			return -1;
		}

		public void setField(String key, String Value) {
			mField.put(key, Value);
		}

		public String getType(String key) {
			return (String) mType.get(key);
		}

		public void setType(String key, String Value) {
			mType.put(key, Value);
		}

		public Map getFields() {
			return mField;
		}
	}
}
