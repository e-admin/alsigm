package ieci.tdw.ispac.ispaclib.gendoc.config;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.session.OrganizationUser;
import ieci.tdw.ispac.ispaclib.session.OrganizationUserInfo;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/** 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */
public class RepositoriesCache {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(RepositoriesCache.class);

	/**
	 * Clave por defecto de los repositorios cacheados
	 */
	private static final String DEFAULT_REPOSITORY_KEY = "MONOENTIDAD";
		
	/**
	 * Información de los repositorios documentales configurados.
	 */
	private static final Map repositoriesMap = new HashMap();

	
	public static synchronized Repositories getRepositories(IClientContext ctx, String configFileName) throws ISPACException {
		
		String repositoriesKey = getRepositoriesKey();
		
		Repositories repositories = (Repositories)repositoriesMap.get(repositoriesKey);
		if (repositories == null) {
			try {
				
				DocumentalConfiguration documentalConfig = null;
				
				// Leer la configuración de repositorios de la variable del sistema
				if (ctx != null) {
					String configVarName = ISPACConfiguration.getInstance().get(ISPACConfiguration.CONNECTOR_MANAGER_CFG_VARNAME);
					if (StringUtils.isNotBlank(configVarName)) {
				    	String repositoriesXML = ConfigurationMgr.getVarGlobal(ctx, configVarName);
				    	if (StringUtils.isNotBlank(repositoriesXML)) {
				    		
				    		documentalConfig = new DocumentalConfiguration(new ByteArrayInputStream(repositoriesXML.getBytes("UTF-8")));
				    	}
					}
				}

				// Leer la configuración de repositorios del fichero de configuración
				if ((documentalConfig == null) && StringUtils.isNotBlank(configFileName)) {
					documentalConfig = new DocumentalConfiguration(configFileName);
				}
				
				repositories = documentalConfig.getRepositories();
				repositoriesMap.put(repositoriesKey, repositories);
				
			} catch (Throwable e) {
				logger.error("Error al cargar la información de los repositorios documentales", e);
				throw new ISPACException("Error al cargar la información de los repositorios documentales", e);
			}
		}
		
		return repositories;
	}
	
	private static String getRepositoriesKey() {

		String key = null;
		
		OrganizationUserInfo info = OrganizationUser.getOrganizationUserInfo();
		if (info != null) {
			key = info.getOrganizationId();
		} else {
			key = DEFAULT_REPOSITORY_KEY;
		}

		return key;
	}

}
