package es.ieci.tecdoc.isicres.admin.business.spring;

import es.ieci.tecdoc.isicres.admin.business.manager.GestionDCOManager;
import es.ieci.tecdoc.isicres.admin.business.manager.IntercambioRegistralManager;
import es.ieci.tecdoc.isicres.api.intercambioregistral.business.manager.TipoTransporteIntercambioRegistralManager;

public class AdminIRManagerProvider {

	private static final String TIPO_TRANSPORTE_INTERCAMBIO_REGISTRAL_MANAGER_BEAN_NAME = "tipoTransporteIntercambioRegistralManager";
	private static final String GESTION_DCO_ADMIN_MANAGER_BEAN_NAME = "gestionDCOAdminManager";
	private static final String INTERCAMBIO_REGISTRAL_ADMIN_MANAGER_BEAN_NAME = "intercambioRegistralAdminManager";
	protected static AdminIRManagerProvider instance = null;

	protected AdminIRManagerProvider() {
		super();
	}

	public static AdminIRManagerProvider getInstance() {
		if (instance == null) {
			instance = new AdminIRManagerProvider();
		}

		return instance;
	}

	public IntercambioRegistralManager getIntercambioRegistralManager() {
		IntercambioRegistralManager result = (IntercambioRegistralManager) getGenericBean(INTERCAMBIO_REGISTRAL_ADMIN_MANAGER_BEAN_NAME);
		return result;
	}

	public GestionDCOManager getGestionDCOManager() {
		GestionDCOManager result = (GestionDCOManager) getGenericBean(GESTION_DCO_ADMIN_MANAGER_BEAN_NAME);
		return result;
	}

	public TipoTransporteIntercambioRegistralManager getTipoTransporteIntercambioRegistralManager() {
		TipoTransporteIntercambioRegistralManager result = (TipoTransporteIntercambioRegistralManager) getGenericBean(TIPO_TRANSPORTE_INTERCAMBIO_REGISTRAL_MANAGER_BEAN_NAME);
		return result;
	}

	public Object getGenericBean(String bean) {
		Object result = AppContext.getApplicationContext().getBean(bean);
		return result;
	}

}
