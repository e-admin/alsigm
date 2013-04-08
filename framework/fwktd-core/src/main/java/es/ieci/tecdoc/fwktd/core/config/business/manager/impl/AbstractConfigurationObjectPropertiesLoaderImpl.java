package es.ieci.tecdoc.fwktd.core.config.business.manager.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ieci.tecdoc.fwktd.core.config.business.manager.ConfigurationObjectLoader;
import es.ieci.tecdoc.fwktd.core.config.business.manager.ConfigurationPathLoader;
import es.ieci.tecdoc.fwktd.core.config.business.vo.ConfigurationObject;
import es.ieci.tecdoc.fwktd.core.config.exception.ConfigurationObjectException;
import es.ieci.tecdoc.fwktd.core.messages.ConfiguracionObjectErrorCodes;

public abstract class AbstractConfigurationObjectPropertiesLoaderImpl implements
		ConfigurationObjectLoader {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(AbstractConfigurationObjectPropertiesLoaderImpl.class);
	
	protected ConfigurationPathLoader configurationPathLoader;
	
	protected ConfigurationObject configurationObject;
	
	public AbstractConfigurationObjectPropertiesLoaderImpl(){
		super();
	}
	
	public AbstractConfigurationObjectPropertiesLoaderImpl(ConfigurationObject configurationObject, ConfigurationPathLoader configurationPathLoader){
		this.configurationPathLoader=configurationPathLoader;
		this.configurationObject=configurationObject;
	}
	
	public ConfigurationObject loadConfigurationObject() {
		Properties props=null;
		String propertiesPath = configurationPathLoader.getPathConfiguracionFile();
		props = loadConfigurationObject(propertiesPath);
		
		
		if ((props!=null) && (!props.isEmpty())){
			
			// Recorrer las propiedades y almacenarlas en la propiedad
			Iterator it = props.entrySet().iterator();
			while (it.hasNext()){
				Map.Entry entry = (Map.Entry) it.next();
				configurationObject.set((String) entry.getKey(), entry.getValue());
			}
		}
		return configurationObject;
	}
	
	

	protected Properties loadConfigurationObject(String propertiesPath) throws ConfigurationObjectException {

		Properties props;
		try {
			InputStream is = new FileInputStream(new File(propertiesPath));
			props = new Properties();
			props.load(is);
		} catch (FileNotFoundException e) {
			logger.error("loadConfigurationObject(String)", e);

			throw new ConfigurationObjectException(ConfiguracionObjectErrorCodes.ERR_1000_CONFIG_FILE_NOT_FOUND, e.getMessage(), e);
		} catch (IOException e) {
			logger.error("loadConfigurationObject(String)", e);

			throw new ConfigurationObjectException(ConfiguracionObjectErrorCodes.ERR_1001_IO_EXCEPTION, e.getMessage(), e);
		}
		


		return props;
		
		

	}



	public ConfigurationObject getConfigurationObject() {
		return configurationObject;
	}

	public void setConfigurationObject(ConfigurationObject configurationObject) {
		this.configurationObject = configurationObject;
	}

	public ConfigurationPathLoader getConfigurationPathLoader() {
		return configurationPathLoader;
	}

	public void setConfigurationPathLoader(
			ConfigurationPathLoader configurationPathLoader) {
		this.configurationPathLoader = configurationPathLoader;
	}

	

}
