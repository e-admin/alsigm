package ieci.tecdoc.sgm.core.config.impl.spring;


import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;

import es.ieci.tecdoc.fwktd.core.spring.configuration.ConfigFilePathResolver;
import es.ieci.tecdoc.fwktd.core.spring.configuration.ConfigFilePathResolverImpl;
import es.ieci.tecdoc.fwktd.core.spring.configuration.ConfigurationResourceLoader;

public class SigemConfigFilePathResolver  {
	
	protected static final Logger logger = Logger.getLogger(SigemConfigFilePathResolver.class);

	protected static String SPRING_BEANS_FILENAME_DEFAULT="SIGEM_spring.xml";
	protected static String SPRING_CONFIG_BEAN_DEFAULT="sigemConfigurationResourceLoader";
	
	
	protected ConfigFilePathResolver configFilePathResolver;
	
	protected ConfigurationResourceLoader configurationResourceLoader;
	

	static SigemConfigFilePathResolver instance;
	
	
	public static synchronized SigemConfigFilePathResolver getInstance() {
	    if (instance == null)
	      instance = new SigemConfigFilePathResolver();
	    return instance;
	}


	
	private SigemConfigFilePathResolver(){
		
		String springBeansFileName= SPRING_BEANS_FILENAME_DEFAULT;
		String springConfigBean=SPRING_CONFIG_BEAN_DEFAULT;
		
		String configSubDir="";
		configFilePathResolver= new ConfigFilePathResolverImpl(springBeansFileName,springConfigBean, configSubDir);
		
		try {
			configurationResourceLoader= (ConfigurationResourceLoader) DefaultConfiguration.getConfiguration().getBean(SPRING_CONFIG_BEAN_DEFAULT);
		} catch (Exception e) {
			String message= e.getLocalizedMessage();
			message= "Error obteniendo bean de configuracion:"+SPRING_CONFIG_BEAN_DEFAULT+"--- "+message;
			logger.error(message);
			throw new RuntimeException(message);		
		}
		
			
	}
	
	/**
	 * Metodo que carga un recurso de configuracion externalizado
	 * @param resourceName
	 * @param subDir
	 * @return
	 */
	public Resource loadResource(String resourceName, String subDir){
		Resource result=null;
		
		String newresourceName=subDir+File.separator+resourceName;
		result = configurationResourceLoader.loadResource(newresourceName, null);
				
		return result;
	}
	
	/**
	 * Metodo que carga un recurso de properties de configuracion externalizado
	 * @param resourceName
	 * @param subDir
	 * @return
	 */
	public Properties loadProperties(String resourceName, String subDir){

		Properties result= new Properties();
		
		
		Resource resource =this.loadResource(resourceName, subDir);
		
		try {
			result.load(resource.getInputStream());
		} catch (IOException e) {
			String message= e.getLocalizedMessage();
			message= "Error cargando properties:"+subDir+File.separator+resourceName+"--- "+message;
			logger.error(message);
			throw new RuntimeException(message);
		}
		
		return result;		
		
	}	
	
	public String resolveFullPath(String fileName,String subDir){
		
		String newFileName=subDir+File.separator+fileName;
		String result= configFilePathResolver.resolveFullPath(newFileName);
		
		return result;
	}

	public ConfigFilePathResolver getConfigFilePathResolver() {
		return configFilePathResolver;
	}

	public void setConfigFilePathResolver(
			ConfigFilePathResolver configFilePathResolver) {
		this.configFilePathResolver = configFilePathResolver;
	}



	public ConfigurationResourceLoader getConfigurationResourceLoader() {
		return configurationResourceLoader;
	}



	public void setConfigurationResourceLoader(
			ConfigurationResourceLoader configurationResourceLoader) {
		this.configurationResourceLoader = configurationResourceLoader;
	}



	
	
	
}
