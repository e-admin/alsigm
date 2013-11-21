package ieci.tecdoc.isicres.rpadmin.struts.acciones.dco;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.ieci.tecdoc.fwktd.dir3.core.type.CriterioOficinaEnum;
import es.ieci.tecdoc.fwktd.dir3.core.vo.Criterios;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class ConfiguracionDCOAction extends RPAdminWebAction {

	private static final String INICIALIZADO_DCO = "inicializadoDCO";
	private static final Logger logger = Logger.getLogger(ConfiguracionDCOAction.class);

	@Override
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		boolean superUser = (Boolean) request.getSession().getAttribute(
				"isSuperuser");

		if(superUser){
			try{
				//Ejecutamos una consulta sobre el DIR3 para verificar si esta inicializado
				ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();
				Criterios<CriterioOficinaEnum> criterios= new Criterios<CriterioOficinaEnum>();
				List listEntidadRegistral = oServicio.findOficinasDirectorioComun(criterios);

				//Comprobamos si se nos devuelve algún dato
				if((listEntidadRegistral!=null) && (!listEntidadRegistral.isEmpty())){
					//se considera que el DCO ha sido inicializado
					request.setAttribute(INICIALIZADO_DCO, true);
				}else{
					//el DCO no ha sido inicializado
					request.setAttribute(INICIALIZADO_DCO, false);
				}

			}catch (Exception e){
				//Si se produce una excepcion consideramos que no esta inicializado el DCO
				if(logger.isDebugEnabled()){
					logger.debug("El sistema del DCO no se encuentra inicializado", e);
				}

				request.setAttribute(INICIALIZADO_DCO, false);
			}
		}else{
			logger.error("Error al intentar inicializar/actualizar DCO el usuario no tiene permisos");
			//El usuario no puede continuar con la operativa
			ActionErrors errores = new ActionErrors();
			ActionError error = new ActionError(
					"ieci.tecdoc.rpadmin.dco.error.user.not.permissions");
			errores.add("Error interno", error);
			saveErrors(request, errores);
			return mapping.findForward("error");
		}

		return mapping.findForward("success");
	}

}
