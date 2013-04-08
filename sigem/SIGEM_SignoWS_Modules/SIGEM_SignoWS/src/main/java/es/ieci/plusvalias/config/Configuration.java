package es.ieci.plusvalias.config;

import ieci.tecdoc.sgm.base.file.FileManager;
import ieci.tecdoc.sgm.core.config.impl.spring.MultiEntityContextHolder;
import ieci.tecdoc.sgm.core.config.impl.spring.SigemConfigFilePathResolver;
import ieci.tecdoc.sgm.core.services.cripto.firma.FirmaDigitalException;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import es.ieci.plusvalias.IPlusvaliaEntidadService;
import es.ieci.plusvalias.ws.IpValidatorInterceptor;

public class Configuration{
	public static final Logger logger = Logger.getLogger(Configuration.class);
	private static Configuration instance=null;
	
	private static final String CONFIG_SUBDIR="SIGEM_SignoWS";
	private static final String CONFIG_DIR_KEY="CONFIG_DIR";
	private static final String FECHA_ULTIMA_CARGA_KEY="FECHA_ULTIMA_CARGA_DATE";
	private static final String CONFIG_FILE="plusvalias-config.properties";
	private static final String DEFAULT_RECEIPT_FILE="receipt.pdf";
	private static final String DEFAULT_ENTIDAD_SUBDIR="default";
	
	private static final String SERVICE_PLUSVALIAS_ALLOW_IP_KEY="service.plusvalias.allow.ip";
	private static final String SERVICE_SPRING_BEAN_ENTIDAD_KEY="service.spring.bean.entidad";
	
	private static final String SIGEM_REGISTRO_OFICINA_KEY="sigem.registro.oficina.codigo";
	private static final String SIGEM_REGISTRO_ORGANO_KEY="sigem.registro.destino.organo.codigo";
	private static final String SIGEM_REGISTRO_ASUNTO_KEY="sigem.registro.asunto.codigo";
	private static final String SIGEM_REGISTRO_XML_SOLICITUD_KEY="sigem.registro.xmlsolicitud";
	
	private static final String SIGEM_TRAMITACION_ENDPOINT_KEY="sigem.tramitacion.endpoint";
	private static final String SIGEM_TRAMITACION_COD_PROCEDIMIENTO_KEY="sigem.tramitacion.codProcedimiento";
	//private static final String SIGEM_TRAMITACION_TIPO_DOCUMENTOS_KEY="sigem.tramitacion.tipoDocumentos";
	private static final String SIGEM_TRAMITACION_DATOS_ESPECIFICOS_KEY="sigem.tramitacion.datosEspecificos";
	private static final String SIGEM_TRAMITACION_ID_FASE_TERMINACION_KEY="sigem.tramitacion.idFaseTerminacion";
	private static final String SIGEM_TRAMITACION_ID_FASE_ARCHIVO_KEY="sigem.tramitacion.idFaseArchivo";

	private static final String SIGEM_PAGO_ENDPOINT_KEY="sigem.pago.endpoint";
	private static final String SIGEM_PAGO_ENTIDAD_EMISORA_KEY="sigem.pago.entidadEmisora";
	private static final String SIGEM_PAGO_ID_TASA_KEY="sigem.pago.idTasa";
	//private static final String SIGEM_PAGO_DIAS_PLAZO_KEY="sigem.pago.diasPlazo";
	private static final String SIGEM_PAGO_NOMBRE_RESGUARDO_KEY="sigem.pago.nombreResguardo";
	//private static final String SIGEM_PAGO_NOMBRE_SOLICITUD_KEY="sigem.pago.nombreSolicitud";
	//private static final String SIGEM_PAGO_NOMBRE_ESCRITURA_KEY="sigem.pago.nombreEscritura";
	private static final String SIGEM_PAGO_DETALLE_KEY="sigem.pago.detalle";

	private static final String SIGEM_SIGN_KEYSTORE_PATH_KEY="pdf.sign.keystore.path";
	private static final String SIGEM_SIGN_KEYSTORE_TYPE_KEY="pdf.sign.keystore.type";
	private static final String SIGEM_SIGN_KEYSTORE_PASSWORD_KEY="pdf.sign.keystore.password";
	private static final String SIGEM_SIGN_KEYSTORE_ALIAS_KEY="pdf.sign.keystore.alias";
	private static final String SIGEM_SIGN_KEYSTORE_ALIAS_PASSWORD_KEY="pdf.sign.keystore.alias.password";
	private static final String SIGEM_SIGN_RECEIPT_PATH_KEY="pdf.sign.receipt.path";
	private static final String SIGEM_SIGN_REASON_KEY="pdf.sign.reason";
	private static final String SIGEM_SIGN_LOCATION_KEY="pdf.sign.location";
	private static final String SIGEM_SIGN_AUX1_KEY="pdf.sign.aux1";
	private static final String SIGEM_SIGN_FIELD_KEY="pdf.sign.field";
	private final SigemConfigFilePathResolver pathResolver=SigemConfigFilePathResolver.getInstance();
	
	
	private ConcurrentHashMap<String, Properties> configEntidades;
	
	private Configuration(){
		configEntidades=new ConcurrentHashMap();
	}
	
	public void load() {
		String idEntidad=getIdEntidad();
		//if(instance.configEntidades.containsKey(idEntidad)) return;
		
		//pathResolver = ;
		
		Properties properties=null;
		String configRelativePathsubdir = CONFIG_SUBDIR+File.separator+idEntidad;
		try {
			properties = loadProperties(CONFIG_FILE, configRelativePathsubdir);
		} catch (Exception e) {
			configRelativePathsubdir = CONFIG_SUBDIR + File.separator + DEFAULT_ENTIDAD_SUBDIR;
			try {
				properties = loadProperties(CONFIG_FILE, configRelativePathsubdir);
			} catch (Exception ex) {
				configRelativePathsubdir = CONFIG_SUBDIR;

				try {
					properties = loadProperties(CONFIG_FILE, configRelativePathsubdir);
				} catch (Exception exc) {
					logger.error("No se ha podido cargar el fichero de configuración: " +
								CONFIG_FILE+ " para la entidad: " + idEntidad, exc);
				}
			}
		}
		
		if(properties!=null){
			properties.put(CONFIG_DIR_KEY,configRelativePathsubdir);
			properties.put(FECHA_ULTIMA_CARGA_KEY,new Date());
			configEntidades.put(idEntidad, properties);
		}
	}
	
	private Properties loadProperties(String filename,String subDir) throws Exception{
		Properties props=null;
		long lastModified=pathResolver.loadResource(filename, subDir).getFile().lastModified();
		Date fechaUltimaCarga=(Date)getPropertyObject(FECHA_ULTIMA_CARGA_KEY);
		if(fechaUltimaCarga==null || lastModified>fechaUltimaCarga.getTime()){
			props = pathResolver.loadProperties(filename, subDir);
		}
		return props;
	}
	
	public static Configuration getInstance(){
		if(instance==null){
			instance=new Configuration();
		}
		String idEntidad=getIdEntidad();
		//if(!instance.configEntidades.containsKey(idEntidad)){
			instance.load();
		//}
		return instance;
	}
		
	public static String getIdEntidad(){
		return MultiEntityContextHolder.getEntity();
	}
	
	public static IPlusvaliaEntidadService getPlusvaliaEntidadService(){
		return (IPlusvaliaEntidadService)getInstance().getBean(
				get(SERVICE_SPRING_BEAN_ENTIDAD_KEY));
	}
	public static String getServicePlusvaliasAlowIPs(){
		return get(SERVICE_PLUSVALIAS_ALLOW_IP_KEY);
	}
	
	public static String getRegistroOficina(){
		return get(SIGEM_REGISTRO_OFICINA_KEY);
	}
	public static String getRegistroOrgano(){
		return get(SIGEM_REGISTRO_ORGANO_KEY);
	}
	public static String getRegistroAsunto(){
		return get(SIGEM_REGISTRO_ASUNTO_KEY);
	}
	public static String getRegistroXmlSolicitud(){
		return get(SIGEM_REGISTRO_XML_SOLICITUD_KEY);
	}
	
	public static String getTramitacionEndPoint(){
		return get(SIGEM_TRAMITACION_ENDPOINT_KEY);
	}
	public static String getTramitacionCodProcedimiento(){
		return get(SIGEM_TRAMITACION_COD_PROCEDIMIENTO_KEY);
	}
	public static String getTramitacionDatosEspecificos(){
		return get(SIGEM_TRAMITACION_DATOS_ESPECIFICOS_KEY);
	}
	public static String getTramitacionIdFaseTerminacion(){
		return get(SIGEM_TRAMITACION_ID_FASE_TERMINACION_KEY);
	}
	public static String getTramitacionIdFaseArchivo(){
		return get(SIGEM_TRAMITACION_ID_FASE_ARCHIVO_KEY);
	}
	
	public static String getPagoEndPoint(){
		return get(SIGEM_PAGO_ENDPOINT_KEY);
	}
	public static String getPagoEntidadEmisora(){
		return get(SIGEM_PAGO_ENTIDAD_EMISORA_KEY);
	}
	public static String getPagoIdTasa(){
		return get(SIGEM_PAGO_ID_TASA_KEY);
	}
	public static String getPagoNombreResguardo(){
		return get(SIGEM_PAGO_NOMBRE_RESGUARDO_KEY);
	}
	public static String getPagoDetalle(){
		return get(SIGEM_PAGO_DETALLE_KEY);
	}
	
	public static String getFirmaPdfKeyStorePath(){
		return get(SIGEM_SIGN_KEYSTORE_PATH_KEY);
	}
	public static String getFirmaPdfKeyStoreType(){
		return get(SIGEM_SIGN_KEYSTORE_TYPE_KEY);
	}
	public static String getFirmaPdfKeyStorePassword(){
		return get(SIGEM_SIGN_KEYSTORE_PASSWORD_KEY);
	}
	public static String getFirmaPdfKeyStoreAlias(){
		return get(SIGEM_SIGN_KEYSTORE_ALIAS_KEY);
	}
	public static String getFirmaPdfKeyStoreAliasPassword(){
		return get(SIGEM_SIGN_KEYSTORE_ALIAS_PASSWORD_KEY);
	}
	public static String getFirmaPdfReceiptPath(){
		return get(SIGEM_SIGN_RECEIPT_PATH_KEY);
	}
	public static String getFirmaPdfReason(){
		return get(SIGEM_SIGN_REASON_KEY);
	}
	public static String getFirmaPdfLocation(){
		return get(SIGEM_SIGN_LOCATION_KEY);
	}
	public static String getFirmaPdfAux(){
		return get(SIGEM_SIGN_AUX1_KEY);
	}
	public static String getFirmaPdfField(){
		return get(SIGEM_SIGN_FIELD_KEY);
	}
	
	public static byte[] getPlantillaJustificantePago(){
		return getInstance().getPlantillaJustificante();
	}
	
	private byte[] getPlantillaJustificante(){
		byte[] res=null;
		String configPath=get(CONFIG_DIR_KEY);
		Resource resource=null;
		String justificanteSubPath=getProperty(SIGEM_SIGN_RECEIPT_PATH_KEY);
		try {
			resource = pathResolver.loadResource(justificanteSubPath, configPath);
			res=getReadResource(resource);
		} catch (Exception e) {
			try {
				resource = pathResolver.loadResource(DEFAULT_RECEIPT_FILE, configPath);
				res=getReadResource(resource);
			}catch(Exception ex){
				try{
					res=FileManager.readBytesFromFile(justificanteSubPath);
				}catch (Exception exc) {
					logger.error("No se ha podido cargar la plantilla del justificante de pago: " +
							CONFIG_FILE+ " para la entidad: " + getIdEntidad(), exc);
				}
			} 
		}
		return res;
	}
	
	private byte[] getReadResource(Resource resource) throws Exception{
		byte[] bytes=new byte[(int)resource.getFile().length()];
		InputStream is=resource.getInputStream();
		is.read(bytes);
		return bytes;	
	}
	
	private static String get(String key){
		return getInstance().getProperty(key);
	}
	
	private Object getBean(String idBean){
		if(StringUtils.isEmpty(idBean)) return null;
		return SpringWebContext.getContext().getBean(idBean);
	}
	
	private String getProperty(String key){
		return (String)getPropertyObject(key);
	}
	
	private Object getPropertyObject(String key){
		String idEntidad=getIdEntidad();
		Properties properties=(Properties)configEntidades.get(idEntidad);
		if(properties==null) return null;
		return properties.getProperty(key);
	}
}
