package ieci.tecdoc.sgm.admsistema.action;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import ieci.tecdoc.sgm.admsistema.form.ConfiguracionXMLForm;
import ieci.tecdoc.sgm.admsistema.proceso.configuracion.Editar;
import ieci.tecdoc.sgm.admsistema.util.Defs;
import ieci.tecdoc.sgm.admsistema.util.Utilidades;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.administracion.ServicioAdministracion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;

public class ConfigurarServidorAction extends AdministracionWebAction {
	private static final Logger logger = Logger.getLogger(ConfigurarServidorAction.class);

	public static final String FORWARD_CONFIGURAR = "configurar";
	public static final String FORWARD_MOSTRAR = "mostrar";
	public static final String FORWARD_ERROR = "failure";

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String usuario = (String)request.getSession().getAttribute(Defs.PARAMETRO_NOMBRE_USUARIO_TRABAJO);

		try {
			ConfiguracionXMLForm configuracionXMLForm = (ConfiguracionXMLForm)form;

			if (configuracionXMLForm == null || Utilidades.esNuloOVacio(configuracionXMLForm.getIdEntidad())) {
				if (form == null) form = new ConfiguracionXMLForm();

				((ConfiguracionXMLForm)form).setIdEntidad("");
				((ConfiguracionXMLForm)form).setDireccionBaseDatos("");
				((ConfiguracionXMLForm)form).setPuertoBaseDatos("");
				((ConfiguracionXMLForm)form).setUsuarioBaseDatos("");
				((ConfiguracionXMLForm)form).setPasswordBaseDatos("");
				((ConfiguracionXMLForm)form).setPasswordRepetidoBaseDatos("");
				((ConfiguracionXMLForm)form).setUsuarioBaseDatosTE("");
				((ConfiguracionXMLForm)form).setPasswordBaseDatosTE("");
				((ConfiguracionXMLForm)form).setUsuarioBaseDatosRP("");
				((ConfiguracionXMLForm)form).setPasswordBaseDatosRP("");
				((ConfiguracionXMLForm)form).setUsuarioBaseDatosAD("");
				((ConfiguracionXMLForm)form).setPasswordBaseDatosAD("");
				((ConfiguracionXMLForm)form).setUsuarioBaseDatosGE("");
				((ConfiguracionXMLForm)form).setPasswordBaseDatosGE("");
				((ConfiguracionXMLForm)form).setUsuarioBaseDatosAudit("");
				((ConfiguracionXMLForm)form).setPasswordBaseDatosAudit("");
				((ConfiguracionXMLForm)form).setUsuarioBaseDatosSIR("");
				((ConfiguracionXMLForm)form).setPasswordBaseDatosSIR("");
				((ConfiguracionXMLForm)form).setInstancia("");
				((ConfiguracionXMLForm)form).setTipoServidor("");
				((ConfiguracionXMLForm)form).setFicheroServidor(null);

				ServicioAdministracion oServicio = LocalizadorServicios.getServicioAdministracion();
				List entidades = Utilidades.entidadesAdministrar(usuario, oServicio.getEntidades(usuario));
				request.setAttribute(Defs.LISTADO_ENTIDADES, entidades);

				return mapping.findForward(FORWARD_CONFIGURAR);

			} else {
				if (form != null) {

					MultipartRequestHandler files = form.getMultipartRequestHandler();
				 	Hashtable ht = files.getFileElements();
			    	int numFicheros = ht.size();
			    	FormFile[] ficheros = new FormFile[numFicheros];
			    	int cont = 0;
			    	for (Enumeration enumer = ht.keys() ; enumer.hasMoreElements() ;) {
			    		String key = (String)enumer.nextElement();
			    		FormFile fichero = (FormFile)ht.get(key);
			    		if (fichero.getFileSize() > 0){
			    			ficheros[cont] = fichero;
			    		}
			        }

			    	String[] parametros = new String[] {
			    									((ConfiguracionXMLForm)form).getIdEntidad(),
			    									((ConfiguracionXMLForm)form).getDireccionBaseDatos(),
			    									((ConfiguracionXMLForm)form).getPuertoBaseDatos(),
			    									((ConfiguracionXMLForm)form).getUsuarioBaseDatos(),
			    									((ConfiguracionXMLForm)form).getPasswordBaseDatos(),
			    									((ConfiguracionXMLForm)form).getInstancia(),
			    									((ConfiguracionXMLForm)form).getTipoBase(),
			    									((ConfiguracionXMLForm)form).getUsuarioBaseDatosAD(),
			    									((ConfiguracionXMLForm)form).getPasswordBaseDatosAD(),
			    									((ConfiguracionXMLForm)form).getUsuarioBaseDatosGE(),
			    									((ConfiguracionXMLForm)form).getPasswordBaseDatosGE(),
			    									((ConfiguracionXMLForm)form).getUsuarioBaseDatosRP(),
			    									((ConfiguracionXMLForm)form).getPasswordBaseDatosRP(),
			    									((ConfiguracionXMLForm)form).getUsuarioBaseDatosTE(),
			    									((ConfiguracionXMLForm)form).getPasswordBaseDatosTE(),
			    									((ConfiguracionXMLForm)form).getUsuarioBaseDatosAudit(),
			    									((ConfiguracionXMLForm)form).getPasswordBaseDatosAudit(),
			    									((ConfiguracionXMLForm)form).getUsuarioBaseDatosSIR(),
			    									((ConfiguracionXMLForm)form).getPasswordBaseDatosSIR()
			    							};

			    	String serverXml = Editar.editar(parametros, ficheros[0].getFileData(), ((ConfiguracionXMLForm)form).getTipoServidor());

			    	if (Utilidades.esNuloOVacio(serverXml)) {
			    		ServicioAdministracion oServicio = LocalizadorServicios.getServicioAdministracion();
						List entidades = Utilidades.entidadesAdministrar(usuario, oServicio.getEntidades(usuario));
						request.setAttribute(Defs.LISTADO_ENTIDADES, entidades);

			    		request.setAttribute(Defs.MENSAJE_ERROR, "mensaje.error.proceso.configurar.incorrecto");
			    		return mapping.findForward(FORWARD_ERROR);
			    	} else {
			    		request.getSession().setAttribute(Defs.PARAMETRO_FICHERO_SERVIDOR_NUEVO, serverXml);
			    		if (Defs.SERVIDOR_TOMCAT_5.equals(((ConfiguracionXMLForm)form).getTipoServidor())
			    				|| Defs.SERVIDOR_TOMCAT_6.equals(((ConfiguracionXMLForm)form).getTipoServidor())
			    				|| Defs.SERVIDOR_TOMCAT_7.equals(((ConfiguracionXMLForm)form).getTipoServidor()))
			    			request.getSession().setAttribute(Defs.PARAMETRO_FICHERO_SERVIDOR_NOMBRE, "server.xml");
			    		else request.getSession().setAttribute(Defs.PARAMETRO_FICHERO_SERVIDOR_NOMBRE, "server-ds.xml");
			    		return mapping.findForward(FORWARD_MOSTRAR);
			    	}
				}
			}
			ServicioAdministracion oServicio = LocalizadorServicios.getServicioAdministracion();
			List entidades = Utilidades.entidadesAdministrar(usuario, oServicio.getEntidades(usuario));
			request.setAttribute(Defs.LISTADO_ENTIDADES, entidades);
			request.setAttribute(Defs.MENSAJE_ERROR, "mensaje.error.proceso.configurar.error_inesperado");
			return mapping.findForward(FORWARD_ERROR);
		} catch(Exception e) {
			logger.error("Se ha producido un error inesperado", e);
			try {
				ServicioAdministracion oServicio = LocalizadorServicios.getServicioAdministracion();
				List entidades = Utilidades.entidadesAdministrar(usuario, oServicio.getEntidades(usuario));
				request.setAttribute(Defs.LISTADO_ENTIDADES, entidades);
			} catch(Exception ex) {}
			request.setAttribute(Defs.MENSAJE_ERROR, "mensaje.error.proceso.configurar.error_inesperado");
			return mapping.findForward(FORWARD_ERROR);
		}
	}
}
