package es.ieci.tecdoc.isicres.admin.rpadmin.manager;

import es.ieci.tecdoc.isicres.admin.business.manager.GestionDCOManager;
import es.ieci.tecdoc.isicres.admin.business.spring.AppContext;

public class ISicresRPAdminConfigDCOManager {

	private static GestionDCOManager gestionDCOManager = (GestionDCOManager) AppContext
			.getApplicationContext().getBean("gestionDCOManager");

	public void inicializarDCO(){

		gestionDCOManager.inicializarDCO();
	}

	public void actualizarDCO(){

		gestionDCOManager.actualizarDCO();
	}



}
