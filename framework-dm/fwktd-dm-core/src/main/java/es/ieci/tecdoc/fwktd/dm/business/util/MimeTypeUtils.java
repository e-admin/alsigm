package es.ieci.tecdoc.fwktd.dm.business.util;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;

import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.MimeUtil;

public class MimeTypeUtils {

	static {
		MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.ExtensionMimeDetector");
		MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
	}

	public static String getExtensionMimeType(String extension) {
		return getMimeType("file." + extension);
	}

	@SuppressWarnings("rawtypes")
	public static String getMimeType(String fileName) {
		Collection mimeTypes = MimeUtil.getMimeTypes(fileName.toLowerCase());
		return getMostSpecificMimeType(mimeTypes);
	}

	@SuppressWarnings("rawtypes")
	public static String getMimeType(File file) {
		Collection mimeTypes = MimeUtil.getMimeTypes(file);
		return getMostSpecificMimeType(mimeTypes);
	}

	@SuppressWarnings("rawtypes")
	public static String getMimeType(byte[] file) {
		Collection mimeTypes = MimeUtil.getMimeTypes(file);
		return getMostSpecificMimeType(mimeTypes);
	}

	@SuppressWarnings("rawtypes")
	public static String getMimeType(InputStream inputStream) {
		Collection mimeTypes = MimeUtil.getMimeTypes(inputStream);
		return getMostSpecificMimeType(mimeTypes);
	}

	@SuppressWarnings("rawtypes")
	protected static String getMostSpecificMimeType(Collection mimeTypes) {

		String mostSpecificMimeType = null;

		if ((mimeTypes != null) && !mimeTypes.isEmpty()) {
			MimeType mimeType = MimeUtil.getMostSpecificMimeType(mimeTypes);
			if (mimeType != null) {
				mostSpecificMimeType = mimeType.toString();
			}
		}

		return mostSpecificMimeType;
	}
}
