package es.ieci.tecdoc.fwktd.util.mime;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;

import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.detector.ExtensionMimeDetector;
import eu.medsea.mimeutil.detector.MagicMimeMimeDetector;

/**
 * Clase de utilidad para detección de MIME Media Types. Envuelve a la clase
 * <code>MimeUtil</code> de la librería
 * <i>mime-util[http://sourceforge.net/projects/mime-util]</i> utilizando las
 * clases <code>MagicMimeMimeDetector</code> y
 * <code>ExtensionMimeDetector</code>
 * 
 * @see eu.medsea.mimeutil.MimeUtil
 * @see MagicMimeMimeDetector
 * @see ExtensionMimeDetector
 * 
 * @author IECISA
 * 
 */
public class MimeUtil {

	static {
		eu.medsea.mimeutil.MimeUtil
				.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
		eu.medsea.mimeutil.MimeUtil
				.registerMimeDetector("eu.medsea.mimeutil.detector.ExtensionMimeDetector");
	}

	/**
	 * Devuelve el MIME Media Type asociado a la extensión
	 * <code>extension</code>.
	 * 
	 * @param extension
	 *            extensión para la que se quiere recuperar su MIME
	 * @return
	 */
	public static String getExtensionMimeType(String extension) {
		return getMimeType("file." + extension);
	}

	/**
	 * Devuelve el MIME Media Type para el fichero de nombre
	 * <code>fileName</code>.
	 * 
	 * @param fileName
	 *            nombre del fichero para el que se quiere recuperar su MIME
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getMimeType(String fileName) {
		Collection<MimeType> mimeTypes = eu.medsea.mimeutil.MimeUtil
				.getMimeTypes(fileName.toLowerCase());
		return getMostSpecificMimeType(mimeTypes);
	}

	/**
	 * Devuelve el MIME Media Type para el fichero <code>file</code>.
	 * 
	 * @param file
	 *            instancia de <code>java.io.File</code> para el que se quiere
	 *            recuperar su MIME
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getMimeType(File file) {
		Collection<MimeType> mimeTypes = eu.medsea.mimeutil.MimeUtil
				.getMimeTypes(file);
		return getMostSpecificMimeType(mimeTypes);
	}

	/**
	 * Devuelve el MIME Media Type para el array de bytes <code>file</code>. Es
	 * un método útil cuando se quiere saber el MIME conociendo el contenido de
	 * un fichero.
	 * 
	 * @param file
	 *            array de bytes para el que se quiere conocer su MIME
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getMimeType(byte[] file) {
		Collection<MimeType> mimeTypes = eu.medsea.mimeutil.MimeUtil
				.getMimeTypes(file);
		return getMostSpecificMimeType(mimeTypes);
	}

	/**
	 * Devuelve el MIME Media Type asociado a la instancia de
	 * <code>java.io.InputStream</code> <code>inputStream</code>.
	 * 
	 * @param inputStream
	 *            instancia de <code>java.io.InputStream</code> para la que
	 *            recuperar su MIME
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getMimeType(InputStream inputStream) {
		Collection<MimeType> mimeTypes = eu.medsea.mimeutil.MimeUtil
				.getMimeTypes(inputStream);
		return getMostSpecificMimeType(mimeTypes);
	}

	/**
	 * Devuelve el tipo MIME más específico de los que hay en la colección que
	 * se recibe como parámetro.
	 * 
	 * @param mimeTypes
	 *            colección de tipos MIME
	 * @return
	 */
	protected static String getMostSpecificMimeType(
			Collection<MimeType> mimeTypes) {

		String mostSpecificMimeType = null;

		if ((mimeTypes != null) && !mimeTypes.isEmpty()) {
			MimeType mimeType = eu.medsea.mimeutil.MimeUtil
					.getMostSpecificMimeType(mimeTypes);
			if (mimeType != null) {
				mostSpecificMimeType = mimeType.toString();
			}
		}

		return mostSpecificMimeType;
	}
}
