package ieci.tecdoc.sgm.consulta_telematico.action;

import ieci.tecdoc.sgm.base.xml.core.XmlDocument;
import ieci.tecdoc.sgm.base.xml.core.XmlTransformer;
import ieci.tecdoc.sgm.consulta_telematico.config.ConsultaTelematicoBeanLoader;
import ieci.tecdoc.sgm.consulta_telematico.manager.ObtenerDetalleManager;
import ieci.tecdoc.sgm.consulta_telematico.utils.Defs;
import ieci.tecdoc.sgm.consulta_telematico.utils.Utils;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.idioma.ConstantesIdioma;
import ieci.tecdoc.sgm.core.services.telematico.RegistroDocumentos;
import ieci.tecdoc.sgm.core.services.telematico.RegistroTelematicoException;
import ieci.tecdoc.sgm.core.services.telematico.ServicioRegistroTelematico;

import java.io.File;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Clase que muestra el detalle de un registro.
 *
 */
public class DetalleRegistroAction extends ConsultaRegistroTelematicoWebAction {

	private static final Logger logger = Logger.getLogger(DetalleRegistroAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		// Información de la sesión del usuario
		HttpSession session = request.getSession();
		String idEntidad = (String) session.getAttribute(Defs.ENTIDAD_ID);

    	String numeroRegistro = (String)request.getParameter(Defs.NUMERO_REGISTRO);
    	String asunto = (String)request.getParameter(Defs.ASUNTO);
    	String fechaRegistro = (String)request.getParameter(Defs.FECHA_REGISTRO);
    	String fechaEfectiva = (String)request.getParameter(Defs.FECHA_EFECTIVA);
    	String destino = (String)request.getParameter(Defs.ADDREESSE);
    	String descripcion = (String)request.getParameter(Defs.DESCRIPCION);

	    try {
	    	ServicioRegistroTelematico oServicioRT = LocalizadorServicios.getServicioRegistroTelematico();
	    	Entidad entidad = Utils.obtenerEntidad(idEntidad);

			// Obtener el XML de detalle del registro
	    	ObtenerDetalleManager obtenerDetalleManager = ConsultaTelematicoBeanLoader.getObtenerDetalleManager();
		   	XmlDocument xmlDocReg = obtenerDetalleManager.getDetalleRegistro(numeroRegistro, entidad);
		   	Locale idioma = (Locale)session.getAttribute("org.apache.struts.action.LOCALE");
		   	if (idioma == null || idioma.getLanguage() == null) {
		   		idioma = request.getLocale();
		   	}
		   	String strIdioma = idioma.getLanguage();
		   	if (ConstantesIdioma.ESPANOL.equals(strIdioma)) {
				strIdioma = "";
		   	}

		   	// Configuración externalizada
		   	String aplicacion = (String)session.getServletContext().getAttribute(Defs.PLUGIN_APLICACION_TELEMATICO);
		   	String carpeta = (String)session.getServletContext().getAttribute(Defs.PLUGIN_CARPETA_TELEMATICO);

		   	String ruta = Utils.obtenerRuta(request.getSession().getServletContext().getRealPath(""), asunto, aplicacion, carpeta, idEntidad);
		   	String rutaFinal = "";

		   	if (StringUtils.isNotBlank(strIdioma)) {
		   		rutaFinal = ruta + "formulario_relleno_" + strIdioma + ".xsl";
		   	   	File plantilla = new File(rutaFinal);
		   		if (plantilla == null || !plantilla.exists()) {
		   			rutaFinal =  ruta + "formulario_relleno.xsl";
		   		}
		   	} else {
		   		rutaFinal =  ruta + "formulario_relleno.xsl";
		   	}

		   	// Documentos asociados al registro
		   	RegistroDocumentos registroDocumentos = oServicioRT.obtenerDocumentosRegistro(numeroRegistro, entidad);
		   	request.setAttribute(Defs.DOCUMENTOS, Utils.obtenerListado(registroDocumentos, xmlDocReg.getRootElement()));

		   	String xml_w_xsl = XmlTransformer.transformXmlDocumentToHtmlStringTextUsingXslFile(xmlDocReg, rutaFinal);
		   	request.setAttribute(Defs.INFORMACION_REGISTRO, xml_w_xsl);
		   	request.setAttribute(Defs.NUMERO_REGISTRO, numeroRegistro);
		   	request.setAttribute(Defs.ASUNTO, asunto);
		   	request.setAttribute(Defs.FECHA_REGISTRO, fechaRegistro);
			request.setAttribute(Defs.FECHA_EFECTIVA, fechaEfectiva);
			request.setAttribute(Defs.ADDREESSE, destino);
		   	request.setAttribute(Defs.DESCRIPCION, descripcion);

	    	return mapping.findForward(Defs.SUCCESS_FORWARD);
	    }
	    catch(Exception e) {
	    	logger.error("Se ha producido un error al obtener detalle del registro", e);

	    	if (e instanceof RegistroTelematicoException) {
	    		request.setAttribute(Defs.ERROR, e.getMessage());
	    	}

	    	request.setAttribute(Defs.INFORMACION_REGISTRO, null);
	    	request.setAttribute(Defs.NUMERO_REGISTRO, numeroRegistro);
		   	request.setAttribute(Defs.ASUNTO, asunto);
		   	request.setAttribute(Defs.FECHA_REGISTRO, fechaRegistro);
		   	request.setAttribute(Defs.FECHA_EFECTIVA, fechaEfectiva);
			request.setAttribute(Defs.ADDREESSE, destino);
		   	request.setAttribute(Defs.DESCRIPCION, descripcion);

	    	return mapping.findForward(Defs.FAILURE_FORWARD);
   		}
   	}
}
