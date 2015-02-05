package ieci.tecdoc.sgm.admsistema.proceso.utils;

import java.io.File;

import org.apache.log4j.Logger;

/** 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */

public class FileUtils extends org.apache.commons.io.FileUtils {

	private static final Logger logger = Logger.getLogger(FileUtils.class);
	
	public static File ensureExistDirectory(String dirPath) {
		return ensureExistDirectory(new File(dirPath));
	}

	public static File ensureExistDirectory(File dir) {
		
		if (!dir.isDirectory()) {
			if (!dir.mkdir()) {
				ensureExistDirectory(dir.getParentFile());
				if (!dir.mkdir()) {
					logger.error("No se ha podido crear el directorio: " + dir);
				}
			}
		}
		
		return dir;
	}

}
