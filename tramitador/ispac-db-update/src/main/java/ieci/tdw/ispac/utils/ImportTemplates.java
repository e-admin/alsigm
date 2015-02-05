package ieci.tdw.ispac.utils;

import ieci.tdw.ispac.ScriptBase;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.cat.TemplateDataDAO;
import ieci.tdw.ispac.ispaclib.utils.TypeConverter;
import ieci.tdw.ispac.ispaclib.utils.XmlFacade;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;

import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

public class ImportTemplates extends ScriptBase {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(ImportTemplates.class); 


	protected static void checkArguments(String[] args) {
		if ((args == null) || (args.length != 5)) {
			logger.error("Argumentos incorrectos (driverClassName url username password templates-file).");
			System.exit(1);
		}

		try {
			Class.forName(args[0]);
		} catch (ClassNotFoundException cnfe) {
			logger.error("Driver JDBC '" + args[0] + "' no encontrado", cnfe);
			System.exit(1);
		}
	}

	public static void main(String[] args) throws Exception {
		
		checkArguments(args);
		
		ClientContext context = getClientContext(args);
		
		try {
			
			// Iniciar la transacción
			context.beginTX();
			
			importTemplates(context, args[4]);
			
			// Commit de la transacción
			context.endTX(true);

			logger.info("Proceso de importación de plantillas finalizado con éxito");
        	
		} catch (Throwable t) {
			logger.error("Error en el proceso de importación de plantillas", t);

			// Rollback de la transacción
			context.endTX(false);
			
		} finally {
			context.releaseTX();
		}
	}

	protected static void importTemplates(ClientContext context, String fileName) throws Exception {

        if (logger.isInfoEnabled()) {
        	logger.info("Importando el fichero de plantillas: " + fileName);
        }
        
        XmlFacade xml = new XmlFacade(new FileInputStream(fileName));
        
		NodeIterator nodeIt = xml.getNodeIterator("/templates/template"); 
		for (Node node = nodeIt.nextNode(); node != null; node = nodeIt.nextNode()) {
			
			String id = XmlFacade.get(node, "@id");
			int templateId = TypeConverter.parseInt(id, -1);
			if (templateId >= 0) {
				String mimeType = XmlFacade.get(node, "@mimetype");
				String bloqueBase64 = XmlFacade.get(node, "./text()");
				byte[] bloque = Base64.decode(bloqueBase64);
				
				logger.info("Importando plantilla: id=[" + templateId + "], mimetype=[" + mimeType + "], nbytes=[" + bloque.length + "]");
				
				// Importar la plantilla
				TemplateDataDAO.setBLOB(context.getConnection(), templateId, new ByteArrayInputStream(bloque), bloque.length, mimeType);
			}
		}
	}
}
