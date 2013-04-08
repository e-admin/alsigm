package ieci.tecdoc.sgm.cripto.validacion.impl.config;

import ieci.tecdoc.sgm.core.config.impl.spring.SigemConfigFilePathResolver;
import ieci.tecdoc.sgm.core.services.cripto.validacion.CriptoValidacionException;
import ieci.tecdoc.sgm.cripto.validacion.impl.afirma.config.AFirmaValidacionConfiguration;
import ieci.tecdoc.sgm.cripto.validacion.impl.bouncycastle.config.ValidacionBCConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class ConfigValidacionLoader {

	private static final Logger logger = Logger.getLogger(ConfigValidacionLoader.class);

	private static final String DEFAULT_ENTIDAD_SUBDIR = "default";

	public static String CONFIG_SUBDIR = "SIGEM_CriptoValidacion";
	public static String CONFIG_AFIRMA_FILE = "ValidacionConfiguration.properties";
	public static String CONFIG_BC_FILE = "ValidacionConfiguration.properties";
	public static String CONFIG_AFIRMA_SECURITY_FILE = "AFirmaConfiguration.properties";

	public ConfigValidacionLoader() {
	}

	public ValidacionBCConfiguration loadBCConfiguration(String idEntidad) throws CriptoValidacionException {

		Properties properties = loadProperties(CONFIG_BC_FILE, idEntidad);

		ValidacionBCConfiguration firmaBCConfiguration = new ValidacionBCConfiguration();
		firmaBCConfiguration.setAlgorithm(properties.getProperty(ValidacionBCConfiguration.VALIDA_SIGEM_API_ALGO));
		firmaBCConfiguration.setProvider(properties.getProperty(ValidacionBCConfiguration.VALIDA_SIGEM_API_PROVIDER));

		return firmaBCConfiguration;
	}

	public AFirmaValidacionConfiguration loadAFirmaConfiguration(String idEntidad) throws CriptoValidacionException {

		Properties properties = loadProperties(CONFIG_AFIRMA_FILE, idEntidad);

		AFirmaValidacionConfiguration aFirmaConfiguration = new AFirmaValidacionConfiguration();
		aFirmaConfiguration.setPathServicios(properties.getProperty(AFirmaValidacionConfiguration.VALIDA_AFIRMA_API_PATH_SERVICES));
		aFirmaConfiguration.setAlgorithm(properties.getProperty(AFirmaValidacionConfiguration.VALIDA_AFIRMA_API_ALGO_HASH));
		aFirmaConfiguration.setIdAplicacion(properties.getProperty(AFirmaValidacionConfiguration.VALIDA_AFIRMA_API_APP_ID));
		aFirmaConfiguration.setModoValidacion(properties.getProperty(AFirmaValidacionConfiguration.VALIDA_AFIRMA_API_VALID_MODE));
		aFirmaConfiguration.setNombreServicioValidarCertificado(properties.getProperty(AFirmaValidacionConfiguration.VALIDA_AFIRMA_API_SERVICE_VALIDAR));

		// Fichero con la configuración de seguridad
		Properties confSeguridad = null;
		String afirmaSecurityFile = properties.getProperty(AFirmaValidacionConfiguration.VALIDA_AFIRMA_API_CONFIG_FILE);
		if (!StringUtils.isEmpty(afirmaSecurityFile)) {
			// Cargar una posible ruta absoluta
			try {
				confSeguridad = new Properties();
				confSeguridad.load(new FileInputStream(new File(afirmaSecurityFile)));
			}
			catch (Exception e) {
				confSeguridad = null;
				logger.warn("No se ha podido cargar la configuracion de arroba firma del fichero: " + afirmaSecurityFile, e);
				logger.warn("Se cargara del fichero " + CONFIG_AFIRMA_SECURITY_FILE + " establecido en la configuracion del modulo");
			}
		}

		if (confSeguridad == null) {
			confSeguridad = loadProperties(CONFIG_AFIRMA_SECURITY_FILE, idEntidad);
		}

		// Configuración de seguridad para la configuración de @firma para la entidad correspondiente
		aFirmaConfiguration.setConfSeguridad(confSeguridad);

		return aFirmaConfiguration;
	}

	private Properties loadProperties(String configFile, String idEntidad) throws CriptoValidacionException {

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
					throw new CriptoValidacionException("No se ha podido cargar el fichero de configuración: " + configFile+ " para la entidad: " + idEntidad);
				}
			}
		}

		return result;
	}
}
