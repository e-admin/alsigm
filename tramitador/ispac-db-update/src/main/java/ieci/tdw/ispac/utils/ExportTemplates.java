package ieci.tdw.ispac.utils;

import ieci.tdw.ispac.ScriptBase;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.TemplateDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.TemplateDataDAO;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;

public class ExportTemplates extends ScriptBase {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(ExportTemplates.class); 


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
			
			exportTemplates(context, args[4]);
			
        	logger.info("Proceso de exportación de plantillas finalizado con éxito");

		} catch (Throwable t) {
			logger.error("Error en el proceso de exportación de plantillas", t);
		} finally {
			context.releaseTX();
		}
	}

	protected static void exportTemplates(ClientContext context, String fileName) throws Exception {

		CollectionDAO collection = new CollectionDAO(TemplateDataDAO.class);
		collection.query(context.getConnection(), null);
		
		String xml = "<templates>\n";
		
        while (collection.next()) {
        	IItem template = collection.value();
        	
        	ByteArrayOutputStream baos = new ByteArrayOutputStream();
        	TemplateDAO.getTemplate(context.getConnection(),
        			template.getKeyInt(), baos);
        	baos.close();
        	
        	xml += "\t<template id='" + template.getKeyInt()
        		//+ "' nbytes='" + template.getInt("NBYTES")
        		+ "' mimetype='" + template.getString("MIMETYPE")
        		+ "'>"
        		+ new String(Base64.encode(baos.toByteArray())) 
        		+ "</template>\n";
        }
        
        xml += "</templates>\n";

        if (logger.isDebugEnabled()) {
        	logger.debug("Plantillas exportadas:\n" + xml);
        }
        
        File file = new File(fileName);
        if (logger.isInfoEnabled()) {
        	logger.info("Fichero de plantillas: " + file.getAbsolutePath());
        }
        
        OutputStream os = new FileOutputStream(file);
        os.write(xml.getBytes());
        os.close();
	}
}
