package ieci.tecdoc.sgm.consolidacion.config;

import ieci.tecdoc.sgm.core.config.impl.spring.SigemConfigFilePathResolver;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class ConsolidacionConfig {
	public final Logger logger = Logger.getLogger(ConsolidacionConfig.class);
	
	//Cada aplicacion web tiene su propio ClassLoader.
	//Una clase cargada en un ClassLoader es diferente a otra de otro ClassLoader. 
	//    Incluso aunque se llame igual y esté en el mismo paquete
	//singletons spring sólo válidos en un contexto de aplicación web (spring). 
	//    Cada aplicación web tiene su propio contexto de aplicacion web (spring)
	//Además un singleton tiene de Ambito el ClassLoader de la aplicación web.
	//Conclusion: No usar singleton. Cargar la configuración necesaria de cada vez.
	
	private static final String CONSOLIDACION_API_MANAGER_NAME_MAXLENGTH="CONSOLIDACION_API_MANAGER_NAME_MAXLENGTH"; 
	private static final String CONSOLIDACION_API_MANAGER_REGISTRY_USERNAME="CONSOLIDACION_API_MANAGER_REGISTRY_USERNAME";
	private static final String CONSOLIDACION_API_MANAGER_REGISTRY_PASSWORD="CONSOLIDACION_API_MANAGER_REGISTRY_PASSWORD";
	private static final String CONSOLIDACION_API_MANAGER_BOOKID="CONSOLIDACION_API_MANAGER_BOOKID";
	private static final String CONSOLIDACION_API_MANAGER_DEFAULT_CA_CODE="CONSOLIDACION_API_MANAGER_DEFAULT_CA_CODE";
	
	private static final String CONSOLIDACION_API_MANAGER_ERROR_ENVIAR_EMAIL_DESTINO="CONSOLIDACION_API_MANAGER_ERROR_ENVIAR_EMAIL_DESTINO";
	private static final String CONSOLIDACION_API_MANAGER_ERROR_ENVIAR_EMAIL_ORIGEN="CONSOLIDACION_API_MANAGER_ERROR_ENVIAR_EMAIL_ORIGEN";
	private static final String CONSOLIDACION_API_MANAGER_ERROR_ENVIAR_EMAIL_ASUNTO="CONSOLIDACION_API_MANAGER_ERROR_ENVIAR_EMAIL_ASUNTO";
	
	private static final String CONFIG_SUBDIR="SIGEM_Consolidacion";
	private static final String CONFIG_FILE="consolidacion.properties";
	private static final String DEFAULT_ENTIDAD_SUBDIR="default";
	
	private int maxLength;
	private String registryUsername;
	private String registryPassword;
	private String bookId;
	private String defaultCACode;
	
	private String errorEmailDestino;
	private String errorEmailOrigen;
	private String errorEmailAsunto;
	private Date fechaUltimaCarga;
	
	//=80
	//=REGISTRO_TELEMATICO
	//=*
	//=1
	//=SUBSANACION_RT

	public void loadConfiguration(String idEntidad){
		if(StringUtils.isBlank(idEntidad) ) return;
		
		String configRelativePathsubdir = CONFIG_SUBDIR+File.separator+idEntidad;
		Properties props=null;
		try {
			loadProperties(CONFIG_FILE, configRelativePathsubdir);
		} catch (Exception e) {
			configRelativePathsubdir = CONFIG_SUBDIR + File.separator + DEFAULT_ENTIDAD_SUBDIR;
			try {
				loadProperties(CONFIG_FILE, configRelativePathsubdir);
			} catch (Exception ex) {
				configRelativePathsubdir = CONFIG_SUBDIR;
				try {
					loadProperties(CONFIG_FILE, configRelativePathsubdir);
				} catch (Exception exc) {
					logger.error("No se ha podido cargar el fichero de configuración: " +
								CONFIG_FILE+ " para la entidad: " + idEntidad, exc);
					return;
				}
			}
		}	
	}
	
	private void loadProperties(String filename,String subDir) throws Exception{
		SigemConfigFilePathResolver pathResolver = SigemConfigFilePathResolver.getInstance();
		long lastModified=pathResolver.loadResource(filename, subDir).getFile().lastModified();
		if(fechaUltimaCarga==null || lastModified>fechaUltimaCarga.getTime()){
			Properties props = pathResolver.loadProperties(CONFIG_FILE, subDir);
			fill(props);
			fechaUltimaCarga=new Date();
		}
	}
	
	private void fill(Properties props){
		if(props==null) return;
		
		maxLength=Integer.parseInt(get(props,CONSOLIDACION_API_MANAGER_NAME_MAXLENGTH));
		registryUsername=get(props,CONSOLIDACION_API_MANAGER_REGISTRY_USERNAME);
		registryPassword=get(props,CONSOLIDACION_API_MANAGER_REGISTRY_PASSWORD);;
		bookId=get(props,CONSOLIDACION_API_MANAGER_BOOKID);
		defaultCACode=get(props,CONSOLIDACION_API_MANAGER_DEFAULT_CA_CODE);
		
		errorEmailDestino=get(props,CONSOLIDACION_API_MANAGER_ERROR_ENVIAR_EMAIL_DESTINO);
		errorEmailOrigen=get(props,CONSOLIDACION_API_MANAGER_ERROR_ENVIAR_EMAIL_ORIGEN);
		errorEmailAsunto=get(props,CONSOLIDACION_API_MANAGER_ERROR_ENVIAR_EMAIL_ASUNTO);
	}
	
	
	//Tamaño máximo para el nombre de los intervinientes.
	public int getMaxLength(){	return maxLength;	}
	
	//Usuario de registro presencial.
	public String getUserName(){	return registryUsername;	}
	
	//Clave del usuario de registro presencial.
	public String getPassword(){	return registryPassword;	}
	
	//Identificador el libro de entrada en registro presencial
	public String getBookId(){		return bookId; }
	
	//Código de asunto por defecto para las subsanaciones
	public String getDefaultCACode(){	return defaultCACode;	}
	
	//E-mail de origen del correo que se envía cuando sucede una excepción al consolidar un registro
	public String getErrorEnvioEmailOrigen(){	return errorEmailOrigen; }
	
	//E-mail de destino del correo que se envía cuando sucede una excepción al consolidar un registro
	public String getErrorEnvioEmailDestino(){	return errorEmailDestino;	}
	
	// Asunto del correo que se envía cuando sucede una excepción al consolidar un registro
	public String getErrorEnvioEmailAsunto(){	return errorEmailAsunto;	}
	
	private String get(Properties props,String key){
		if(props==null) return null;
		return props.getProperty(key);
	}
}
