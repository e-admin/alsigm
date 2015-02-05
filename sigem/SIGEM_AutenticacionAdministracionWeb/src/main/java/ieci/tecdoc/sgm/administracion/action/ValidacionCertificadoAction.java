package ieci.tecdoc.sgm.administracion.action;

import ieci.tecdoc.sgm.administracion.utils.Comprobador;
import ieci.tecdoc.sgm.administracion.utils.Defs;
import ieci.tecdoc.sgm.administracion.utils.Utilidades;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.entidades.ServicioEntidades;
import ieci.tecdoc.sgm.core.services.gestion_administracion.ConstantesGestionUsuariosAdministracion;
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

		boolean aceptado = true;

		try {
			ServicioEntidades servicioEntidades = LocalizadorServicios
					.getServicioEntidades();
			List entidades = servicioEntidades.obtenerEntidades();
			request.setAttribute(Defs.PARAMETRO_ENTIDADES, entidades);

			String key = (String) session
					.getAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_KEY_SESION_USUARIO_ADM);
			if (Utilidades.isNuloOVacio(key)) {
				key = "";
			}

			String idAplicacion = (String) session
					.getAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_APLICACION);
			if (Utilidades.isNuloOVacio(idAplicacion)) {
				idAplicacion = "";
			}

			String idEntidad = request
					.getParameter(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD);
			if (Utilidades.isNuloOVacio(idEntidad)) {
				idEntidad = (String) session
						.getAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD);
			}
			if (Utilidades.isNuloOVacio(idEntidad)) {
				idEntidad = new String("");
			}

			Collection certificados = new ArrayList();
			BigInteger certId = new BigInteger("-1");

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

			X509Certificate certificate;
			String certificadoId = "" + certId;

			// Si no hay certificado volvemos a la pagina de login
			if (certificadoId == null || certificadoId.equals("")) {
				request.setAttribute(Defs.MENSAJE_ERROR,
						Defs.MENSAJE_ERROR_SELECCIONAR_CERTIFICADO);
				return mapping.findForward(FORWARD_LOGIN);
			}

			certificate = getX509Certificate(request, certificadoId);

			// Si no hay certificado volvemos a la pagina de login
			if (certificate == null) {
				request.setAttribute(Defs.MENSAJE_ERROR,
						Defs.MENSAJE_ERROR_SELECCIONAR_CERTIFICADO);
				return mapping.findForward(FORWARD_LOGIN);
			}

			session.setAttribute(Defs.CERTIFICADO_SELECCIONADO,	certificate);
			session.setAttribute(ConstantesGestionUsuariosAdministracion.PARAMETRO_ID_ENTIDAD, idEntidad);

			Entidad oEntidad = new Entidad();
			oEntidad.setIdentificador(idEntidad);

			ServicioGestionUsuariosBackOffice oServicio = LocalizadorServicios
					.getServicioAutenticacionUsuariosBackOffice();

			DatosUsuario user = oServicio.authenticateUser(certificate,
					oEntidad);

			if (user != null) {

				return mapping.findForward(Comprobador
						.comprobarInformacionCertificado(request, key,
								idEntidad, idAplicacion, user, true));
			} else {
				request.setAttribute("invalid_user", "true");
				return mapping.findForward(FORWARD_LOGIN);
			}

		} catch (SesionUsuarioException e) {

			logger.error(
					"Se ha producdo un error en validacionCertificado.do con el certificado de usuario: ",
					e.fillInStackTrace());
			request.setAttribute(Defs.MENSAJE_ERROR,
					"mensaje.error.certificado_no_valido");
			request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.getMessage());
			return mapping.findForward(FORWARD_LOGIN);

		} catch (GestionUsuariosBackOfficeException e) {
			logger.error(
					"Se ha producdo un error en validacionCertificado.do al obtener el usuario asociado al certificado: ",
					e.fillInStackTrace());
			request.setAttribute(Defs.MENSAJE_ERROR,
					"mensaje.error.usuario_no_valido_cert");
			request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.getMessage());
			return mapping.findForward(FORWARD_LOGIN);
		} catch (SigemException e) {
			logger.error("Se ha producdo un error en loginInterno.do: ",
					e.fillInStackTrace());
			request.setAttribute(Defs.MENSAJE_ERROR, "mensaje.error.generico");
			request.setAttribute(Defs.MENSAJE_ERROR_DETALLE, e.getMessage());
			return mapping.findForward(FORWARD_LOGIN);
		} catch (Exception e) {
			logger.error("Se ha producdo un error en loginInterno.do: ",
					e.fillInStackTrace());
			request.setAttribute(Defs.MENSAJE_ERROR, "mensaje.error.validar_usuario");
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
