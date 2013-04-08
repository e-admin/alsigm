package ieci.tecdoc.sgm.administracion.utils;

import javax.servlet.http.HttpServletRequest;

import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.administracion.Aplicacion;
import ieci.tecdoc.sgm.core.services.administracion.Perfil;
import ieci.tecdoc.sgm.core.services.administracion.ServicioAdministracion;
import ieci.tecdoc.sgm.core.services.entidades.Entidad;
import ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion;
import ieci.tecdoc.sgm.core.services.permisos_backoffice.DatosUsuario;
import ieci.tecdoc.sgm.core.services.permisos_backoffice.ServicioPermisosBackOffice;

public class Utilidades {

	public static boolean isNuloOVacio(Object cadena) {
		if((cadena == null) || ("".equals(cadena)) || ("null".equals(cadena))) {
			return true;
		}
		return false;
	}
	
	
	public static boolean entidadValida(String usuario, String idEntidad) {
		try {
			ServicioAdministracion oServicio = LocalizadorServicios.getServicioAdministracion();
			Entidad[] entidades = oServicio.getEntidades(usuario);
			if (entidades == null || entidades.length == 0)
				return false;
			for(int i=0; i<entidades.length; i++)
				if (entidades[i].getIdentificador().equalsIgnoreCase(idEntidad))
					return true;
			return false;
		} catch(Exception e) {
			return false;
		}
	}
	
	
	public static boolean aplicacionValida(String usuario, String idEntidad, String idAplicacion) {
		try {
			ServicioAdministracion oServicio = LocalizadorServicios.getServicioAdministracion();
			Aplicacion[] aplicaciones = oServicio.getAplicaciones(usuario, idEntidad); 
			if (aplicaciones == null || aplicaciones.length == 0)
				return false;
			for(int i=0; i<aplicaciones.length; i++)
				if (aplicaciones[i].getIdentificador().equalsIgnoreCase(idAplicacion))
					return true;
			return false;
		} catch(Exception e) {
			return false;
		}
	}
	
	public static String obtenerUrlAplicacion(HttpServletRequest request, Aplicacion aplicacion) {
		try {
			//String Url = aplicacion.getProtocolo() + "://" +
			//					(Utilidades.isNuloOVacio(aplicacion.getServidorApp()) ? request.getServerName() : aplicacion.getServidorApp()) +
			//					(Utilidades.isNuloOVacio(aplicacion.getPuertoApp()) ? "" : (":" + aplicacion.getPuertoApp())) +
			//					aplicacion.getContextoApp();
			String Url = ".." + aplicacion.getContextoApp();
			return Url;
		} catch(Exception e) {
			return null;
		}
	}
	
	public static boolean accesoAdministracion(String usuario) {
		try {
			ServicioAdministracion oServicio = LocalizadorServicios.getServicioAdministracion();
			Perfil[] perfiles = oServicio.getPerfiles(usuario);
			for(int i=0; i<perfiles.length; i++) {
				if (ConstantesGestionUsuariosAdministracion.APLICACION_ADMINISTRACION.equalsIgnoreCase(perfiles[i].getIdAplicacion()))
					return true;
			}
			return false;
		} catch(Exception e) {
			return false;
		}
	}
	
	public static boolean permisosEntidad(String usuario, String entidad) {
		try {
			ServicioAdministracion oServicio = LocalizadorServicios.getServicioAdministracion();
			Entidad[] entidades = oServicio.getEntidades(usuario);
			if (entidades != null) {
				for(int i=0; i<entidades.length; i++)
					if (entidad.equals(entidades[i].getIdentificador()))
						return true;
			}
		} catch(Exception e) { }
		return false;
	}
	
	public static boolean permisosAplicacion(String usuario, String entidad, String aplicacion) {
		try {
			ServicioAdministracion oServicio = LocalizadorServicios.getServicioAdministracion();
			Perfil[] perfiles = oServicio.getPerfiles(usuario, entidad);
			for(int i=0; i<perfiles.length; i++) {
					if (aplicacion.equals(perfiles[i].getIdAplicacion()))
						return true;
			}
		} catch(Exception e) { }
		return false;
	}
	
	public static boolean permisosAplicacionInternos(int usuario, String entidad, String aplicacion) {
		try {
			ServicioPermisosBackOffice oServicio = LocalizadorServicios.getServicioPermisosBackOffice();
			DatosUsuario datosUsuario = oServicio.obtenerDatosUsuario(usuario, entidad);
			String[] permisos = datosUsuario.get_idAplicaciones();
			for(int i=0; i<permisos.length; i++) {
					if (aplicacion.equals(permisos[i]))
						return true;
			}
		} catch(Exception e) { }
		return false;
	}
	
	public static Aplicacion[] obtenerAplicacionesInterno(int usuario, String entidad) {
		Aplicacion[] aplicaciones = null;
		
		try {
			ServicioPermisosBackOffice oServicio = LocalizadorServicios.getServicioPermisosBackOffice();
			ServicioAdministracion oServicioAdm = LocalizadorServicios.getServicioAdministracion();
			DatosUsuario datosUsuario = oServicio.obtenerDatosUsuario(usuario, entidad);
			String[] permisos = datosUsuario.get_idAplicaciones();
			aplicaciones = new Aplicacion[permisos.length];
			Aplicacion[] apps = oServicioAdm.getAplicaciones();
			int k=0;
			for(int i=0; i<permisos.length; i++) {
				for(int j=0; j<apps.length; j++) {
					if (permisos[i].equals(apps[j].getIdentificador())) {
						aplicaciones[k] = apps[j];
						k++;
						break;
					}
				}
			}
		} catch(Exception e) { }
		
		return aplicaciones;
	}
	
	public static int obtenerIdUsuario(String datosEspecificos) {
		int index1 = datosEspecificos.indexOf("<IdUsuario>");
		int index2 = datosEspecificos.indexOf("</IdUsuario>", index1);
		return new Integer(datosEspecificos.substring(index1 + "<IdUsuario>".length(), index2)).intValue();
	}
}
