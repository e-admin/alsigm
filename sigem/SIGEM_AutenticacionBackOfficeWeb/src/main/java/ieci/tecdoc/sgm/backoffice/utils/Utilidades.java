package ieci.tecdoc.sgm.backoffice.utils;

import ieci.tecdoc.sgm.backoffice.temporal.Aplicacion;
import ieci.tecdoc.sgm.core.admin.web.AutenticacionBackOffice;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.ConstantesGestionUsuariosBackOffice;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class Utilidades {

	public static boolean isNuloOVacio(Object cadena) {
		if((cadena == null) || ("".equals(cadena)) || ("null".equals(cadena))) {
			return true;
		}
		return false;
	}
	
	public static List obtenerListaAplicaciones(HttpServletRequest request, String key){
		/* TODO
		 * Llamada al servicio de administración.
		 */
		List oLista = new ArrayList();
		Aplicacion oAplicacion = new Aplicacion();
		oAplicacion.setIdentificador(ConstantesGestionUsuariosBackOffice.APLICACION_ARCHIVO);
		oAplicacion.setNombre("Archivo");
		oAplicacion.setUrl(AutenticacionBackOffice.obtenerUrlAplicacion(request, ConstantesGestionUsuariosBackOffice.APLICACION_ARCHIVO, key));
		oLista.add(oAplicacion);
		
		// SLuna-20081216-I
		/*Aplicacion oAplicacion2 = new Aplicacion();
		oAplicacion2.setIdentificador(ConstantesGestionUsuariosBackOffice.APLICACION_TRAMITADOR_MANAGER);
		oAplicacion2.setNombre("Gestor de Expedientes");
		oAplicacion2.setUrl(AutenticacionBackOffice.obtenerUrlAplicacion(request, ConstantesGestionUsuariosBackOffice.APLICACION_TRAMITADOR_MANAGER, key));
		oLista.add(oAplicacion2);
		
		Aplicacion oAplicacion3 = new Aplicacion();
		oAplicacion3.setIdentificador(ConstantesGestionUsuariosBackOffice.APLICACION_REGISTRO_PRESENCIAL);
		oAplicacion3.setNombre("Registro Presencial");
		oAplicacion3.setUrl(AutenticacionBackOffice.obtenerUrlAplicacion(request, ConstantesGestionUsuariosBackOffice.APLICACION_REGISTRO_PRESENCIAL, key));
		oLista.add(oAplicacion3);*/
		// SLuna-20081216-F

		return oLista;
	}
}
