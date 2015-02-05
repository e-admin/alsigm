package ieci.tdw.ispac.ispaclib.invesdoc.gendoc;

import ieci.tdw.ispac.api.connector.IDocConnector;
import ieci.tdw.ispac.api.connector.Properties;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.ispaclib.invesdoc.gendoc.api.Volumes;
import ieci.tdw.ispac.ispaclib.utils.MimetypeMapping;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;
import ieci.tecdoc.core.db.DbConnectionConfig;
import ieci.tecdoc.core.db.DbError;
import ieci.tecdoc.core.exception.IeciTdException;
import ieci.tecdoc.idoc.admin.api.volume.Volume;
import ieci.tecdoc.sbo.idoc.api.Archive;
import ieci.tecdoc.sbo.idoc.api.ArchiveObject;
import ieci.tecdoc.sbo.idoc.api.Folder;
import ieci.tecdoc.sbo.idoc.api.FolderDocumentObject;
import ieci.tecdoc.sbo.idoc.api.FolderObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

public class InvesDocConnector implements IDocConnector
{
	
	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(InvesDocConnector.class);
	
	protected int mArchiveId;
	protected int mDefaultArchiveId;
	protected int mUserId;
	protected String msPoolName = null;
	// Gestor de ficheros temporales
	protected InvesDocTemporaryManager mFileTemporaryManager = null; 
	protected String msFileTemporaryPath = null;

	protected DbConnectionConfig mDbCnt;
	protected Archive mArchiveAPI;
	protected Folder mFolderAPI;
	
	DateFormat mDateFormat = new SimpleDateFormat("dd-MM-yyyy");

	protected  int BUFFER_SIZE = 4096;	
	
	
	/**
	 * Construye el conector con invesFlow.
	 * Lee los parámetros del fichero de configuración.
	 * @throws ISPACException si se ha producido algún error 
	 */
	private InvesDocConnector() throws ISPACException {
		initiate();
	}

	private InvesDocConnector(Integer archive) throws ISPACException {
		initiate();
		
		if (archive != null) {
			mArchiveId = archive.intValue();
		}
	}

	protected void initiate() 
	throws ISPACException
	{
		IDOCConfiguration parameters = IDOCConfiguration.getInstance();

		mDefaultArchiveId = parameters.getDefaultArchive();
		mArchiveId = mDefaultArchiveId; 
		mUserId = parameters.getUserId();
		msPoolName = parameters.getPoolName();
		// Obtiene el gestor de ficheros temporales
		mFileTemporaryManager = InvesDocTemporaryManager.getInstance();
		msFileTemporaryPath = mFileTemporaryManager.getFileTemporaryPath();

		try
		{
			mDbCnt = new DbConnectionConfig( msPoolName, null, null);
			mArchiveAPI = new Archive();
		    mArchiveAPI.setConnectionConfig(mDbCnt);
		    mFolderAPI = new Folder();
		    mFolderAPI.setConnectionConfig(mDbCnt);
		} 
		catch (Exception e)
		{
			logger.error("Error al iniciar el conector documental", e);
			throw new ISPACException(e);
		}
	}

	/**
	 * Comprueba si existe el documento.
	 * @param sGUID identificador del documento.
	 * @throws ISPACException si se ha producido algún error
	 */
	public boolean existsDocument(Object session, String sGUID) 
	throws ISPACException
	{
		boolean exists = true;
		
		try
		{
			IDOCGUID guid = new IDOCGUID(sGUID);

			ArchiveObject archive = mArchiveAPI.loadArchive(null, guid.getArchive());
            mFolderAPI.loadFolder(null, mUserId, archive, guid.getFolder());
		    //FolderObject folder = mFolderAPI.loadFolder(null, mUserId, archive, guid.getFolder());  
		} 
		catch (Exception e)
		{
			exists = false;
		}
		
		return exists;
	}

	/**
	 * Descarga el contenido de un documento en un stream de salida.
	 * @param sGUID identificador del documento
	 * @param out stream de salida
	 * @throws ISPACException si se ha producido algún error
	 */
	public void getDocument(Object session, String sGUID, OutputStream out) 
	throws ISPACException
	{
		File file = null;
		
		try
		{
			IDOCGUID guid = new IDOCGUID(sGUID);
			
			ArchiveObject archive = mArchiveAPI.loadArchive(null, guid.getArchive());
			FolderObject folder = mFolderAPI.loadFolder(null, mUserId, archive, guid.getFolder());  

		    int documentId = guid.getDocument();
		    
		    File path = new File( msFileTemporaryPath);
		    file = mFolderAPI.retrieveFolderDocumentFile( null, archive, folder, path, documentId);
		    
		    FileInputStream is = new FileInputStream( file);
	        byte buffer[] = new byte[BUFFER_SIZE];
	        int length = -1;
            while (true)
            {
	            length = is.read(buffer);
	            if (length == -1) break;
	            out.write(buffer, 0, length);
	        }
            is.close();
		} 
		catch (Exception e)
		{
			logger.error("Error al obtener el contenido del documento [" + sGUID + "]", e);
			throw new ISPACException(e);
		}
		finally
		{
			// Elimina el fichero intermedio
			if (file != null) file.delete();
		}
	}

	/**
	 * Crea un nuevo documento. 
	 * Para ello genera una nueva carpeta en el archivador invesDoc
	 * y un documento en el que salva el contenido del stream de 
	 * entrada.
	 * @param in stream de entrada
	 * @param properties propiedades del documento.
	 * @return identificador del documento como un string
	 * xml con el formato:
	 * 
	 * <guid><folder/><document/></guid>
	 * 
	 * @throws ISPACException si se ha producido algún error
	 */
	public String newDocument(Object session, InputStream in, int length, String sProperties) 
	throws ISPACException
	{
		String sGUID = null;
		String sFileName = null;
		
		try
		{

			IDOCConfiguration parameters = IDOCConfiguration.getInstance();

			ArchiveObject archive = mArchiveAPI.loadArchive(null, mArchiveId);
		    FolderObject folder = mFolderAPI.newFolder( null, archive);  

		    String sExt = setFolderFieldValues( mArchiveId, folder, sProperties);
			
			// Salva el contenido del input stream en un fichero intermedio
		    // Salva el contenido del input stream en un fichero intermedio
		    if (length > 0)
		    	sFileName = mFileTemporaryManager.put(in,length);
		    else
				sFileName = mFileTemporaryManager.put(in);
		    
			String sPath = msFileTemporaryPath + "/" + sFileName;
			
			int sizeField = parameters.getSizeField( mArchiveId);

		    File file = new File( sPath);
		    int size = (int) file.length();
			folder.setFieldValue( sizeField, new Integer(size));
			
			
			// Añade el fichero como raíz a la carpeta
			folder.addRootDocument( "Documento", sExt, sPath);
			
			mFolderAPI.createFolder( mUserId, archive, folder);
			
			// Obtiene el identificador el documento
			int documentId = folder.getAllDocuments().get(0).getId();
			
			sGUID  = XmlTag.newTag("archive", archive.getId());
			sGUID += XmlTag.newTag("folder", folder.getId());
			sGUID += XmlTag.newTag("document", documentId);
			sGUID  = XmlTag.newTag("guid", sGUID);
		} 
		catch (Exception e)
		{
			logger.error("Error al crear el documento", e);
			throw new ISPACException(e);
		}
		finally
		{
			// Elimina el fichero intermedio
			if (sFileName != null)
			{
				mFileTemporaryManager.delete(sFileName);
			}
		}
		
		return sGUID;
	}

	/**
	 * Cambia el contenido de un documento.
	 * @param sGUID identificador del documento
	 * @param in stream de entrada
	 * @throws ISPACException si se ha producido algún error
	 */
	public String updateDocument(Object session, String sGUID, InputStream in, int length, String sProperties)
	throws ISPACException
	{
		String sFileName = null;
		
		ArchiveObject archive = null;
		FolderObject folder = null;
		
		try
		{
			IDOCConfiguration parameters = IDOCConfiguration.getInstance();

			IDOCGUID guid = new IDOCGUID(sGUID);
			
			archive = mArchiveAPI.loadArchive(null, guid.getArchive());
		    // Bloquea la carpeta
		    mFolderAPI.editFolder(null, mUserId, archive, guid.getFolder());

		    folder = mFolderAPI.loadFolder(null, mUserId, archive, guid.getFolder());
			
		    String sExt = setFolderFieldValues( guid.getArchive(), folder, sProperties);

		    // Salva el contenido del input stream en un fichero intermedio
		    if (length > 0)
		    	sFileName = mFileTemporaryManager.put(in,length);
		    else
				sFileName = mFileTemporaryManager.put(in);
		    
			String sPath = msFileTemporaryPath + "/" + sFileName;
			
			// Cambia el tamaño del documento
		    File file = new File( sPath);
		    int size = (int) file.length();
		    int sizeField = parameters.getSizeField( guid.getArchive());
		    
			folder.setFieldValue( sizeField, new Integer(size));
			
			// Cambia el contenido del documento. 
			// Se mantiene el identificador del documento
			int documentId = guid.getDocument();
			FolderDocumentObject document = folder.getDocument(documentId);
			
			document.replaceFile(sPath,sExt);
			
			mFolderAPI.storeFolder(mUserId, archive, folder);
		} 
		catch (Exception e)
		{
			logger.error("Error al modificar el documento [" + sGUID + "]", e);
			throw new ISPACException(e);
		}
		finally
		{
			try
			{
				// Desbloquea la carpeta
				if (folder != null)
				{
					mFolderAPI.terminateEditFolder(mUserId, archive, folder.getId());
				}
			}
			catch (Exception e)
			{
				logger.error("Error al desbloquear la carpeta", e);
			}
		}
		return sGUID;
	}
	
	/**
	 * Elimina un documento.
	 * @param sGUID identificador del documento
	 * @throws ISPACException si se ha producido algún error
	 */
	public void deleteDocument(Object session, String sGUID) 
	throws ISPACException
	{
		try
		{
			IDOCGUID guid = new IDOCGUID(sGUID);
			
			ArchiveObject archive = mArchiveAPI.loadArchive(null, guid.getArchive());
		    // Elimina la carpeta
		    mFolderAPI.removeFolder(null, mUserId, archive, guid.getFolder());
		} 
		catch (Exception e)
		{
			if (e instanceof IeciTdException) {
				
				IeciTdException itde = (IeciTdException) e;
				
				// Documento no encontrado (ya eliminado)
				if (itde.getErrorCode().equals(DbError.EC_NOT_FOUND)) {
					return;
				}
			}
			
			logger.error("Error al eliminar el documento", e);
			throw new ISPACException(e);
		}
	}

	/**
	 * Obtiene el mimetype de un documento.
	 * @param sGUID identificador del documento
	 * @return el string que describe el mimetype
	 * @throws ISPACException si se ha producido algún error.
	 */
	public String getMimeType(Object session, String sGUID)
	throws ISPACException {
		
		String sMimeType = null;
		
		try
		{
			IDOCConfiguration parameters = IDOCConfiguration.getInstance();

			IDOCGUID guid = new IDOCGUID(sGUID);
			
			ArchiveObject archive = mArchiveAPI.loadArchive(null, guid.getArchive());
		    FolderObject folder = mFolderAPI.loadFolder(null, mUserId, archive, guid.getFolder());  
			
		    int mimetypeField = parameters.getMimetypeField( guid.getArchive());
		    if (mimetypeField > 0) {
		    	sMimeType = (String) folder.getFieldValue(mimetypeField);
		    } else {
		    	FolderDocumentObject document = folder.getDocument(guid.getDocument());
		    	sMimeType = MimetypeMapping.getMimeType(document.getFileExt());
		    }
		} 
		catch (Exception e)
		{
			if (e instanceof IeciTdException) {
				
				IeciTdException itde = (IeciTdException) e;
				
				// Documento no encontrado
				if (itde.getErrorCode().equals(DbError.EC_NOT_FOUND)) {
					
					throw new ISPACInfo("exception.documents.file.notFound");
				}
			}
			
			logger.error("Error al obtener el tipo MIME del documento", e);
			throw new ISPACException(e);
		}
		
		return sMimeType;
	}
	/**
	 * Obtiene el tamaño del documento.
	 * @param sGUID identificador del documento
	 * @return el tamaño del documento
	 * @throws ISPACException si se ha producido algún error
	 */
	public int getDocumentSize(Object session, String sGUID) 
	throws ISPACException
	{
		int size = -1;
		
		try
		{
			IDOCConfiguration parameters = IDOCConfiguration.getInstance();

			IDOCGUID guid = new IDOCGUID(sGUID);
			
			ArchiveObject archive = mArchiveAPI.loadArchive(null, guid.getArchive());
		    FolderObject folder = mFolderAPI.loadFolder(null, mUserId, archive, guid.getFolder());  
			
		    int sizeField = parameters.getSizeField( guid.getArchive());
		    if (sizeField > 0) {
		    	size = ((Integer) folder.getFieldValue(sizeField)).intValue();
		    } else {
				size = mFolderAPI.getFileSize(null, archive, folder, guid.getDocument());
		    }
		} 
		catch (Exception e)
		{
			logger.error("Error al obtener el tamaño del documento", e);
			throw new ISPACException(e);
		}
		
		return size;
	}
	
	public void createRepository(Object session) 
	throws ISPACException
	{
		/*
		Campos del archivador
		
		documentid integer
		documenttype integer
		documentname string(100)
		procedureid integer
		procedurename string(100)
		expedientid integer
		expedientname string(100)
		userguid string(250)
		username string(250)
		stageid integer
		stagename string(100)
		taskid integer
		taskname string(100)
		mimetype string(100)
		documentsize integer
		sign
		*/
	}
	
	public String getProperties(Object session, String sGUID)
	throws ISPACException
	{
		String sXML = null;
		String sTag;
		String sProperty;
		String sProperties = "";

		try
		{
			IDOCConfiguration parameters = IDOCConfiguration.getInstance();

			IDOCGUID guid = new IDOCGUID(sGUID);

			ArchiveObject archive = mArchiveAPI.loadArchive(null, guid.getArchive());
		    FolderObject folder = mFolderAPI.loadFolder(null, mUserId, archive, guid.getFolder());

			Iterator iterator = parameters.getFields(guid.getArchive()).entrySet().iterator();

			while (iterator.hasNext())
			{
				String value;
				
				Entry entry = (Entry) iterator.next();
				String name = (String) entry.getKey();
				int field = Integer.parseInt( (String) entry.getValue());
				
				Object object = folder.getFieldValue( field);
				
				if (object != null) {
					if (object instanceof Date)
						value = mDateFormat.format( (Date) object);
					else
					    value = object.toString();
				}
				else {
					value = "";
				}
				
				sTag  = XmlTag.newTag("name", name);
//				sTag += XmlTag.newTag("value", value);
				sTag += XmlTag.newTag("value", XmlTag.newCDATA(value));

				
				sProperty = XmlTag.newTag("property", sTag);
				sProperties += sProperty;
				
			}

			sXML = XmlTag.getXmlInstruction("ISO-8859-1")
				 + XmlTag.newTag("doc_properties", sProperties);
		} 
		catch (Exception e)
		{
			logger.error("Error al obtener las propiedades del documento", e);
			throw new ISPACException(e);
		}
		
		return sXML;
	}

	protected String setFolderFieldValues(int archive, FolderObject folder, String sProperties) throws ISPACException {

		String sField;
		String sValue;
		int field;
		String sType;
		Object object;
		String sMimetype = null;

		try
		{
			Properties properties = new Properties(sProperties);
			IDOCConfiguration parameters = IDOCConfiguration.getInstance();
			
			int mimetypeField = parameters.getMimetypeField(archive);
	
			HashMap property = properties.getProperties();
			
			Iterator iterator = property.entrySet().iterator();
	
			while (iterator.hasNext())
			{
				Entry entry = (Entry) iterator.next();
				
				sField = (String) entry.getKey();
				if ((field = parameters.getField( archive, sField)) != -1)
				{
					sValue = (String) entry.getValue();
					if (sValue != null)
					{
						if ((sType = parameters.getType( archive, sField)) != null)
						{
							if (field == mimetypeField)
							{
								sMimetype = sValue;
							}
							
							object = null;
							
							if (sType.equalsIgnoreCase("string"))
								object = sValue;
							else if (sType.equalsIgnoreCase("integer"))
								object = new Integer( sValue);
							else if (sType.equalsIgnoreCase("decimal"))
								object = new Double( sValue);
							else if (sType.equalsIgnoreCase("date"))
								object = mDateFormat.parse( sValue);
							
							if (object != null)
							{
								folder.setFieldValue( field, object);
							}
						}
					}
				}
			}
		} 
		catch (Exception e)
		{
			logger.error("Error al establecer las propiedades del documento", e);
			throw new ISPACException(e);
		}
		
		return MimetypeMapping.getExtension(sMimetype);
	}

	public void setProperty(Object session, String sGUID, String name, String value) throws ISPACException {

		//Actualiza la propiedad
		String str  = XmlTag.newTag("name", name);
		str += XmlTag.newTag("value", XmlTag.newCDATA(value));
		String property = XmlTag.newTag("property", str);
		property = XmlTag.getXmlInstruction("ISO-8859-1") + XmlTag.newTag("doc_properties", property );
		
		
		IDOCGUID guid = new IDOCGUID(sGUID);

		ArchiveObject archive = null;
		FolderObject folder = null;
		try {
			archive = mArchiveAPI.loadArchive(null, guid.getArchive());
			// Bloquea la carpeta
			mFolderAPI.editFolder(null, mUserId, archive, guid.getFolder());

			folder = mFolderAPI.loadFolder(null, mUserId, archive, guid
					.getFolder());

			setFolderFieldValues(guid.getArchive(), folder, property);

			mFolderAPI.storeFolder(mUserId, archive, folder);			
		} catch (Exception e) {
			logger.error("Error al establecer la propiedad [" + name + "] del documento", e);
			throw new ISPACException(e);
		} finally {
			try {
				// Desbloquea la carpeta
				if (folder != null) {
					mFolderAPI.terminateEditFolder(mUserId, archive, folder
							.getId());
				}
			} catch (Exception e) {
				logger.error("Error al desbloquear la carpeta", e);
			}
		}
	}

	public String getProperty(Object session, String sGUID, String property)
			throws ISPACException {
		String value;
		try {
			IDOCConfiguration parameters = IDOCConfiguration.getInstance();
			IDOCGUID guid = new IDOCGUID(sGUID);
			ArchiveObject archive = mArchiveAPI.loadArchive(null, guid
					.getArchive());
			FolderObject folder = mFolderAPI.loadFolder(null, mUserId, archive,
					guid.getFolder());
			int field = parameters.getField(archive.getId(), property);
			Object object = folder.getFieldValue(field);
			if (object != null) {
				if (object instanceof Date)
					value = mDateFormat.format((Date) object);
				else
					value = object.toString();
			} else {
				value = "";
			}
		} catch (Exception e) {
			logger.error("Error al obtener la propiedad [" + property + "] del documento", e);
			throw new ISPACException(e);
		}
		return value;
	}
	
	/**
	 * Obtiene la información de un repositorio.
	 * @param repId Identificador del repositorio.
	 * @return XML con la información del repositorio.
	 * @throws ISPACException si ocurre algún error.
	 */
	public String getRepositoryInfo(Object session, String repId) throws ISPACException {
		
		StringBuffer xml = new StringBuffer();
		StringBuffer properties = new StringBuffer();
		int length = 0;
		int size = 0;
		int files = 0;

		try {

			// Cargar la configuración de invesDoc
			IDOCConfiguration.getInstance();
			
			// Obtener la información del archivador
			int archiveId = TypeConverter.parseInt(repId);
			ArchiveObject archive = mArchiveAPI.loadArchive(null, archiveId);
			if (archive != null) {
				
				// Identificador de la lista de volúmenes asociada al archivador
				int volListId = archive.getArchiveToken().getMisc()
					.getVolListId();
				
				// Obtener los volúmenes de la lista de volúmenes
				Volumes vols = new Volumes();
				vols.setConnectionConfig(mDbCnt);
				vols.loadByVolumeList(volListId);
				
				// Calcular la información del repositorio
				Volume vol;
				String actSize;
				for (int i = 0; i < vols.count(); i++) {
					vol = vols.getVolume(i);
					
					// Espacio total del volumen
					length += TypeConverter.parseInt(vol.getMaxSize(), 0);
					
					// Espacio ocupado en el volumen
					actSize = vol.getActSize();
					if (actSize != null) {
						int pos = actSize.indexOf(".");
						if (pos > 0) {
							actSize = actSize.substring(0, pos);
						}
					}
					size += TypeConverter.parseInt(actSize, 0);
					
					// Número de ficheros en el volumen
					files += vol.getNumFiles();
				}
			}

			// Componer la información del XML
			properties.append(XmlTag.newTag("length", length));
			properties.append(XmlTag.newTag("size", size));
			properties.append(XmlTag.newTag("files", files));

			// Componer el XML completo
			xml.append(XmlTag.getXmlInstruction("ISO-8859-1"));
			xml.append(XmlTag.newTag("repository", properties.toString(), 
						archiveId));
			
		}  catch (Exception e) {
			logger.error("Error al obtener la información del repositorio", e);
			throw new ISPACException(e);
		}

		return xml.toString();
	}

	public Object createSession() throws ISPACException {
		return null;
	}

	public void closeSession(Object session) throws ISPACException {
		
	}

	public static synchronized InvesDocConnector getInstance() throws ISPACException {
		return new InvesDocConnector();
	}

	public static synchronized InvesDocConnector getInstance(Integer archive) throws ISPACException {
		return new InvesDocConnector(archive);
	}
	
}
