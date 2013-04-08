package ieci.tecdoc.sgm.core.config.impl.spring;

import es.ieci.tecdoc.fwktd.core.spring.configuration.IeciSystemConfigurationResourceLoaderImpl;

public class SigemConfigurationResourceLoaderImpl extends
	IeciSystemConfigurationResourceLoaderImpl {
	
	public static String DEFAULT_SIGEM_BASE_CONFIGURATION_PATH_DIR_SYSTEM_VAR_NAME="SIGEM_CONFIG_DIR";
	
	
	
	public SigemConfigurationResourceLoaderImpl(){
		super();
		baseConfigurationPathDirSystemVarName=new String(DEFAULT_SIGEM_BASE_CONFIGURATION_PATH_DIR_SYSTEM_VAR_NAME);
		baseConfigurationPathDir="";
	}

	

}
