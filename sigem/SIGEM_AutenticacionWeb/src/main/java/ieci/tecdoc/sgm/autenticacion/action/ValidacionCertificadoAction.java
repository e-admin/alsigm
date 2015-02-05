package ieci.tecdoc.sgm.autenticacion.action;

import ieci.tecdoc.sgm.autenticacion.utils.Defs;
import ieci.tecdoc.sgm.core.config.ports.PortsConfig;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.sesion.ServicioSesionUsuario;
import ieci.tecdoc.sgm.core.services.sesion.SesionUsuarioException;

import java.io.IOException;
import java.math.BigInteger;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class ValidacionCertificadoAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		HttpSession session = request.getSession();
		String entidadId = (String)request.getSession().getAttribute(Defs.ENTIDAD_ID);

		Collection certificados = new ArrayList();
		BigInteger certId = new BigInteger("-1");

		try{
			String attributeName = "javax.servlet.request.X509Certificate";
			Object obj = request.getAttribute(attributeName);
	      	if (obj instanceof java.security.cert.X509Certificate[]) {
	        	java.security.cert.X509Certificate[] certArray = (java.security.cert.X509Certificate[]) obj;
				certId = certArray[0].getSerialNumber();
			}
			if (obj instanceof java.security.cert.X509Certificate) {
	        	certificados.add(((X509Certificate) obj).getSubjectDN().toString());
	        	certId = ((X509Certificate) obj).getSerialNumber();
			}
	   	}catch(Exception e){
	   		request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_LISTA_CERTIFICADOS);
	    	request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.getMessage());
	   		return mapping.findForward("failure");
	   	}

		X509Certificate certificate;
		String certificadoId = ""+certId;

		try{
			if (certificadoId==null || certificadoId.equals("")){
				request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_SELECCIONAR_CERTIFICADO);
				return mapping.findForward("failure");
			}

			certificate = getX509Certificate(request, certificadoId);
			if(certificate == null){
				request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_SELECCIONAR_CERTIFICADO);
				return mapping.findForward("failure");
			}
		}catch(Exception e){
			request.setAttribute(Defs.MENSAJE_ERROR, Defs.MENSAJE_ERROR_SELECCIONAR_CERTIFICADO);
	    	request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.getMessage());
	   		return mapping.findForward("failure");
	   	}
		request.getSession().setAttribute(Defs.CERTIFICADO_SELECCIONADO, certificate);

		String sessionIdIni = null;
		String sessionId = null;
		boolean aceptado = true;
		try{
			sessionIdIni = (String)session.getAttribute(Defs.SESION_ID);
			Entidad oEntidad = new Entidad();
			oEntidad.setIdentificador(entidadId);
			ServicioSesionUsuario oServicio = LocalizadorServicios.getServicioSesionUsuario();
			if (sessionIdIni!=null && !sessionIdIni.equals("")){
				sessionId = oServicio.login(sessionIdIni, "", certificate, oEntidad);
			}
			else{
				sessionId = oServicio.login(null, "", certificate, oEntidad);
			}
			session.setAttribute(Defs.SESION_ID, sessionId);

			String redireccion = (String)session.getAttribute(Defs.REDIRECCION);
			String url = (String)request.getSession().getServletContext().getAttribute("redir" + redireccion);
			String port = PortsConfig.getCertPort();
			session.setAttribute(Defs.URL_REDIRECCION, url);
			session.setAttribute(Defs.URL_PUERTO, port);
	   	}catch(SesionUsuarioException e){

	   		//TODO Revisar mensajes
	   		if (aceptado){
	   			if( (e.getErrorCode() == SesionUsuarioException.SECURITY_ERROR_CODE)
	   					|| (e.getErrorCode() == SesionUsuarioException.INVALID_CREDENTIALS_ERROR_CODE)  ){
	   				if(e.getErrorCode() == SesionUsuarioException.INVALID_CREDENTIALS_ERROR_CODE){
	   					request.setAttribute(Defs.MENSAJE_LOGIN, Defs.CERT_NO_VALIDO);
	   				}else{
	   					request.setAttribute(Defs.MENSAJE_LOGIN, Defs.CERT_REVOCADO);
	   				}
	   			}else{
	   				request.setAttribute(Defs.MENSAJE_LOGIN, Defs.CERT_REVOCADO);
	   			}
	   		}else request.setAttribute(Defs.MENSAJE_LOGIN, Defs.METODO_AUTH_NO_ACEPTADA);

	   		request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.getMessage());
	   		return mapping.findForward("failure");
	   	}
	   	catch (SigemException e) {

	   		request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.getMessage());
	   		return mapping.findForward("failure");
	   	}

	   	String mensaje = "";
	   	if (sessionId!=null){
	   		if (sessionId.equals(sessionIdIni))
	   			mensaje = Defs.CERT_YA_VALIDADO;
	   	}else{
	   		mensaje = Defs.CERT_NO_VALIDO;
	   		request.setAttribute(Defs.MENSAJE_LOGIN, mensaje);
	   		return mapping.findForward("failure");
	   	}

	   	request.setAttribute(Defs.MENSAJE_LOGIN, mensaje);
	   	return mapping.findForward("success");
	}


	public static X509Certificate getX509Certificate(HttpServletRequest request, String serialNumber) {
		String attributeName = "javax.servlet.request.X509Certificate";

		Object obj = request.getAttribute(attributeName);
		X509Certificate certificate = null;
		if (obj instanceof java.security.cert.X509Certificate[]) {
			java.security.cert.X509Certificate[] certArray = (java.security.cert.X509Certificate[]) obj;
			for(int i=0; i<certArray.length; i++)
				if(certArray[i].getSerialNumber().toString().equals(serialNumber))
					return certArray[i];
			return null;
		}

		if (obj instanceof java.security.cert.X509Certificate) {
			certificate = (X509Certificate) obj;
			if(certificate.getSerialNumber().toString().equals(serialNumber))
				return certificate;
			else return null;
		}

		return null;
	}
}
