package ieci.tecdoc.sgm.registro.plugin;

import ieci.tecdoc.sgm.core.config.impl.spring.SigemConfigFilePathResolver;
import ieci.tecdoc.sgm.registro.utils.Defs;

import java.util.Properties;

public class ConfigLoader {

	public static String CONFIG_SUBDIR="SIGEM_RegistroTelematicoWeb";
	public static String CONFIG_FILE="registroTelematicoWebConfig.properties";

	protected Properties properties;


	public ConfigLoader(){
		properties= loadConfiguration();
	}

	public String getOrganismoValue(){
		String key=Defs.PLUGIN_ORGANISMO;
		String result=getValue(key);
		return result;
	}

	public String getTmpUploadPathValue(){
		String key=Defs.PLUGIN_TMP_PATH_UPLOAD;
		String result=getValue(key);
		return result;
	}

	public String getTmpXmlPathValue(){
		String key=Defs.PLUGIN_TMP_PATH_XML;
		String result=getValue(key);
		return result;
	}


	public String getTramitesPathValue(){
		String key=Defs.PLUGIN_TMP_PATH_TRAMITES;
		String result=getValue(key);
		return result;
	}

	public String getAFirmaValue(){
		String key=Defs.PLUGIN_AFIRMA;
		String result=getValue(key);
		return result;
	}

	public String getPlantillaValue(){
		String key=Defs.PLUGIN_PLANTILLA;
		String result=getValue(key);
		return result;
	}

	public String getCertificadoValue(){
		String key=Defs.PLUGIN_CERTIFICADO;
		String result=getValue(key);
		return result;
	}

	public String getRedirAutenticacionValue(){
		String key=Defs.PLUGIN_REDIRAUTENTICACION;
		String result=getValue(key);
		return result;
	}


	public String getIniciarExpedienteConVirusValue(){
		String key=Defs.PLUGIN_INICIAREXPEDIENTECONVIRUS;
		String result=getValue(key);
		return result;
	}

	public String getRegistrarConVirusValue(){
		String key=Defs.PLUGIN_REGISTRARCONVIRUS;
		String result=getValue(key);
		return result;
	}

	public String getIniciarExpedienteValue(){
		String key=Defs.PLUGIN_INICIAREXPEDIENTE;
		String result=getValue(key);
		return result;
	}

	public String getIniciarExpedienteErrorRegistroEstadoErrorValue(){
		String key=Defs.PLUGIN_INICIAREXPEDIENTEERRORREGISTROESTADOERROR;
		String result=getValue(key);
		return result;
	}

	public String getSubsanacionSinNumeroRegistroInicialCodigoOficinaValue(){
		String key=Defs.PLUGIN_SUBSANACIONSINNUMEROREGISTROINICIAL_CODIGO_OFICINA;
		String result=getValue(key);
		return result;
	}

	public String getSubsanacionSinNumeroRegistroInicialFirmarSolicitudValue(){
		String key=Defs.PLUGIN_SUBSANACIONSINNUMEROREGISTROINICIAL_FIRMAR_SOLICITUD;
		String result=getValue(key);
		return result;
	}
	
	public String getIniciarExpendienteErrorEnviarEmailOrigen(){
		String key=Defs.PLUGIN_INICIAREXPEDIENTEERROR_ENVIAR_EMAIL_ORIGEN;
		String result=getValue(key);
		return result;
	}
	
	public String getIniciarExpendienteErrorEnviarEmailDestino(){
		String key=Defs.PLUGIN_INICIAREXPEDIENTEERROR_ENVIAR_EMAIL_DESTINO;
		String result=getValue(key);
		return result;
	}
	
	public String getIniciarExpendienteErrorEnviarEmailAsunto(){
		String key=Defs.PLUGIN_INICIAREXPEDIENTEERROR_ENVIAR_EMAIL_ASUNTO;
		String result=getValue(key);
		return result;
	}

	protected String getValue(String key){
		String result="";
		result= properties.getProperty(key);
		return result;
	}

	protected Properties loadConfiguration(){

		Properties result=null;
		SigemConfigFilePathResolver pathResolver = SigemConfigFilePathResolver.getInstance();
		result= pathResolver.loadProperties(CONFIG_FILE, CONFIG_SUBDIR);

		return result;

	}

}
