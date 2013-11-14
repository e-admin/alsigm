package es.ieci.tecdoc.isicres.api.intercambioregistral.business.spring;

import es.ieci.tecdoc.isicres.api.documento.electronico.business.manager.DocumentoElectronicoAnexoManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralActualizadorEstadosManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralGeneradorObjetosManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.IntercambioRegistralManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.TipoTransporteIntercambioRegistralManager;


public class ManagerProvider{
	private static final String TIPO_TRANSPORTE_INTERCAMBIO_REGISTRAL_MANAGER_BEAN = "tipoTransporteIntercambioRegistralManager";
	private static final String INTERCAMBIO_REGISTRAL_MANAGER_BEAN = "intercambioRegistralManager";
	private static final String INTERCAMBIO_REGISTRAL_GENERADOR_OBJETOS_MANAGER_BEAN = "intercambioRegistralGeneradorObjetosManager";
	private static final String INTERCAMBIO_REGISTRAL_ACTUALIZADOR_ESTADOS_MANAGER_BEAN = "intercambioRegistralActualizadorEstadosManager";

	private static final String DOCUMENTO_ELECTRONICO_ANEXO_MANAGER_BEAN = "documentoElectronicoAnexoManager";

	protected static ManagerProvider instance=null;

	protected ManagerProvider(){
		super();
	}

	public static ManagerProvider getInstance(){
		if (instance==null){
			instance = new ManagerProvider();
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

	public TipoTransporteIntercambioRegistralManager getTipoTransporteIntercambioRegistralManager(){
		TipoTransporteIntercambioRegistralManager result = (TipoTransporteIntercambioRegistralManager) getGenericBean(TIPO_TRANSPORTE_INTERCAMBIO_REGISTRAL_MANAGER_BEAN);
		return result;
	}


	public Object getGenericBean(String bean) {
		Object result =AppContext.getApplicationContext().getBean(bean);
		return result;
	}



}
