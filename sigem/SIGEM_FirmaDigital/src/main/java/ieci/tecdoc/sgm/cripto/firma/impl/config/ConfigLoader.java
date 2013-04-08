package ieci.tecdoc.sgm.cripto.firma.impl.config;

import ieci.tecdoc.sgm.core.config.impl.spring.SigemConfigFilePathResolver;
import ieci.tecdoc.sgm.core.services.cripto.firma.FirmaDigitalException;
import ieci.tecdoc.sgm.cripto.firma.impl.afirma.config.AFirmaConfiguration;
import ieci.tecdoc.sgm.cripto.firma.impl.bouncycastle.config.FirmaBCConfiguration;
import ieci.tecdoc.sgm.cripto.firma.impl.bouncycastle.config.FirmaPKCS7Config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class ConfigLoader {

	private static final Logger logger = Logger.getLogger(ConfigLoader.class);

	private static final String DEFAULT_ENTIDAD_SUBDIR = "default";

	public static String CONFIG_SUBDIR = "SIGEM_FirmaDigital";
	public static String CONFIG_PKCS7_FILE = "firmaPKCS7Config.properties";
	public static String CONFIG_AFIRMA_FILE = "firmaAFirmaConfig.properties";
	public static String CONFIG_BC_FILE = "firmaBCConfig.properties";
	public static String CONFIG_AFIRMA_SECURITY_FILE = "AFirmaConfiguration.properties";

	public ConfigLoader() {
	}

	public FirmaPKCS7Config loadPKCS7Configuration(String idEntidad) throws FirmaDigitalException {

		Properties properties = loadProperties(CONFIG_PKCS7_FILE, idEntidad);

		FirmaPKCS7Config firmaPKCS7Config = new FirmaPKCS7Config();
		firmaPKCS7Config.setAlias(properties.getProperty(FirmaPKCS7Config.FIRMA_PKCS7_API_ALIAS));
		firmaPKCS7Config.setKeyStore(properties.getProperty(FirmaPKCS7Config.FIRMA_PKCS7_API_KEYSTORE));
		firmaPKCS7Config.setKeyStoreProvider(properties.getProperty(FirmaPKCS7Config.FIRMA_PKCS7_API_KEYSTORE_PROVIDER));
		firmaPKCS7Config.setKeyStoreType(properties.getProperty(FirmaPKCS7Config.FIRMA_PKCS7_API_KEYSTORE_TYPE));
		firmaPKCS7Config.setPassword(properties.getProperty(FirmaPKCS7Config.FIRMA_PKCS7_API_PASSWORD));

		return firmaPKCS7Config;
	}

	public FirmaBCConfiguration loadBCConfiguration(String idEntidad) throws FirmaDigitalException {

		Properties properties = loadProperties(CONFIG_BC_FILE, idEntidad);

		FirmaBCConfiguration firmaBCConfiguration = new FirmaBCConfiguration();
		firmaBCConfiguration.setAlias(properties.getProperty(FirmaBCConfiguration.FIRMA_SIGEM_API_ALIAS));
		firmaBCConfiguration.setKeyStore(properties.getProperty(FirmaBCConfiguration.FIRMA_SIGEM_API_KEYSTORE));
		firmaBCConfiguration.setKeyStoreProvider(properties.getProperty(FirmaBCConfiguration.FIRMA_SIGEM_API_KEYSTORE_PROVIDER));
		firmaBCConfiguration.setKeyStoreType(properties.getProperty(FirmaBCConfiguration.FIRMA_SIGEM_API_KEYSTORE_TYPE));
		firmaBCConfiguration.setPassword(properties.getProperty(FirmaBCConfiguration.FIRMA_SIGEM_API_PASSWORD));

		return firmaBCConfiguration;
	}

	public AFirmaConfiguration loadAFirmaConfiguration(String idEntidad) throws FirmaDigitalException {

		Properties properties = loadProperties(CONFIG_AFIRMA_FILE, idEntidad);

		AFirmaConfiguration aFirmaConfiguration = new AFirmaConfiguration();
		aFirmaConfiguration.setAlias(properties.getProperty(AFirmaConfiguration.FIRMA_AFIRMA_API_ALIAS));
		aFirmaConfiguration.setAlgoritmoHash(properties.getProperty(AFirmaConfiguration.FIRMA_AFIRMA_API_ALGO_HASH));
		aFirmaConfiguration.setFirmante(properties.getProperty(AFirmaConfiguration.FIRMA_AFIRMA_API_FIRMANTE));
		aFirmaConfiguration.setFormatoFirma(properties.getProperty(AFirmaConfiguration.FIRMA_AFIRMA_API_FORMATO));
		aFirmaConfiguration.setIdAplicacion(properties.getProperty(AFirmaConfiguration.FIRMA_AFIRMA_API_APP_ID));
		aFirmaConfiguration.setIdReferencia(properties.getProperty(AFirmaConfiguration.FIRMA_AFIRMA_API_REF_ID));
		aFirmaConfiguration.setKeyStore(properties.getProperty(AFirmaConfiguration.FIRMA_AFIRMA_API_KEYSTORE));
		aFirmaConfiguration.setKeyStoreProvider(properties.getProperty(AFirmaConfiguration.FIRMA_AFIRMA_API_KEYSTORE_PROVIDER));
		aFirmaConfiguration.setKeyStoreType(properties.getProperty(AFirmaConfiguration.FIRMA_AFIRMA_API_KEYSTORE_TYPE));
		aFirmaConfiguration.setNombreServicioAlmacenarDocumento(properties.getProperty(AFirmaConfiguration.FIRMA_AFIRMA_API_SERVICE_STORAGE));
		aFirmaConfiguration.setNombreServicioFirmar(properties.getProperty(AFirmaConfiguration.FIRMA_AFIRMA_API_SERVICE_FIRMAR));
		aFirmaConfiguration.setNombreServicioRegistrarFirma(properties.getProperty(AFirmaConfiguration.FIRMA_AFIRMA_API_SERVICE_REGISTRAR_FIRMA));
		aFirmaConfiguration.setNombreServicioVerificar(properties.getProperty(AFirmaConfiguration.FIRMA_AFIRMA_API_SERVICE_VERIFICAR));
		aFirmaConfiguration.setPassword(properties.getProperty(AFirmaConfiguration.FIRMA_AFIRMA_API_PASSWORD));
		aFirmaConfiguration.setPathServicios(properties.getProperty(AFirmaConfiguration.FIRMA_AFIRMA_API_PATH_SERVICES));

		// Fichero con la configuración de seguridad
		Properties confSeguridad = null;
		String afirmaSecurityFile = properties.getProperty(AFirmaConfiguration.FIRMA_AFIRMA_API_CONFIG_FILE);
		if (!StringUtils.isEmpty(afirmaSecurityFile)) {
			// Cargar una posible ruta absoluta
			try {
				confSeguridad = new Properties();
				confSeguridad.load(new FileInputStream(new File(afirmaSecurityFile)));
			}
			catch (Exception e) {
				confSeguridad = null;
				logger.warn("No se ha podido cargar la configuracion de arroba firma del fichero: " + afirmaSecurityFile, e);
				logger.warn("Se cargara del fichero " + CONFIG_AFIRMA_SECURITY_FILE + " de la configuracion del modulo");
			}
		}

		if (confSeguridad == null) {
			confSeguridad = loadProperties(CONFIG_AFIRMA_SECURITY_FILE, idEntidad);
		}

		// Configuración de seguridad para la configuración de @firma para la entidad correspondiente
		aFirmaConfiguration.setConfSeguridad(confSeguridad);

		return aFirmaConfiguration;
	}

	private Properties loadProperties(String configFile, String idEntidad) throws FirmaDigitalException {

		Properties result = null;
		String subdir = "";

		SigemConfigFilePathResolver pathResolver = SigemConfigFilePathResolver.getInstance();

		if (!StringUtils.isEmpty(idEntidad)) {
			subdir = CONFIG_SUBDIR + File.separator + idEntidad;
		} else {
			subdir = CONFIG_SUBDIR + File.separator + DEFAULT_ENTIDAD_SUBDIR;
		}

		try {
			result = pathResolver.loadProperties(configFile, subdir);

		} catch (Exception e) {
			subdir = CONFIG_SUBDIR + File.separator + DEFAULT_ENTIDAD_SUBDIR;

			try {
				result = pathResolver.loadProperties(configFile, subdir);

			} catch (Exception ex) {
				subdir = CONFIG_SUBDIR;

				try {
					result = pathResolver.loadProperties(configFile, subdir);

				} catch (Exception exc) {
					throw new FirmaDigitalException("No se ha podido cargar el fichero de configuración: " + configFile+ " para la entidad: " + idEntidad);
				}
			}
		}

		return result;
	}
}
