package es.ieci.tecdoc.isicres.document.connector.alfresco.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.log4j.Logger;

/**
 * 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */
public class MimeTypes {

	private static final Logger log = Logger.getLogger(MimeTypes.class);
	private static final String FILE_MIME_TYPES = "mime.types";
	
	
	private Hashtable extensions = new Hashtable();

	private Hashtable types = new Hashtable();

	public MimeTypes() throws Exception {
		readDefaults();
	}

	private void readDefaults() throws Exception {
		try {
			read(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(FILE_MIME_TYPES)));
		} catch (Exception e) {
			log.error("Error al leer los mimeTypes",e);
			throw e;
		}

	}

	public void read(Reader in) throws Exception{
		BufferedReader reader = new BufferedReader(in);
		String line;

		try {
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				int index = line.indexOf(' ');
				if (index != -1) {
					String mimeType = line.substring(0, index).trim();
					Vector ext = new Vector();
					line = line.substring(index + 1);

					while ((index = line.indexOf(' ')) != -1) {
						String extension = line.substring(0, index);
						ext.add(extension);
						extensions.put(extension, mimeType);
						line = line.substring(index + 1).trim();
					}

					ext.add(line);
					extensions.put(line, mimeType);
					String[] array = new String[0];
					types.put(mimeType, ext.toArray(array));
				}
			}
		} catch (IOException e) {
			log.error(e);
			throw e;
		}
	}

	public String getMimeType(String fileName) {
		String mimeType = null;
		int index = fileName.lastIndexOf('.');
		if (index != -1) {
			mimeType = ((String) extensions.get(fileName.substring(index + 1).toLowerCase()));
		}
		return mimeType;

	}

}
