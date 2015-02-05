package ieci.tecdoc.sgm.ct.utilities;

import ieci.tecdoc.sgm.autenticacion.util.TipoSolicitante;
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
import ieci.tecdoc.sgm.core.services.sesion.InfoUsuario;
import ieci.tecdoc.sgm.core.services.sesion.ServicioSesionUsuario;
import ieci.tecdoc.sgm.core.user.web.SesionUserHelper;
import ieci.tecdoc.sgm.ct.exception.ConsultaCodigosError;
import ieci.tecdoc.sgm.ct.exception.ConsultaExcepcion;

import java.security.cert.X509Certificate;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Misc {

	public static X509Certificate getCertificadoCliente(HttpServletRequest request)
	throws ConsultaExcepcion {

		X509Certificate cert = null;

		Object obj =
		request.getAttribute("javax.servlet.request.X509Certificate");

		if (obj instanceof X509Certificate[]) {
			X509Certificate[] certArr = (X509Certificate[])obj;
			cert = certArr[0];
		} else if(obj instanceof X509Certificate) {
			cert = (X509Certificate)obj;
		}

		if (cert == null) {
			 throw new ConsultaExcepcion(ConsultaCodigosError.EC_CERTIFICADO_NO_ENCONTRADO);
		}

		return cert;

	}

	public static String getCNIFUsuario(HttpServletRequest request, String  sessionID){

		HttpSession session = request.getSession();

		String cnif = (String) session.getAttribute(CNIF);
		if (!isEmpty(cnif)) {
			return cnif;
		}
		else {
			 try {
				 ServicioSesionUsuario oServicio = LocalizadorServicios.getServicioSesionUsuario();

				 InfoUsuario solicitante = oServicio.getInfoUsuario(sessionID, Misc.obtenerEntidad(request));
				 //Solicitante solicitante = SesionManager.getSender(sessionID);

				 // Persona fisica o juridica
			     if (solicitante.getInQuality().equals(String.valueOf(TipoSolicitante.INDIVIDUAL))) {

			    	 // Persona fisica (individual)
			    	 cnif = solicitante.getId();
					 session.setAttribute(NOMBRE_USUARIO, solicitante.getName());
			     }
			     else {
			    	 // Persona juridica (representante legal)
			    	 cnif = solicitante.getCIF();
			    	 String nombreUsuario = solicitante.getSocialName();
			    	 if (!isEmpty(solicitante.getName())) {
			    		 nombreUsuario += " (" + solicitante.getName() + ")";
			    	 }
					 session.setAttribute(NOMBRE_USUARIO, nombreUsuario);
			     }

			     session.setAttribute(CNIF, cnif);
				 session.setAttribute(SESION_ID, sessionID);
			 }
			 catch(Exception ex){
				 request.setAttribute(MENSAJE_ERROR, ex.getMessage());
			 }
		 }

		 return cnif;
	}

	public static boolean isEmpty(String cadena){
		return (cadena == null || cadena.trim().equals("") || cadena.equals("null"));
	}

	public static Entidad obtenerEntidad(HttpServletRequest request){
		Entidad oEntidad = new Entidad();
		oEntidad.setIdentificador(SesionUserHelper.obtenerIdentificadorEntidad(request));
		return oEntidad;
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

	public static final String IDIOMAS_DISPONIBLES = "IdiomasDisponibles";
	public static final String LANG = "LANG";
	public static final String COUNTRY = "COUNTRY";

	public static final String NOMBRE_USUARIO = "NOMBRE_USUARIO";
	public static final String SESION_ID = "SESION_ID";
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
