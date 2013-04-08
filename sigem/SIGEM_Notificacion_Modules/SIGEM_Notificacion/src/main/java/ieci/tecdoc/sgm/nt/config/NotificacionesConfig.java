package ieci.tecdoc.sgm.nt.config;

import ieci.tecdoc.sgm.core.config.impl.spring.SigemBasePlaceholderConfigurer;
import ieci.tecdoc.sgm.core.config.impl.spring.SigemConfigurationResourceLoaderImpl;
import ieci.tecdoc.sgm.nt.conectores.ConectorNotificacion;
import ieci.tecdoc.sgm.nt.config.beans.ConectorDefinition;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.axis.utils.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import es.ieci.tecdoc.fwktd.core.spring.configuration.ConfigurationResourceLoader;

public class NotificacionesConfig {
	private static final Logger logger = Logger.getLogger(NotificacionesConfig.class);

	private static Map conectoresEntidades = null;
	private static Map sisnotConfigDbMapEntidades=null;
	
	public static String POSTGRES_DRIVER="org.postgresql.Driver";
	
	public static String BD_DRIVER="SISNOT_BD_DRIVER";
	public static String BD_URL="SISNOT_BD_URL";
	public static String BD_USERNAME="SISNOT_BD_USERNAME";
	public static String BD_PASSWORD="SISNOT_BD_PASSWORD";
	public static String QUERY="SISNOT_ID_QUERY";
	
	public static String CONECTORES_CONFIG_FILE="conectores.properties";
	public static String SISNOT_DB_CONFIG_FILE="SisnotBD.properties";
	public static String NOTIFICACIONES_ENTYTIES_CONFIR_DIR="SIGEM_Notificacion";
	public static String DEFAULT="default";
	
	public static String BEAN_SISNOT="NOTIFICACION.SISNOT";
	public static String BEAN_SMS="NOTIFICACION.SMS";
	static{
		conectoresEntidades=new HashMap();
		sisnotConfigDbMapEntidades=new HashMap();
	}
	
	private static String getEntityConfigRelativePath(ConfigurationResourceLoader loader,String entidad){
		String ENTITY_CONFIG_RELATIVE_PATH=null;
		Resource resource=null;
		try{
			ENTITY_CONFIG_RELATIVE_PATH=NOTIFICACIONES_ENTYTIES_CONFIR_DIR+File.separator+entidad+File.separator;
			resource=loader.loadResource(ENTITY_CONFIG_RELATIVE_PATH, null);
			if(resource==null || !resource.exists()) throw new NullPointerException();
		}catch(Exception e){
			try{
				ENTITY_CONFIG_RELATIVE_PATH=NOTIFICACIONES_ENTYTIES_CONFIR_DIR+File.separator+DEFAULT+File.separator;
				resource=loader.loadResource(ENTITY_CONFIG_RELATIVE_PATH, null);
				if(resource==null || !resource.exists()) throw new NullPointerException();
			}catch(Exception ex){
				logger.error("No se pudo encontrar el directorio de configuraci�n de notificaciones de la entidad "+entidad+")", e);
			}
		}
		return ENTITY_CONFIG_RELATIVE_PATH;
	}
	
	private static void refreshBeans(ClassPathXmlApplicationContext context,String[] beanNames){
		if(beanNames==null || beanNames.length==0) return;
		try {
			//context.getBeanFactory().setCacheBeanMetadata(false);
			context.getBeanFactory().destroySingletons();
			for(int i=0;i<beanNames.length;i++){
				BeanDefinition beanDef=((AbstractBeanFactory)context.getBeanFactory()).getMergedBeanDefinition(beanNames[i]);
				for(Iterator it=beanDef.getPropertyValues().getPropertyValueList().iterator();it.hasNext();){
					PropertyValue prop=(PropertyValue)it.next();
					prop.setConvertedValue(((TypedStringValue)prop.getValue()).getValue());
				}
			}
			context.getBeanFactory().preInstantiateSingletons();
			context.publishEvent(new ContextRefreshedEvent(context));
		} catch (BeansException ex) {
			// Destroy already created singletons to avoid dangling resources.
			context.getBeanFactory().destroySingletons();
			throw ex;
		}
	}
	
	private static void cargarConectoresYConfigBDEntidad(String entidad){
		if(conectoresEntidades.get(entidad)!=null) return;
		
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("notificacion-config-beans.xml");
			SigemBasePlaceholderConfigurer configurer = new SigemBasePlaceholderConfigurer();
			ConfigurationResourceLoader loader=new SigemConfigurationResourceLoaderImpl();
			configurer.setConfigurationResourceLoader(loader);
			
			String ENTITY_CONFIG_RELATIVE_PATH=getEntityConfigRelativePath(loader,entidad);
			String[] strResources=new String[] {
					loader.loadResource(ENTITY_CONFIG_RELATIVE_PATH+CONECTORES_CONFIG_FILE,null).getFile().getAbsolutePath(),
					loader.loadResource(ENTITY_CONFIG_RELATIVE_PATH+SISNOT_DB_CONFIG_FILE,null).getFile().getAbsolutePath()	};
			if(!new File(strResources[0]).exists() || !new File(strResources[1]).exists()){
				Resource[] resources=new Resource[]{
						new ClassPathResource(ENTITY_CONFIG_RELATIVE_PATH+CONECTORES_CONFIG_FILE),
						new ClassPathResource(ENTITY_CONFIG_RELATIVE_PATH+SISNOT_DB_CONFIG_FILE) };	
				configurer.setLocations(resources);
			}else{
				configurer.setLocations(strResources);
			}
			
			configurer.setIgnoreUnresolvablePlaceholders(true);
			configurer.postProcessBeanFactory(context.getBeanFactory());
			refreshBeans(context,new String[]{BEAN_SISNOT,BEAN_SMS});
			
			Map conectores=(Map) context.getBean("NOTIFICACION.CONECTORES_CONFIG");
			
			Iterator it=conectores.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry map=(Map.Entry)it.next();
				ConectorDefinition definicion=((ConectorDefinition)map.getValue());
				ConectorNotificacion conector=(ConectorNotificacion)Class.forName(definicion.getClaseJava()).newInstance();
				definicion.setConector(conector);
			}
			
			Map sisnotConfigDbMap=(Map) context.getBean("NOTIFICACIONES.SISNOT_BD_CONFIG");
			
			conectoresEntidades.put(entidad,conectores);
			sisnotConfigDbMapEntidades.put(entidad,sisnotConfigDbMap);
			context.getBeanFactory().setCacheBeanMetadata(true);
		} catch (Exception e) {
			logger.error("Error inicializando configuraci�n beans de notificaciones (entidad +"+entidad+")", e);
		}
	}
	
	public static ConectorNotificacion getConector(String idConector,String entidad){
		ConectorDefinition conectorDefinition=getConectorDefinition(idConector,entidad); 
		return conectorDefinition.getConector();
	}
	
	public static ConectorDefinition getConectorDefinition(String idConector,String entidad){
		Map conectores=(Map)conectoresEntidades.get(entidad);
		if(conectores==null){
			cargarConectoresYConfigBDEntidad(entidad);
			conectores=(Map)conectoresEntidades.get(entidad);
		}
		
		if(conectores==null  || conectores.get(idConector)==null) return null;
		ConectorDefinition conectorDefinition=((ConectorDefinition)conectores.get(idConector));
		logger.debug(conectorDefinition);
		return conectorDefinition;
	}
	
	//Por defecto se utilizar� el driver de Postgres.
	private static Connection getConnectionFromConfig(String entidad) throws ClassNotFoundException,SQLException{
		Map sisnotConfigDbMap=(Map)sisnotConfigDbMapEntidades.get(entidad);
		if(sisnotConfigDbMap==null){
			cargarConectoresYConfigBDEntidad(entidad);
			sisnotConfigDbMap=(Map)sisnotConfigDbMapEntidades.get(entidad);
		}
		
		String driverDb=(String)sisnotConfigDbMap.get(BD_DRIVER);
		if(StringUtils.isEmpty(driverDb)) 
			driverDb=POSTGRES_DRIVER;
		Class.forName(driverDb);
		return DriverManager.getConnection(
				(String)sisnotConfigDbMap.get(BD_URL),
				(String)sisnotConfigDbMap.get(BD_USERNAME),
				(String)sisnotConfigDbMap.get(BD_PASSWORD));
	}
	
	public static Connection getSisnotConnection(String entidad) {
		Connection con=null;
		DataSource ds=null;
		
		try {
			//Context initContext = new InitialContext();
		  	//Context envContext = (Context) initContext.lookup("java:/comp/env");
		  	
		  	//tratamos de usar el datasource del servidor de aplicaciones
		  	//if (envContext != null){
		  	//	try{ ds = (DataSource) envContext.lookup("jdbc/sisnot"); }
		  	//	catch(Exception e){ logger.debug(e); }
		  	//}
		  	//if (envContext == null || ds == null){
		  		//si no esta definido el datasource "jbdc/sisnot"
		  		//carga los datos del fichero properties de configuracion
		  		con=getConnectionFromConfig(entidad);
		  	//}else{
		  		//si hay datasource obtiene la conexion a partir de �l
		  		con=ds.getConnection();
		  	//}
		}catch(Exception e){
			logger.debug(e);
		}
		return con;
	}
	
	public static String getSisnotIdUsuarioQuery(String entidad){
		Map sisnotConfigDbMap=(Map)sisnotConfigDbMapEntidades.get(entidad);
		if(sisnotConfigDbMap==null){
			cargarConectoresYConfigBDEntidad(entidad);
			sisnotConfigDbMap=(Map)sisnotConfigDbMapEntidades.get(entidad);
		}
		return (String)sisnotConfigDbMap.get(QUERY);
	}
}
