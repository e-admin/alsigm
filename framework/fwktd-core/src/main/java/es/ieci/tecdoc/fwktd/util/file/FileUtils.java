package es.ieci.tecdoc.fwktd.util.file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Utilidades para el tratamiento de ficheros.
 * @author Iecisa
 * @version $Revision$
 *
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

	private static final int BUFFER_SIZE = 4096;

	/**
	 * Obtiene el contenido de un InputStream
	 * @param in InputStream.
	 * @return Binario del contenido.
	 * @throws IOException si ocurre algún error.
	 */
	public static byte[] retrieve(InputStream in) throws IOException {
		return IOUtils.toByteArray(in);
	}

	/**
	 * Copia un contenido.
	 * @param in InputStream origen.
	 * @param out OutputStream destino.
	 * @throws IOException si ocurre algún error.
	 */
	public static void copy(InputStream in, OutputStream out)
			throws IOException {
		try {
			IOUtils.copy(in, out);
			out.flush();
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	/**
	 * Copia un contenido y cierra el OutputStream.
	 * @param in InputStream origen.
	 * @param out OutputStream destino.
	 * @throws IOException si ocurre algún error.
	 */
	public static void copyAndClose(InputStream in, OutputStream out)
			throws IOException {
		try {
			try {
				IOUtils.copy(in, out);
				out.flush();
			} finally {
				IOUtils.closeQuietly(out);
			}
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	/**
	 * Obtiene la extensión de un fichero a partir del nombre del mismo.
	 * @param file Fichero.
	 * @return Extensión del fichero.
	 */
	public static String getExtension(File file) {

		String ext = null;

		if ((file != null) && !file.isDirectory()) {
			ext = getExtension(file.getName());
		}

		return ext;
	}

	/**
	 * Obtiene la extensión de un fichero a partir del nombre del mismo.
	 * @param fileName Nombre del fichero.
	 * @return Extensión del fichero.
	 */
	public static String getExtension(String fileName) {

		String ext = null;

		if (StringUtils.isNotBlank(fileName)) {
			int i = fileName.lastIndexOf('.');
			if (i > 0 && i < fileName.length() - 1) {
				ext = fileName.substring(i + 1).toLowerCase();
			}
		}

		return ext;
	}

}
