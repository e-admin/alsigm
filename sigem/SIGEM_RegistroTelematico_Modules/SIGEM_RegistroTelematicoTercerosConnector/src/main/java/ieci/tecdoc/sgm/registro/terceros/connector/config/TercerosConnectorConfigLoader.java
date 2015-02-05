package ieci.tecdoc.sgm.registro.terceros.connector.config;

import ieci.tecdoc.sgm.core.config.impl.spring.SigemConfigFilePathResolver;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

public class TercerosConnectorConfigLoader {


	private static final String DEFAULT_ENTIDAD_SUBDIR = "default";
	private static final String CONFIG_SUBDIR="SIGEM_RegistroTelematicoTercerosConnector";
	private static final String CONFIG_FILE = "registroTelematicoTercerosConnectorConfig.properties";

	protected static Map<String, Properties> properties = new HashMap<String, Properties>();

	/**
	 * Obtiene la configuración de la conexión con terceros para este <code>idEntidad</code>
	 * @param idEntidad
	 * @return
	 */
	public static Properties getProperties(String idEntidad)
	{
		Properties prop = null;
		if(properties.containsKey(idEntidad))
		{
			prop = properties.get(idEntidad);
		}
		else
		{
			prop = loadProperties(idEntidad);
			if(prop!=null)
			{
				properties.put(idEntidad, prop);
			}
		}
		return prop;
	}

	private static Properties loadProperties(String idEntidad)
	{

		Properties result=null;
		SigemConfigFilePathResolver pathResolver = SigemConfigFilePathResolver.getInstance();
		String subdir = "";
		if(!StringUtils.isEmpty(idEntidad))
		{
			subdir = CONFIG_SUBDIR+File.separator+idEntidad;
		}
		else
		{
			subdir = CONFIG_SUBDIR+File.separator+DEFAULT_ENTIDAD_SUBDIR;
		}

		try{
			result= pathResolver.loadProperties(CONFIG_FILE, subdir);
		}
		catch (Exception e) {
			subdir = CONFIG_SUBDIR+File.separator+DEFAULT_ENTIDAD_SUBDIR;

			try{
				result= pathResolver.loadProperties(CONFIG_FILE, subdir);
			}
			catch (Exception ex) {

			}
		}

		return result;
	}
}
