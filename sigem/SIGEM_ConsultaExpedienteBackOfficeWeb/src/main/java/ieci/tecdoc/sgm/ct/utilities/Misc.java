package ieci.tecdoc.sgm.ct.utilities;

import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.consulta.FicheroHito;
import ieci.tecdoc.sgm.core.services.consulta.FicherosHito;
import ieci.tecdoc.sgm.core.services.consulta.Notificacion;
import ieci.tecdoc.sgm.core.services.consulta.Notificaciones;
import ieci.tecdoc.sgm.core.services.consulta.Pago;
import ieci.tecdoc.sgm.core.services.consulta.Pagos;
import ieci.tecdoc.sgm.core.services.consulta.Subsanacion;
import ieci.tecdoc.sgm.core.services.consulta.Subsanaciones;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.permisos_backoffice.DatosUsuario;
import ieci.tecdoc.sgm.core.services.permisos_backoffice.ServicioPermisosBackOffice;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

public class Misc {

	public static boolean isEmpty(String cadena){
		return (cadena == null || cadena.trim().equals("") || cadena.equals("null"));
	}

	public static Entidad obtenerEntidad (String idEntidad) {
		Entidad entidad = new Entidad();
		entidad.setIdentificador(idEntidad);
		return entidad;
	}

	public static String obtenerMensaje(HttpServletRequest request, String cadena) {
		Locale locale = (Locale)request.getSession().getAttribute("org.apache.struts.action.LOCALE");
		String idioma = "es";
		if (locale != null)
			idioma = locale.getLanguage();
		try {
			int index = cadena.indexOf("<label locale=\"" + idioma + "\">");
			if (index != -1) {
				int index1 = cadena.indexOf("</label>", index);
				if (index1 != -1)
					return cadena.substring(index+("<label locale=\"" + idioma + "\">").length(), index1);
			}
			index = cadena.indexOf("<label locale=\"es\">");
			if (index != -1) {
				int index1 = cadena.indexOf("</label>", index);
				if (index1 != -1)
					return cadena.substring(index+("<label locale=\"es\">").length(), index1);
			}
			return cadena;
		} catch (Exception e) {
			return cadena;
		}
	}

	public static void modificarMensajesSubsanaciones(HttpServletRequest request, Subsanaciones subsanaciones) {
		if (subsanaciones != null && subsanaciones.count() > 0) {
			for(int i=0; i<subsanaciones.count(); i++) {
				((Subsanacion)subsanaciones.get(i)).setMensajeParaElCiudadano(obtenerMensaje(request, ((Subsanacion)subsanaciones.get(i)).getMensajeParaElCiudadano()));
			}
		}
	}

	public static void modificarMensajesPagos(HttpServletRequest request, Pagos pagos) {
		if (pagos != null && pagos.count() > 0) {
			for(int i=0; i<pagos.count(); i++) {
				((Pago)pagos.get(i)).setMensajeParaElCiudadano(obtenerMensaje(request, ((Pago)pagos.get(i)).getMensajeParaElCiudadano()));
			}
		}
	}

	public static void modificarMensajesNotificaciones(HttpServletRequest request, Notificaciones notificaciones) {
		if (notificaciones != null && notificaciones.count() > 0) {
			for(int i=0; i<notificaciones.count(); i++) {
				((Notificacion)notificaciones.get(i)).setDescripcion(obtenerMensaje(request, ((Notificacion)notificaciones.get(i)).getDescripcion()));
			}
		}
	}

	public static FicherosHito modificarMensajesFicherosHito(HttpServletRequest request, FicherosHito ficherosHito) {
		if (ficherosHito != null && ficherosHito.count() > 0) {
			for(int i=0; i<ficherosHito.count(); i++) {
				((FicheroHito)ficherosHito.get(i)).setTitulo(obtenerMensaje(request, ((FicheroHito)ficherosHito.get(i)).getTitulo()));
			}
		}else new FicherosHito();
		return ficherosHito;
	}

	public static boolean permisosAplicacion(int usuario, String entidad, String aplicacion) {
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

	public static int obtenerIdUsuario(String datosEspecificos) {
		int index1 = datosEspecificos.indexOf("<IdUsuario>");
		int index2 = datosEspecificos.indexOf("</IdUsuario>", index1);
		return new Integer(datosEspecificos.substring(index1 + "<IdUsuario>".length(), index2)).intValue();
	}

	public static final String NOACCESS_FORWARD = "noaccess";
	public static final String ENTRY_FORWARD = "entry";

	public static final String ENTIDAD_ID= "ENTIDAD_ID";
	public static final String SESION_ID = "SESION_ID";
	public static final String PERMISO_CE = "PERMISO_CE";

	public static final String MENSAJE_ERROR = "MENSAJE_ERROR";

	public static final String CNIF = "CNIF";
	public static final String EXPEDIENTE = "expediente";
	public static final String PROCEDIMIENTO = "procedimiento";
	public static final String NUMERO_REGISTRO_INICIAL = "numeroRegistroInicial";
	public static final String FECHA_DESDE = "fechaDesde";
	public static final String FECHA_DESDE_BUSQUEDA = "fechaDesdeBusqueda";
	public static final String OPERADOR_CONSULTA = "operadorConsulta";
	public static final String FECHA_HASTA = "fechaHasta";
	public static final String FECHA_HASTA_BUSQUEDA = "fechaHastaBusqueda";
	public static final String FECHA_REGISTRO_INICIAL_DESDE = "fechaRegistroInicialDesde";
	public static final String FECHA_REGISTRO_INICIAL_DESDE_BUSQUEDA = "fechaRegistroInicialDesdeBusqueda";
	public static final String OPERADOR_CONSULTA_FECHA_INICIAL = "operadorConsultaFechaInicial";
	public static final String FECHA_REGISTRO_INICIAL_HASTA = "fechaRegistroInicialHasta";
	public static final String FECHA_REGISTRO_INICIAL_HASTA_BUSQUEDA = "fechaRegistroInicialHastaBusqueda";
	public static final String ESTADO = "estado";
}
