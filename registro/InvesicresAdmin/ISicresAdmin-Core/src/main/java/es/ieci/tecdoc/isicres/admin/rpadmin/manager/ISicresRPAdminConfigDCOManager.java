package es.ieci.tecdoc.isicres.admin.rpadmin.manager;

import es.ieci.tecdoc.isicres.admin.business.manager.GestionDCOManager;
import es.ieci.tecdoc.isicres.admin.business.spring.AppContext;
import es.ieci.tecdoc.isicres.admin.business.spring.AdminIRManagerProvider;

public class ISicresRPAdminConfigDCOManager {

	private static GestionDCOManager gestionDCOManager = AdminIRManagerProvider.getInstance().getGestionDCOManager();

	public void inicializarDCO(){

		gestionDCOManager.inicializarDCO();
	}

	public void actualizarDCO(){

		gestionDCOManager.actualizarDCO();
	}

}
