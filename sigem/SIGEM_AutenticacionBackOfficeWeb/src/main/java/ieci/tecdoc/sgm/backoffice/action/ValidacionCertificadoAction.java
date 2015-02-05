package ieci.tecdoc.sgm.backoffice.action;

import ieci.tecdoc.core.base64.Base64Util;
import ieci.tecdoc.sgm.backoffice.utils.Defs;
import ieci.tecdoc.sgm.backoffice.utils.Utilidades;
import ieci.tecdoc.sgm.core.admin.web.AdministracionHelper;
import ieci.tecdoc.sgm.core.admin.web.AutenticacionBackOffice;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.admsesion.backoffice.ServicioAdministracionSesionesBackOffice;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.ConstantesGestionUsuariosBackOffice;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.DatosUsuario;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.GestionUsuariosBackOfficeException;
import ieci.tecdoc.sgm.core.services.gestion_backoffice.ServicioGestionUsuariosBackOffice;
import ieci.tecdoc.sgm.core.services.sesion.SesionUsuarioException;

import java.io.IOException;
import java.math.BigInteger;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ValidacionCertificadoAction extends Action {

	private static final Logger logger = Logger
			.getLogger(ValidacionCertificadoAction.class);

	private static final String FORWARD_LOGIN = "login";

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		HttpSession session = request.getSession();

		String idEntidad = null, idAplicacion = null, usuario = null;

		idEntidad = (String)session.getAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD);
		if (Utilidades.isNuloOVacio(idEntidad)) {
			idEntidad = new String("");
		}

		idAplicacion = (String)session.getAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_APLICACION);
		if (Utilidades.isNuloOVacio(idAplicacion)) {
			idAplicacion = new String("");
		}

		if (Utilidades.isNuloOVacio(idEntidad)){
			List oLista = AdministracionHelper.obtenerListaEntidades();
			if(oLista.size()==1) {
				Entidad oEntidad = (Entidad)oLista.get(0);
				session.setAttribute(ConstantesGestionUsuariosBackOffice.PARAMETRO_ID_ENTIDAD, oEntidad.getIdentificador());
			} else {
				request.setAttribute("entidades", oLista);
		   		return mapping.findForward("entidades");
			}
		}

		Collection certificados = new ArrayList();
		BigInteger certId = new BigInteger("-1");

		try {
			String attributeName = "javax.servlet.request.X509Certificate";
			Object obj = request.getAttribute(attributeName);
			if (obj instanceof java.security.cert.X509Certificate[]) {
				java.security.cert.X509Certificate[] certArray = (java.security.cert.X509Certificate[]) obj;
				certId = certArray[0].getSerialNumber();
			}
			if (obj instanceof java.security.cert.X509Certificate) {
				certificados.add(((X509Certificate) obj).getSubjectDN()
						.toString());
				certId = ((X509Certificate) obj).getSerialNumber();
			}
		} catch (Exception e) {
			request.setAttribute(Defs.MENSAJE_ERROR,
					Defs.MENSAJE_ERROR_LISTA_CERTIFICADOS);
			request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.getMessage());
			return mapping.findForward("failure");
		}

		X509Certificate certificate;
		String certificadoId = "" + certId;

		try {
			if (certificadoId == null || certificadoId.equals("")) {
				request.setAttribute(Defs.MENSAJE_ERROR,
						Defs.MENSAJE_ERROR_SELECCIONAR_CERTIFICADO);
				return mapping.findForward("failure");
			}

			certificate = getX509Certificate(request, certificadoId);
			if (certificate == null) {
				request.setAttribute(Defs.MENSAJE_ERROR,
						Defs.MENSAJE_ERROR_SELECCIONAR_CERTIFICADO);
				return mapping.findForward("failure");
			}
		} catch (Exception e) {
			request.setAttribute(Defs.MENSAJE_ERROR,
					Defs.MENSAJE_ERROR_SELECCIONAR_CERTIFICADO);
			request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.getMessage());
			return mapping.findForward("failure");
		}
		request.getSession().setAttribute(Defs.CERTIFICADO_SELECCIONADO,
				certificate);

		boolean aceptado = true;
		try {
			Entidad oEntidad = new Entidad();
			oEntidad.setIdentificador(idEntidad);

			ServicioGestionUsuariosBackOffice oServicio = LocalizadorServicios
					.getServicioAutenticacionUsuariosBackOffice();
			DatosUsuario user = oServicio.authenticateUser(certificate,
					oEntidad);


			if (user != null) {

				usuario = user.getUser();

				ServicioAdministracionSesionesBackOffice oClient = LocalizadorServicios
						.getServicioAdministracionSesionesBackOffice();

				String key = oClient.nuevaSesion(usuario, idEntidad);

				// SLuna-20081217-I

				// Introducimos una nueva 'llave' en la sesión que sólo se
				// usará cuando entremos en el módulo de Registro
				// Presencial. Se crea una 'llave' con el usuario codificado
				// en BASE64 por si su nombre de usuario contiene algún
				// carácter no ANSI.
				String keyForRP = null;

				String usuarioBase64 = Base64Util.encodeString(usuario);
				StringBuffer buffer = new StringBuffer();

				buffer.append(usuarioBase64).append("##CODE##");
				keyForRP = oClient.nuevaSesion(buffer.toString(), idEntidad);
				session.setAttribute("keySesionUsuarioRP", keyForRP);

				// SLuna-20081217-F

				String datosSesion = "<IdUsuario>" + user.getId()
						+ "</IdUsuario>" + "<TipoAutenticacion>"
						+ user.getAuthenticationType() + "</TipoAutenticacion>";
				String tipoAutenticacion = user.getAuthenticationType();
				if ((!"".equals(tipoAutenticacion))
						&& (tipoAutenticacion
								.equals(DatosUsuario.AUTHENTICATION_TYPE_LDAP))) {
					datosSesion += "<LdapGuid>" + user.getLdapGuid()
							+ "</LdapGuid>";
				}

				session.setAttribute(
						ConstantesGestionUsuariosBackOffice.PARAMETRO_KEY_SESION_USUARIO,
						key);
				oClient.modificarDatosSesion(key, datosSesion);
				if (!Utilidades.isNuloOVacio(idAplicacion)) {
					if (!ConstantesGestionUsuariosBackOffice.APLICACION_ARCHIVO
							.equals(idAplicacion)) {
						String url = oServicio
								.obtenerDireccionAplicacion(idAplicacion);
						url = AutenticacionBackOffice
								.comprobarURL(request, url);
						request.setAttribute(Defs.PARAMETRO_URL, url);
						return mapping.findForward("success");
					}
				}

				List oLista = Utilidades.obtenerListaAplicaciones(request, key);
				request.setAttribute("aplicaciones", oLista);
				return mapping.findForward("aplicaciones");
			} else {
				request.setAttribute("invalid_user", "true");
				return mapping.findForward("login");
			}

		} catch (SesionUsuarioException e) {

			// TODO Revisar mensajes
			if (aceptado) {
				if ((e.getErrorCode() == SesionUsuarioException.SECURITY_ERROR_CODE)
						|| (e.getErrorCode() == SesionUsuarioException.INVALID_CREDENTIALS_ERROR_CODE)) {
					if (e.getErrorCode() == SesionUsuarioException.INVALID_CREDENTIALS_ERROR_CODE) {
						request.setAttribute(Defs.MENSAJE_LOGIN,
								Defs.CERT_NO_VALIDO);
					} else {
						request.setAttribute(Defs.MENSAJE_LOGIN,
								Defs.CERT_REVOCADO);
					}
				} else {
					request.setAttribute(Defs.MENSAJE_LOGIN, Defs.CERT_REVOCADO);
				}
			} else
				request.setAttribute(Defs.MENSAJE_LOGIN,
						Defs.METODO_AUTH_NO_ACEPTADA);

			request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.getMessage());
			return mapping.findForward("failure");
		} catch (GestionUsuariosBackOfficeException e) {
			logger.error(
					"Se ha producdo un error en validacionCertificado.do al obtener el usuario asociado al certificado: ",
					e.fillInStackTrace());
			request.setAttribute(Defs.MENSAJE_LOGIN,
					"mensaje.error.usuario_no_valido_cert");
			request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.getMessage());
			return mapping.findForward(FORWARD_LOGIN);
		}catch (SigemException e) {

			request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.getMessage());
			return mapping.findForward("failure");
		} catch (Exception e) {
			logger.error("Se ha producdo un error en loginInterno.do: ",
					e.fillInStackTrace());
			request.setAttribute(Defs.MENSAJE_LOGIN, "mensaje.error.generico");
			request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.getMessage());
			return mapping.findForward(FORWARD_LOGIN);
		}

	}

	public static X509Certificate getX509Certificate(
			HttpServletRequest request, String serialNumber) {
		String attributeName = "javax.servlet.request.X509Certificate";

		Object obj = request.getAttribute(attributeName);
		X509Certificate certificate = null;
		if (obj instanceof java.security.cert.X509Certificate[]) {
			java.security.cert.X509Certificate[] certArray = (java.security.cert.X509Certificate[]) obj;
			for (int i = 0; i < certArray.length; i++)
				if (certArray[i].getSerialNumber().toString()
						.equals(serialNumber))
					return certArray[i];
			return null;
		}

		if (obj instanceof java.security.cert.X509Certificate) {
			certificate = (X509Certificate) obj;
			if (certificate.getSerialNumber().toString().equals(serialNumber))
				return certificate;
			else
				return null;
		}

		return null;
	}
}
