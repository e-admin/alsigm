package ieci.tecdoc.sgm.core.admin.web;

/*
 * $Id: AdministracionHelper.java,v 1.2 2008/03/17 11:28:21 jnogales Exp $
 */

import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.entidades.ServicioEntidades;

import java.util.ArrayList;
import java.util.List;

public class AdministracionHelper {

	/**
	 * Obtiene la lista de entidades del sistema SIGEM
	 * @return List ArrayList de objetos eci.tecdoc.sgm.core.services.dto.Entidad
	 */
	public static List obtenerListaEntidades(){
		try {
			ServicioEntidades oServicio = LocalizadorServicios.getServicioEntidades();
			List oLista = oServicio.obtenerEntidades();
			return getEntidades(oLista);
		} catch (Exception e) {
			return new ArrayList();
		}
	}
	
	private static List getEntidades(List oLista){
		if (oLista == null)
			return new ArrayList();
		
		for(int i=0; i<oLista.size(); i++){
			oLista.set(i, getEntidad((ieci.tecdoc.sgm.core.services.entidades.Entidad)oLista.get(i)));
		}
		
		return oLista;
	}
	
	private static Entidad getEntidad(ieci.tecdoc.sgm.core.services.entidades.Entidad oEntidad) {
		if (oEntidad == null)
			return null;
		
		Entidad poEntidad = new Entidad();
		
		poEntidad.setIdentificador(oEntidad.getIdentificador());
		poEntidad.setNombre(oEntidad.getNombreLargo());
		
		return poEntidad;
	}
}
