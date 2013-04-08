package ieci.tecdoc.sgm.certificacion.action;

import ieci.tecdoc.sgm.autenticacion.util.TipoSolicitante;
import ieci.tecdoc.sgm.certificacion.utilsweb.Defs;
import ieci.tecdoc.sgm.core.exception.SigemException;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.certificacion.Certificacion;
import ieci.tecdoc.sgm.core.services.certificacion.ServicioCertificacion;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.repositorio.ContenedorDocumento;
import ieci.tecdoc.sgm.core.services.repositorio.ServicioRepositorioDocumentosTramitacion;
import ieci.tecdoc.sgm.core.services.sesion.InfoUsuario;
import ieci.tecdoc.sgm.core.services.sesion.ServicioSesionUsuario;
import ieci.tecdoc.sgm.core.services.sesion.SesionUsuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

public class ObtenerCertificacionAction extends Action {
	private static final Logger logger = Logger.getLogger(ObtenerCertificacionAction.class);
	
	private static final String FAILURE_FORWARD = "failure";
	private static final String SUCCESS_FORWARD = "success";
	private static final String LOGIN_FORWARD = "login";
	private static final String CAMPO_USUARIO = "idUsuario";
	private static final String CAMPO_FICHERO = "idFichero";
	private static final String DATOS_FICHERO = "datosFichero";
	private static final String  ERROR = "error";
	
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
      		throws Exception {
    	
    	DynaValidatorForm oForm = (DynaValidatorForm)form;
    	HttpSession session = request.getSession();
    	
    	String idEntidad = (String)session.getAttribute(Defs.ENTIDAD_ID);
    	if (idEntidad == null || "".equals(idEntidad)) {
    		return mapping.findForward(LOGIN_FORWARD);
    	}
    	
    	String idSesion = (String)session.getAttribute(Defs.SESION_ID);
    	if (idSesion == null || "".equals(idSesion)) {
    		return mapping.findForward(LOGIN_FORWARD);
    	}
    	
    	ServicioSesionUsuario oServicioAuth = LocalizadorServicios.getServicioSesionUsuario();
    	Entidad entidad = new Entidad();
    	entidad.setIdentificador(idEntidad);
    	SesionUsuario oSesion =  oServicioAuth.obtenerSesion(idSesion, entidad);
    	if (oSesion == null || oSesion.getSessionId() == null) {
    		return mapping.findForward(LOGIN_FORWARD);
    	}
    	
    	String cnif = null;
    	InfoUsuario solicitante = oSesion.getSender();
		
		// Persona fisica o juridica
	    if (solicitante.getInQuality().equals(String.valueOf(TipoSolicitante.INDIVIDUAL))) {
	    	 
	    	// Persona fisica (individual)
	    	cnif = solicitante.getId();
			session.setAttribute(Defs.NOMBRE_USUARIO, solicitante.getName());
	    }
	    else {
	    	// Persona juridica (representante legal)
	    	cnif = solicitante.getCIF();
	    	String nombreUsuario = solicitante.getSocialName();
	    	if ((solicitante.getName() != null) && (!solicitante.getName().trim().equals(""))) {
	    		 nombreUsuario += " (" + solicitante.getName() + ")";
	    	}
			session.setAttribute(Defs.NOMBRE_USUARIO, nombreUsuario);
	    }
    	
    	session.setAttribute(Defs.CNIF_USUARIO, cnif);
    	
    	ServicioRepositorioDocumentosTramitacion oServicioRde = LocalizadorServicios.getServicioRepositorioDocumentosTramitacion();
    	ServicioCertificacion oServicioCertificacion = LocalizadorServicios.getServicioCertificacion();
    	
		try {
			Certificacion oCertificacionFormulario = obtenerDatosCertificacion(oForm);
			if (oCertificacionFormulario.getIdFichero() != null && !"".equals(oCertificacionFormulario.getIdFichero())) {
				Certificacion oCertificacion = oServicioCertificacion.obtenerCertificacion(
					cnif.toUpperCase(), 
					oCertificacionFormulario.getIdFichero().toUpperCase(),
					entidad);
				if (oCertificacion != null) {
					ContenedorDocumento oContenedor = oServicioRde.retrieveDocumentInfo("", oCertificacion.getIdFichero(), entidad);
					session.setAttribute(DATOS_FICHERO, oContenedor.getContent());
					session.setAttribute(CAMPO_USUARIO, oCertificacionFormulario.getIdUsuario());
					session.setAttribute(CAMPO_FICHERO, oCertificacionFormulario.getIdFichero());
				} else {
					request.setAttribute(ERROR, "mensaje.error.no_recuperar");
					return mapping.findForward(FAILURE_FORWARD);		
				}
			} else {
				return mapping.findForward(FAILURE_FORWARD);		
			}
		} catch (Exception e) {
			logger.error("Se ha producido un error al obtener la certificacion", e.fillInStackTrace());
			request.setAttribute(ERROR, "mensaje.error.no_existe");
			return mapping.findForward(FAILURE_FORWARD);		
		}
		return mapping.findForward(SUCCESS_FORWARD);
	}
    
    private static Certificacion obtenerDatosCertificacion(DynaValidatorForm poForm) throws SigemException{
    	Certificacion oCertificacion = new Certificacion();
    	oCertificacion.setIdUsuario((String)poForm.get(CAMPO_USUARIO));
    	oCertificacion.setIdFichero((String)poForm.get(CAMPO_FICHERO));
    	return oCertificacion;
	}
 
}
