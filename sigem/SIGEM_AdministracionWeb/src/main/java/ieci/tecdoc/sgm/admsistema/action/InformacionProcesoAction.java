package ieci.tecdoc.sgm.admsistema.action;

import java.io.File;

import ieci.tecdoc.sgm.admsistema.proceso.Info;
import ieci.tecdoc.sgm.admsistema.proceso.Informacion;
import ieci.tecdoc.sgm.admsistema.util.Defs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InformacionProcesoAction extends AdministracionWebAction {
	private static final Logger logger = Logger.getLogger(InformacionProcesoAction.class);
	
	public static final String FORWARD_INFORMACION = "informacion";
	public static final String FORWARD_NO_INFORMACION = "no_informacion";
	
	public ActionForward executeAction(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {

			try {
				String idProceso = (String)request.getParameter(Defs.PARAMETRO_ID_PROCESO);
				String tipoProceso = (String)request.getParameter(Defs.PARAMETRO_TIPO_PROCESO);
				
				String directorio = "";
				if(Defs.EXPORTAR.equals(tipoProceso))
					directorio = System.getProperties().getProperty("user.home") + File.separator + Defs.EXPORTAR;
				else if(Defs.IMPORTAR.equals(tipoProceso))
					directorio = System.getProperties().getProperty("user.home") + File.separator + Defs.IMPORTAR;
				else if(Defs.ACCION_MULTIENTIDAD.equals(tipoProceso))
					directorio = System.getProperties().getProperty("user.home") + File.separator + Defs.ACCION_MULTIENTIDAD;				
				else return mapping.findForward(FORWARD_NO_INFORMACION);
				
				directorio = directorio + File.separator + idProceso;
				
				Informacion informacion = new Informacion();
				Info info = informacion.obtenerInformacion(directorio);
				
				request.setAttribute(Defs.PARAMETRO_INFORMACION_PROCESO, info);
				
				return mapping.findForward(FORWARD_INFORMACION);
			} catch(Exception e) {
				logger.error("Se ha producido un error inesperado", e);
				return mapping.findForward(FORWARD_NO_INFORMACION);
			}
	}

}
