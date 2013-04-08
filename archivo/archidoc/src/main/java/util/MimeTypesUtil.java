package util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;

import common.Constants;

public class MimeTypesUtil extends Properties {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static final String DOCCONTENTTYPES_FILE = "mimetypes.properties";

	private static MimeTypesUtil mInstance = null;

	public static synchronized MimeTypesUtil getInstance() {
		if (mInstance == null) {
			mInstance = new MimeTypesUtil();
			mInstance.initiate(DOCCONTENTTYPES_FILE);
		}
		return mInstance;
	}

	protected void initiate(String fileName) {
		try {

			InputStream is = this.getClass().getResourceAsStream(
					DOCCONTENTTYPES_FILE);
			load(is);
			is.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}

	public String getMimeType(String name) {
		String extension = FilenameUtils.getExtension(name);
		if (!name.startsWith("."))
			extension = "." + extension;

		Object value = super.get(extension);
		if (value == null)
			return Constants.STRING_TEXT;
		return value.toString();
	}
}
