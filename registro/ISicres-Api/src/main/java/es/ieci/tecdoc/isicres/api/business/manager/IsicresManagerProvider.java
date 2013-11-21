package es.ieci.tecdoc.isicres.api.business.manager;

import com.ieci.tecdoc.common.adapter.PersonValidation;

import es.ieci.tecdoc.isicres.api.IsicresSpringAppContext;
import es.ieci.tecdoc.isicres.api.business.manager.impl.builder.BaseRegistroVOBuilder;
import es.ieci.tecdoc.isicres.api.compulsa.business.manager.CompulsaManager;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.manager.DocumentoElectronicoAnexoManager;
import es.ieci.tecdoc.isicres.api.documento.electronico.business.manager.DocumentoTipoDocumentalSicresManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.ConfiguracionIntercambioRegistralManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralActualizadorEstadosManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralGeneradorObjetosManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralManager;

public class IsicresManagerProvider {

	private static final String CONFIGURACION_INTERCAMBIO_REGISTRAL_MANAGER_BEAN = "configuracionIntercambioRegistralManager";
	private static final String CONTEXTO_APLICACION_MANAGER_BEAN = "contextoAplicacionManager";
	private static final String LOGIN_MANAGER_BEAN = "loginManager";
	private static final String LIBRO_MANAGER_BEAN = "libroManager";
	private static final String REGISTRO_MANAGER_BEAN = "registroManager";
	private static final String TIPO_ASUNTO_MANAGER_BEAN = "tipoAsuntoManager";
	private static final String UNDAD_ADM_MANAGER_BEAN = "unidadAdministrativaManager";
	private static final String PERMISOS_MANAGER_BEAN = "permisosManager";
	private static final String DISTRIBUCION_MANAGER_BEAN = "distribucionManager";
	private static final String OFICINA_MANAGER_BEAN = "oficinaManager";
	private static final String REPORT_MANAGER_BEAN = "reportManager";
	private static final String BASE_REGISTROVOBUILDER_MANAGER_BEAN = "baseRegistroVOBuilder";
	private static final String PERSONVALIDATOR_MANAGER_BEAN = "personValidationManager";
	private static final String COMPULSA_MANAGER_BEAN = "isicresCompulsaManager";

	private static final String DOCUMENTOTIPODOCUMENTAL_MANAGER_BEAN = "documentotipoDocumentalSicresManager";

	private static final String INTERCAMBIO_REGISTRAL_MANAGER_BEAN = "intercambioRegistralManager";
	private static final String INTERCAMBIO_REGISTRAL_GENERADOR_OBJETOS_MANAGER_BEAN = "intercambioRegistralGeneradorObjetosManager";
	private static final String INTERCAMBIO_REGISTRAL_ACTUALIZADOR_ESTADOS_MANAGER_BEAN = "intercambioRegistralActualizadorEstadosManager";

	private static final String DOCUMENTO_ELECTRONICO_ANEXO_MANAGER_BEAN = "documentoElectronicoAnexoManager";

	protected static IsicresManagerProvider instance=null;

	protected IsicresManagerProvider(){
		super();
	}

	public static IsicresManagerProvider getInstance(){
		if (instance==null){
			instance = new IsicresManagerProvider();
		}

		return instance;
	}

	public IntercambioRegistralManager getIntercambioRegistralManager() {
		IntercambioRegistralManager result = (IntercambioRegistralManager) getGenericBean(INTERCAMBIO_REGISTRAL_MANAGER_BEAN);
		return result;
	}

	public IntercambioRegistralGeneradorObjetosManager getIntercambioRegistralGeneradorOjetosManager() {
		IntercambioRegistralGeneradorObjetosManager result = (IntercambioRegistralGeneradorObjetosManager) getGenericBean(INTERCAMBIO_REGISTRAL_GENERADOR_OBJETOS_MANAGER_BEAN);
		return result;
	}

	public IntercambioRegistralActualizadorEstadosManager getIntercambioRegistralActualizadorEstadosManager(){
		IntercambioRegistralActualizadorEstadosManager result = (IntercambioRegistralActualizadorEstadosManager)getGenericBean(INTERCAMBIO_REGISTRAL_ACTUALIZADOR_ESTADOS_MANAGER_BEAN);
		return result;
	}

	public DocumentoElectronicoAnexoManager getDocumentoElectronicoAnexoManager(){
		DocumentoElectronicoAnexoManager result = (DocumentoElectronicoAnexoManager) getGenericBean(DOCUMENTO_ELECTRONICO_ANEXO_MANAGER_BEAN);
		return result;
	}

	public ContextoAplicacionManager getContextoAplicacionManager(){
		ContextoAplicacionManager result = (ContextoAplicacionManager) getGenericBean(CONTEXTO_APLICACION_MANAGER_BEAN);
		return result;
	}

	public LoginManager getLoginManager(){
		LoginManager result = (LoginManager) getGenericBean(LOGIN_MANAGER_BEAN);
		return result;
	}

	public LibroManager getLibroManager(){
		LibroManager result = (LibroManager) getGenericBean(LIBRO_MANAGER_BEAN);
		return result;
	}

	public RegistroManager getRegistroManager(){
		RegistroManager result = (RegistroManager) getGenericBean(REGISTRO_MANAGER_BEAN);
		return result;
	}

	public TipoAsuntoManager getTipoAsuntoManager(){
		TipoAsuntoManager result = (TipoAsuntoManager) getGenericBean(TIPO_ASUNTO_MANAGER_BEAN);
		return result;
	}

	public UnidadAdministrativaManager getUnidadAdministrativaManager(){
		UnidadAdministrativaManager result = (UnidadAdministrativaManager) getGenericBean(UNDAD_ADM_MANAGER_BEAN);
		return result;
	}

	public PermisosManager getPermisosManager(){
		PermisosManager result = (PermisosManager) getGenericBean(PERMISOS_MANAGER_BEAN);
		return result;
	}

	public DistribucionManager getDistribucionManager(){
		DistribucionManager result = (DistribucionManager) getGenericBean(DISTRIBUCION_MANAGER_BEAN);
		return result;
	}

	public OficinaManager getOficinaManager(){
		OficinaManager result = (OficinaManager) getGenericBean(OFICINA_MANAGER_BEAN);
		return result;
	}

	public ReportManager getReportManager(){
		ReportManager result = (ReportManager) getGenericBean(REPORT_MANAGER_BEAN);
		return result;
	}

	public BaseRegistroVOBuilder getBaseRegistroVOBuilder(){
		BaseRegistroVOBuilder result = (BaseRegistroVOBuilder) getGenericBean(BASE_REGISTROVOBUILDER_MANAGER_BEAN);
		return result;
	}

	public PersonValidation getPersonValidation(){
		PersonValidation result = (PersonValidation) getGenericBean(PERSONVALIDATOR_MANAGER_BEAN);
		return result;
	}

	public CompulsaManager getCompulsaManager(){
		CompulsaManager result = (CompulsaManager) getGenericBean(COMPULSA_MANAGER_BEAN);
		return result;
	}

	public DocumentoTipoDocumentalSicresManager getDocumentoTipoDocumentalSicresManager(){
		DocumentoTipoDocumentalSicresManager result = (DocumentoTipoDocumentalSicresManager) getGenericBean(DOCUMENTOTIPODOCUMENTAL_MANAGER_BEAN);
		return result;
	}

	public ConfiguracionIntercambioRegistralManager getConfiguracionIntercambioRegistralManager(){
		ConfiguracionIntercambioRegistralManager result = (ConfiguracionIntercambioRegistralManager) getGenericBean(CONFIGURACION_INTERCAMBIO_REGISTRAL_MANAGER_BEAN);
		return result;
	}



	public Object getGenericBean(String bean) {
		Object result =IsicresSpringAppContext.getApplicationContext().getBean(bean);
		return result;
	}

}


