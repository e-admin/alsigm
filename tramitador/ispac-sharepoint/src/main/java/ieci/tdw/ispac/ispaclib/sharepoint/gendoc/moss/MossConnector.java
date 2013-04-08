package ieci.tdw.ispac.ispaclib.sharepoint.gendoc.moss;

import ieci.tdw.ispac.api.connector.IDocConnector;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

public class MossConnector implements IDocConnector {
	
	static Logger logger = Logger.getLogger(MossConnector.class);
	//private static MossConnector _instance = null;
	
	private MossHelper moss;
	
	
	public static synchronized MossConnector getInstance(ClientContext ctx) throws ISPACException {
		return new MossConnector(ctx);
	}

	public static synchronized MossConnector getInstance(ClientContext ctx, Object repository) throws ISPACException {
		if (repository instanceof String) {
			return new MossConnector(ctx, (String)repository);
		}else if (repository instanceof Integer) {
			return new MossConnector(ctx, ((Integer)repository).intValue());
		}
		String message = "Error al inicializar el conector, tipo de parametro no permitido";
		logger.error(message);
		throw new ISPACException(message);
	}
	
	


	private MossConnector(ClientContext ctx) throws ISPACException {
		try {
			moss =  new MossHelper(ctx);
			//moss = MossHelper.getInstance();
		} catch (Exception e) {
			String message = "Error al inicializar el conector"+ "[" + e.getMessage() + "]";
			logger.error(message, e);
			throw new ISPACException(message);
		}
	}

	private MossConnector(ClientContext ctx, int repositoryId) throws ISPACException {
		try {
			moss = new MossHelper(ctx, repositoryId);
			//moss = MossHelper.getInstance(repositoryId);
		} catch (Exception e) {
			String message = "Error al inicializar el conector"+ "[" + e.getMessage() + "]";
			logger.error(message, e);
			throw new ISPACException(message);			
		}
	}

	private MossConnector(ClientContext ctx, String repositoryAlias) throws ISPACException {
		try {
			moss = new MossHelper(ctx, repositoryAlias);
			//moss = MossHelper.getInstance(repositoryAlias);
		} catch (Exception e) {
			String message = "Error al inicializar el conector"+ "[" + e.getMessage() + "]";
			logger.error(message, e);
			throw new ISPACException(message);			
		}
	}

	public void closeSession(Object session) throws ISPACException {
		// TODO Apéndice de método generado automáticamente
	}

	public Object createSession() throws ISPACException {
		// TODO Apéndice de método generado automáticamente
		return null;
		
	}

	public void deleteDocument(Object session, String sguid) throws ISPACException {
		try {
			moss.deleteDocument(sguid);
		} catch (Exception e) {		
			String message = "Error al eliminar el documento con id:" + sguid + "[" + e.getMessage() + "]";
			logger.error(message, e);
			throw new ISPACException(message);			
		}
	}
	
	public boolean existsDocument(Object session, String sguid)
	throws ISPACException {
		boolean result = false;
		try {			
			result = moss.existDocument(sguid);
		} catch (Exception e) {
			String message = "Error al verificar la existencia del documento con id:" + sguid+ "[" + e.getMessage() + "]";
			logger.error(message, e);
			throw new ISPACException(message);			
		}					
		return result;
	}

	public int getDocumentSize(Object session, String sguid)
	throws ISPACException {
		int result = 0;
		try {
			result = moss.getDocumentSize(sguid);
		} catch (Exception e) {
			String message = "Error al obtener el tamaño del documento con id:" + sguid+ "[" + e.getMessage() + "]";
			logger.error(message, e);
			throw new ISPACException(message);			
		}					
		return result;
	}

	public String getMimeType(Object session, String sguid)
	throws ISPACException {
		String result = "";
		try {			
			result = moss.getMimeType(sguid);
		} catch (Exception e) {
			String message = "Error al obtener el ContentType del documento con id:" + sguid+ "[" + e.getMessage() + "]";
			logger.error(message, e);
			throw new ISPACException(message);			
		}					
		return result;
	}

	public String getProperties(Object session, String sguid)
	throws ISPACException {	
		String result = "";
		try {			
			result = moss.getFields(sguid);
		} catch (Exception e) {
			String message = "Error al obtener las propiedades del documento con id:" + sguid+ "[" + e.getMessage() + "]";
			logger.error(message, e);
			throw new ISPACException(message);			
		}
		return result;
	}

	public String getRepositoryInfo(Object session, String repId)
	throws ISPACException {
	    String result = "";
	    try {
	    	result = moss.getRepositoryInfo(repId);	    	
	    } catch (Exception e) {
			String message ="Error al obtener la información del repositorio con id:" + repId+ "[" + e.getMessage() + "]";
			logger.error(message, e);
			throw new ISPACException(message);	    	
	    }
	    return result;
	}

	public void setProperty(Object session, String sguid, String name,
			String value) throws ISPACException {
		try
		{	
			moss.updateField(sguid, name, value);
		} catch (Exception e) {
			String message = "Error al establecer el valor del metadato: " + name +"[" + e.getMessage() + "]";
			logger.error(message, e);
			throw new ISPACException(message);			
		}
	}

	public void getDocument(Object session, String sguid, OutputStream out) throws ISPACException {
		try {
			moss.getDocument(sguid, out);
		} catch (Exception e) {
			String message = "Error al obtener el documento con id:" + sguid+ "[" + e.getMessage() + "]";
			logger.error(message, e);
			throw new ISPACException(message);			
		}
	}

	public String newDocument(Object session, InputStream in, int length, String properties) throws ISPACException {
		String result =null;
		try {			
			result = moss.uploadDocument(in, null, length, properties, false);
		} catch (Exception e) {
			String message = "Error al crear un nuevo documento"+ "[" + e.getMessage() + "]";
			logger.error(message, e);
			throw new ISPACException(message);			
		}
		return result;
	}

//	public void setDocument(Object session, String sguid, InputStream in, int length, String properties) throws ISPACException {
//		try {
//			moss.uploadDocument(in, length, properties, true);
//		} catch (Exception e) {
//			throw new ISPACException("Error al modificar el documento con id:" + sguid+ "[" + e.getMessage() + "]");
//		}
//	}

	public String updateDocument(Object session, String sguid, InputStream in, int length, String properties) throws ISPACException {
		try {
			
			String newGUID = moss.uploadDocument(in, sguid, length, properties, true);
			if (!StringUtils.equals(sguid, newGUID))
				return newGUID;
			return null;
		} catch (Exception e) {
			String message = "Error al modificar el documento con id:" + sguid+ "[" + e.getMessage() + "]";
			logger.error(message, e);
			throw new ISPACException(message);			
		}
	}	
	public String getProperty(Object session, String sguid, String property) throws ISPACException {		
		try {
			return moss.getFieldValue(sguid, property);
		} catch (Exception e) {
			//throw new ISPACException("Error al obtener la propiedad " + property + " del documento con id:" + sguid+ "[" + e.getMessage() + "]");
			String message = "Error al obtener la propiedad " + property + " del documento con id:" + sguid+ "[" + e.getMessage() + "]";
			logger.error(message, e);
			throw new ISPACException(message);			
		}
	}

	public void createRepository(Object session) throws ISPACException {
		// TODO Auto-generated method stub
	}
}
