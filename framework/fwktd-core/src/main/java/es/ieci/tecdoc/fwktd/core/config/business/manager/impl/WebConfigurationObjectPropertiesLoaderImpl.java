package es.ieci.tecdoc.fwktd.core.config.business.manager.impl;

import javax.servlet.ServletRequest;

import es.ieci.tecdoc.fwktd.core.config.business.vo.ConfigurationObjectMapImpl;

public class WebConfigurationObjectPropertiesLoaderImpl extends AbstractConfigurationObjectPropertiesLoaderImpl {

	public WebConfigurationObjectPropertiesLoaderImpl(ServletRequest request) {
		
		this.configurationPathLoader=new ConfigurationPathLoaderWebImpl(request, null);
		this.configurationObject = new ConfigurationObjectMapImpl();
		
	}
	
	
	

}
