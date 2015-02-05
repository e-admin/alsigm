package ieci.tdw.ispac.ispaclib.gendoc;

import ieci.tdw.ispac.api.connector.IDocConnector;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.ClassLoaderUtil;

import org.apache.log4j.Logger;


/**
 * Factoría para obtener el conector documental.
 */
public class DMConnectorFactory {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(DMConnectorFactory.class);
	
	private static final int NONE = 0;
	private static final int FILESYSTEM = 1;
	private static final int DOCUMENTUM = 2;
	private static final int DATABASE = 3;
	private static final int FTPSERVER = 4;
	private static final int INVESDOC = 5;
	private static final int SHAREPOINT = 6;
	private static final int ALFRESCO = 7;

	private static int mConnector = NONE;
	static {
		try {
			String manager = ISPACConfiguration.getInstance().get(ISPACConfiguration.CONNECTOR_MANAGER);

			if (manager.equalsIgnoreCase("FileSystem")) {
				mConnector = FILESYSTEM;
			} else if (manager.equalsIgnoreCase("Documentum")) {
				mConnector = DOCUMENTUM;
			} else if (manager.equalsIgnoreCase("DataBase")) {
				mConnector = DATABASE;
			} else if (manager.equalsIgnoreCase("FTPServer")) {
				mConnector = FTPSERVER;
			} else if (manager.equalsIgnoreCase("invesDoc")) {
				mConnector = INVESDOC;
			} else if (manager.equalsIgnoreCase("sharepoint")) {
				mConnector = SHAREPOINT;
			} else if (manager.equalsIgnoreCase("alfresco")) {
				mConnector = ALFRESCO;
			} else {
				throw new ISPACException("No se ha indicado ningún gestor de almacenamiento");
			}

		} catch (Exception e) {
			logger.error("Error al parsear el gestor de almacenamiento", e);
		}
	}
	
	/**
	 * Contexto de cliente.
	 */
	private ClientContext ctx = null;
	
	/**
	 * Constructor
	 * @param ctx Contexto de cliente
	 */
	private DMConnectorFactory(ClientContext ctx) {
		this.ctx = ctx;
	}

	/**
	 * Obtiene una instancia de la factoría.
	 * @param ctx Contexto de cliente.
	 * @return Instancia de la factoría.
	 */
	public static synchronized DMConnectorFactory getInstance(ClientContext ctx) {
		return new DMConnectorFactory(ctx);
	}

	public IDocConnector getConnector(String sProperties) throws ISPACException {
		return null;
	}

	public IDocConnector getConnector() throws ISPACException {

		switch (mConnector) {
		
			case INVESDOC:
				return (IDocConnector) ClassLoaderUtil.executeStaticMethod(
					"ieci.tdw.ispac.ispaclib.invesdoc.gendoc.InvesDocConnector",
					"getInstance", null);
				
			case ALFRESCO:
				return (IDocConnector) ClassLoaderUtil.executeStaticMethod(
						"ieci.tdw.ispac.ispaclib.alfresco.doc.AlfrescoConnector",
						"getInstance", new Object[] { ctx });

			case SHAREPOINT:
				return (IDocConnector) ClassLoaderUtil.executeStaticMethod(
						"ieci.tdw.ispac.ispaclib.sharepoint.gendoc.moss.MossConnector",
						"getInstance", new Object[] { ctx });
	
			case DOCUMENTUM :
				return (IDocConnector) ClassLoaderUtil.executeStaticMethod(
					"ieci.tdw.ispac.ispaclib.documentum.gendoc.DocumentumConnector",
					"getInstance", null);
				
			case FILESYSTEM:
				return FileConnector.getInstance();
				
			case DATABASE :
				return DataBaseConnector.getInstance();
				
			case FTPSERVER:
				return FTPConnector.getInstance();
		}

		return null;
	}
	public IDocConnector getConnector (Object obj) throws ISPACException {
		
		switch (mConnector) {
		
			case INVESDOC:
				return (IDocConnector) ClassLoaderUtil.executeStaticMethod(
					"ieci.tdw.ispac.ispaclib.invesdoc.gendoc.InvesDocConnector",
					"getInstance", new Object[] { (Integer)obj });
				
			case ALFRESCO:
				return (IDocConnector) ClassLoaderUtil.executeStaticMethod(
						"ieci.tdw.ispac.ispaclib.alfresco.doc.AlfrescoConnector",
						"getInstance", new Object[] { ctx, obj });

			case SHAREPOINT:
				return (IDocConnector) ClassLoaderUtil.executeStaticMethod(
						"ieci.tdw.ispac.ispaclib.sharepoint.gendoc.moss.MossConnector",
						"getInstance", new Object[] { ctx, obj });
			case FILESYSTEM:
				return FileConnector.getInstance();
				
			case DOCUMENTUM :
				return (IDocConnector) ClassLoaderUtil.executeStaticMethod(
					"ieci.tdw.ispac.ispaclib.documentum.gendoc.DocumentumConnector",
					"getInstance", null);
				
			case DATABASE :
				return DataBaseConnector.getInstance();
				
			case FTPSERVER:
				return FTPConnector.getInstance();
		}

		return null;
	}
	
	
}