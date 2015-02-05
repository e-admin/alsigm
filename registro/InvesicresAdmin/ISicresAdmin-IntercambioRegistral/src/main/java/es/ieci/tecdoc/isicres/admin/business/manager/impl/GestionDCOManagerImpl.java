package es.ieci.tecdoc.isicres.admin.business.manager.impl;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.fwktd.dir3.core.service.ServicioActualizacionDirectorioComun;
import es.ieci.tecdoc.fwktd.dir3.core.service.ServicioInicializacionDirectorioComun;
import es.ieci.tecdoc.isicres.admin.business.manager.GestionDCOManager;

public class GestionDCOManagerImpl implements GestionDCOManager {

	protected ServicioInicializacionDirectorioComun servicioInicializacionDirectorioComun;
	protected ServicioActualizacionDirectorioComun servicioActualizacionDirectorioComun;


	private static final Logger logger = Logger.getLogger(GestionDCOManagerImpl.class);

	public ServicioActualizacionDirectorioComun getServicioActualizacionDirectorioComun() {
		return servicioActualizacionDirectorioComun;
	}
	public void setServicioActualizacionDirectorioComun(
			ServicioActualizacionDirectorioComun servicioActualizacionDirectorioComun) {
		this.servicioActualizacionDirectorioComun = servicioActualizacionDirectorioComun;
	}
	public ServicioInicializacionDirectorioComun getServicioInicializacionDirectorioComun() {
		return servicioInicializacionDirectorioComun;
	}
	public void setServicioInicializacionDirectorioComun(
			ServicioInicializacionDirectorioComun servicioInicializacionDirectorioComun) {
		this.servicioInicializacionDirectorioComun = servicioInicializacionDirectorioComun;
	}


	public void obtenerScriptInicializacionDCO(){
		servicioInicializacionDirectorioComun.generateScriptsInicializacionDirectorioComun();
	}

	public void inicializarDCO(){
		servicioInicializacionDirectorioComun.inicializarDirectorioComun();
	}

	public void obtenerScriptActualizarDCO(){
		servicioActualizacionDirectorioComun.generateScriptsActualizacionDirectorioComun();
	}

	public void actualizarDCO(){
		servicioActualizacionDirectorioComun.actualizarDirectorioComun();
	}



}
