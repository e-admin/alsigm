package ieci.tdw.ispac.ispaclib.gendoc.stamp;

import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.gendoc.openoffice.OpenOfficeHelper;
import ieci.tdw.ispac.ispaclib.util.FileTemporaryManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.apache.struts.util.MessageResources;


/**
 * Clase para sellar documentos.
 *
 */
public class StampConnectorImpl implements StampConnector {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(StampConnectorImpl.class);

	
	/**
	 * Constructor.
	 */
	public StampConnectorImpl() {
		super();
	}
	
	/**
	 * Sella un documento.
	 * 
	 * @param ctx
	 *            Contexto de cliente.
	 * @param document
	 *            Información del documento.
	 * @param messageResources
	 *            Mensajes de recursos.
	 * @param response
	 *            Respuesta al cliente.
	 * @return Fichero con el documento sellado.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public File stampDocument(ClientContext ctx, IItem document,
			MessageResources messageResources) throws ISPACException {

    	File docFile = null;
    	File imageFile = null;

		IGenDocAPI genDocAPI = ctx.getAPI().getGenDocAPI();

		// Sesión de gestión documental
		Object connectorSession = null;
		
		try {
			
			// Crear la sesión de gestión documental
			connectorSession = genDocAPI.createConnectorSession();
  		
	  		// Número de registro
	  		String numreg = document.getString("NREG");

	  		// Referencia del documento en el gestor documental
	  		String guid = document.getString("INFOPAG");
	  		
	  		// Sellar si existen datos de registro y documento 
	  		if ((numreg != null) && (guid != null)) {
				if (!genDocAPI.existsDocument(connectorSession, guid)){
					logger.error("No se ha encontrado el documento físico con identificador: '"+guid+"' en el repositorio de documentos");
					throw new ISPACInfo("exception.documents.notExists");
				}
				// Procesar el tipo del fichero anexado al documento
				String mimeType = genDocAPI.getMimeType(connectorSession, guid);
		
				// Sellado limitado a documentos Word
			    if (!"application/msword".equalsIgnoreCase(mimeType)
			    		&& !"application/vnd.oasis.opendocument.text".equalsIgnoreCase(mimeType)
			    		&& !"application/rtf".equalsIgnoreCase(mimeType)) {
			    	throw new ISPACInfo(messageResources.getMessage(ctx.getLocale(), "exception.message.stampWord"));
			    }
		    	
				try {

					// Información de la imagen del sello
					StampImage stampImage = StampImage.getInstance(ctx); 

					// Crear la imagen del sello
					imageFile = stampImage.createStampImage(document, messageResources);

					// Crear el documento a sellar
					docFile = createDocFile(ctx, connectorSession, document);

		            // Sellar el documento con la imagen generada
		            OpenOfficeHelper.getInstance().stampDocument(docFile, imageFile, 
		            		stampImage.getStampWidth(), stampImage.getStampHeight());
		            
				} catch (Throwable e) {
					logger.error("Error al sellar el documento", e);
					throw new ISPACInfo(messageResources.getMessage(ctx.getLocale(), "exception.message.canNotStamp"));
				} finally {
					
				    // Borrar temporales
					if (imageFile != null) {
						imageFile.delete();
					}
					
				}
	  		}
		} finally {
			if (connectorSession != null) {
				genDocAPI.closeConnectorSession(connectorSession);
			}
    	}			  		

		return docFile;
	}
	
	protected static File createDocFile(ClientContext ctx, Object connectorSession, IItem document) throws ISPACException, IOException {
		
		IGenDocAPI genDocAPI = ctx.getAPI().getGenDocAPI();
		
  		// Referencia del documento en el gestor documental
  		String guid = document.getString("INFOPAG");

  		// Referencia del documento en el gestor documental
  		String ext = document.getString("EXTENSION");

       	// Documento temporal para el sellado
		File docFile = FileTemporaryManager.getInstance().newFile("." + ext);
        
        // Generar un documento temporal del documento original
        OutputStream osDocTempFile = new FileOutputStream(docFile);
        genDocAPI.getDocument(connectorSession, guid, osDocTempFile);
        osDocTempFile.close();
        
        return docFile;
	}
	
}
