/**
 * 
 */
package com.ieci.tecdoc.common.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;

import com.ieci.tecdoc.common.exception.BookException;

/**
 * @author 66575267
 *
 *
 *         Clase que contiene metodos para automatizar las operaciones mas
 *         frecuentes con fichero que se realizan en la operacion
 */
public class ISFileUtil {

	/**
	 * Este metodo nos devuelve si el fichero a adjuntar contiene datos.
	 *
	 * Si el contenido o el path del fichero contienen valor, devolvera true.
	 *
	 * Si el contenido y el path son null y el uid contiene valor devolvera
	 * false.
	 *
	 * Si no contienen datos ni el contenido, ni el path, ni el uid se lanzara
	 * una excepcion
	 *
	 * @param content
	 * @param filePath
	 * @param uid
	 * @return
	 * @throws BookException
	 */
	public static boolean isFileWithContent(byte[] content, String filePath,
			String uid) throws BookException {
		if (content != null && content.length > 0) {
			return true;
		} else if (StringUtils.isNotBlank(filePath)) {
			return true;
		} else if (StringUtils.isNotBlank(uid)) {
			return false;
		}

		throw new BookException(BookException.ERROR_CANNOT_SAVE_FILE);
	}

	public static InputStream getInputStream(byte[] content, String filePath)
			throws FileNotFoundException {
		InputStream inputStream = null;
		if (content != null && content.length > 0) {
			inputStream = new ByteArrayInputStream(content);
		} else if (StringUtils.isNotEmpty(filePath)) {
			inputStream = new FileInputStream(new File(filePath));
		}

		return inputStream;
	}

	public static byte[] inputStream2byteArray(InputStream inputStream)
			throws IOException {
		byte[] content = null;
		if (inputStream != null) {
			content = new byte[inputStream.available()];
			inputStream.read(content, 0, inputStream.available());
		}

		return content;
	}

}
