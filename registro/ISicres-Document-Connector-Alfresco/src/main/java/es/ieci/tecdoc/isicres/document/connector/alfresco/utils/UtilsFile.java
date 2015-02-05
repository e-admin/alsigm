package es.ieci.tecdoc.isicres.document.connector.alfresco.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.alfresco.webservice.content.Content;
import org.alfresco.webservice.types.ContentFormat;
import org.alfresco.webservice.util.ContentUtils;
import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.document.connector.alfresco.keys.AlfrescoKeys;

/**
 * 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */
public class UtilsFile {
	
	private static final Logger log = Logger.getLogger(UtilsFile.class);
	
	/**
	 * Metodo estico que retorna el array de bytes de un content
	 * @param content
	 * @return
	 * @throws IOException 
	 */
	public static byte[] getByteContent(Content content) throws Exception{
		
		ContentFormat format = content.getFormat();
		String mimeType = format.getMimetype();
		String ext = mimeType.split("/")[1];		
		File fileTemp;
		byte[] contentBytes=null;
		
		try {			
			fileTemp = File.createTempFile(AlfrescoKeys.FILE_TEMP
					, "."
					+ ext);

			// Se convierte el contenido en un fichero temporal
			ContentUtils.copyContentToFile(content, fileTemp);
			
			// Se recupera el inputStream del fichero temporal y se retornan los byte[]
			InputStream viewStream = new FileInputStream(fileTemp);
			contentBytes = ContentUtils.convertToByteArray(viewStream);
			
			// Se borra el fichero temproal
			boolean a = fileTemp.delete();
		} catch (IOException e) {
			log.error("Error en el fichero",e);
			throw e;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
			throw e;
		}
		
		
		return contentBytes;
	}	
}
