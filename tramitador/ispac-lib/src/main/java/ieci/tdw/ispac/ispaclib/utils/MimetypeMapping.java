package ieci.tdw.ispac.ispaclib.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import org.apache.log4j.Logger;

import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.MimeUtil;

public class MimetypeMapping {

	/**
	 * Tipo MIME por defecto.
	 */
	public static final String UNKNOWN_MIME_TYPE = "application/octet-stream";
	
	/**
	 * Extensión por defecto.
	 */
	public static final String UNKNOWN_EXTENSION = "bin";
	
	/**
	 * Extensiones de los tipos MIME.
	 */
	private static final Properties mimeTypesExts = new Properties();
	
	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(MimetypeMapping.class);

	static {
		MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.ExtensionMimeDetector");
		MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
		initMimeTypes();
	}
	
	private static void initMimeTypes() {

		try {
						
			// Load the default supplied mime types
			mimeTypesExts.load(MimetypeMapping.class.getClassLoader().getResourceAsStream("ieci/tdw/ispac/ispaclib/utils/MimetypeMapping.properties"));

			// Load any .mime-types.properties from the users home directory
			try {
				File f = new File(System.getProperty("user.home") + File.separator + ".MimetypeMapping.properties");
				if (f.exists()) {
					InputStream is = new FileInputStream(f);
					if (is != null) {
						logger.debug("Found a custom .MimetypeMapping.properties file in the users home directory.");
						Properties props = new Properties();
						props.load(is);
						if (props.size() > 0) {
							mimeTypesExts.putAll(props);
						}
						logger.debug("Successfully parsed .MimetypeMapping.properties from users home directory.");
					}
				}
			} catch (Exception e) {
				logger.info("Failed to parse .MimetypeMapping file from users home directory. File will be ignored.", e);
			}

			try {
				// Load any classpath provided mime types that either extend or
				// override the default mime type entries
				InputStream is = MimeUtil.class.getClassLoader().getResourceAsStream("MimetypeMapping.properties");
				if (is != null) {
					logger.debug("Found a custom MimetypeMapping.properties file on the classpath.");
					Properties props = new Properties();
					props.load(is);
					if (props.size() > 0) {
						mimeTypesExts.putAll(props);
					}
					logger.debug("Successfully loaded custome MimetypeMapping.properties file from classpath.");
				}
			} catch (Exception e) {
				logger.info("Failed to load the MimetypeMapping.properties file located on the classpath. File will be ignored.");
			}
			
			try {
				// Load any mime extension mappings file defined with the JVM
				// property -Dmime-mappings=../my/custom/MimetypeMapping.properties
				String fname = System.getProperty("mime-mappings");
				if (fname != null && fname.length() != 0) {
					InputStream is = new FileInputStream(fname);
					if (is != null) {
						if (logger.isDebugEnabled()) {
							logger.debug("Found a custom mime-mapping property defined by the property -Dmime-mappings ["
									+ System.getProperty("-Dmime-mappings") + "].");
						}
						Properties props = new Properties();
						props.load(is);
						if (props.size() > 0) {
							mimeTypesExts.putAll(props);
						}
						logger.debug("Successfully loaded the mime mappings file from property -Dmime-mappings ["
								+ System.getProperty("-Dmime-mappings") + "].");
					}
				}
			} catch (Exception e) {
				logger.info("Failed to load the mime-mappings file defined by the property -Dmime-mappings ["
						+ System.getProperty("-Dmime-mappings") + "].", e);
			}
			
		} catch (Exception e) {
			logger.error("Error loading internal ieci/tdw/ispac/ispaclib/utils/MimetypeMapping.properties", e);
		}
	}

	public static String getMimeType(String extension) {

		if (extension != null) {
			return getFileMimeType("file." + extension.toLowerCase());
		}
		
		return UNKNOWN_MIME_TYPE;
	}

	public static String getFileMimeType(String fileName) {

		if (fileName != null) {
			return getMimeType(MimeUtil.getMimeTypes(fileName.toLowerCase()));
		}
		
		return UNKNOWN_MIME_TYPE;
	}
	
	public static String getMimeType(byte[] file) {

		if (file != null) {
			return getMimeType(MimeUtil.getMimeTypes(file));
		}

		return UNKNOWN_MIME_TYPE;
	}

	private static String getMimeType(Collection mimeTypes) {
		
		if (mimeTypes != null) {
			Iterator mimeTypeIt = mimeTypes.iterator();
			while (mimeTypeIt.hasNext()) {
				MimeType mimeType = (MimeType) mimeTypeIt.next();
				if (mimeType != null) {
					return mimeType.toString();
				}
			}
		}
		
		return UNKNOWN_MIME_TYPE;
	}
	
	public static String getExtension(String mimeType) {

		String ext = UNKNOWN_EXTENSION;
		
		if (mimeType != null) {
			ext=mimeTypesExts.getProperty(mimeType.toLowerCase().trim());
			
		}

		return ext;
	}

}
