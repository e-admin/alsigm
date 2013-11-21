package ieci.tecdoc.isicres.rpadmin.struts.acciones.unidades;


import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.forms.UnidadForm;
import ieci.tecdoc.isicres.rpadmin.struts.util.Utils;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.OrganizacionBean;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class GuardarEdicionUnidadAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(GuardarEdicionUnidadAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		    ActionForward af = null;

		    try {
		    	ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

				// Guardamos los datos de la Unidad desde la opción de guardar de la
			    // pagina de Edición si el idUnidad viene relleno
				String idUnidad = (String)request.getParameter("idUnidad");

				if( idUnidad != null && !idUnidad.equals("")) {

					UnidadForm unidadForm = (UnidadForm)form;
					unidadForm.toUpperCase(request);

					OrganizacionBean unidad = new OrganizacionBean();

					Calendar fechaForm = Calendar.getInstance();
					Calendar fechaAlta = Calendar.getInstance();
					Calendar fechaBaja = Calendar.getInstance();
					fechaForm.setTime(new Date());
					BeanUtils.copyProperties(unidad, unidadForm);
					fechaAlta.setTime(unidad.getFechaAlta());
					if(fechaAlta.getTime().after(fechaForm.getTime())){
						ActionErrors errores = new ActionErrors();
						ActionError error = new ActionError("ieci.tecdoc.sgm.rpadmin.oficinas.error.fechaAlta");
						errores.add("Error interno", error);
						saveErrors(request, errores);
						return mapping.findForward("error");
					}
					if(unidad.getFechaBaja() != null){
						fechaBaja.setTime(unidad.getFechaBaja());
						if(fechaAlta.getTime().after(fechaBaja.getTime())){
							ActionErrors errores = new ActionErrors();
							ActionError error = new ActionError("ieci.tecdoc.sgm.rpadmin.oficinas.error.fechaBaja");
							errores.add("Error interno", error);
							saveErrors(request, errores);
							return mapping.findForward("error");
						}
						//Si tiene fecha de baja se pone el campo enabled=0 sino enabled=1
						unidad.setEnabled(false);
					}else
						unidad.setEnabled(true);

					unidad.setId(Integer.parseInt( idUnidad ));

					//Se obtiene la entidad
					Entidad entidad = new Entidad();
					entidad.setIdentificador(MultiEntityContextHolder.getEntity());

					//validamos los datos del IR
					Utils.validateDatosIR(unidad);

					oServicio.editarOrganizacion(unidad, entidad);

					request.setAttribute("accion", "LISTADO");

					ActionMessages messages = new ActionMessages();
					ActionMessage mesage = new ActionMessage("ieci.tecdoc.sgm.rpadmin.unidades.resultado.editadoOK");
					messages.add("Result: ", mesage);
					saveMessages(request, messages);
					af = mapping.findForward("success");
				}
			}
		    catch ( Exception e )
		    {

				logger.error("Error en la aplicación", e);
				ActionErrors errores = new ActionErrors();
				ActionError error = new ActionError("ieci.tecdoc.sgm.rpadmin.error.mensaje", e.getMessage());
				errores.add("Error interno", error);
				saveErrors(request, errores);
				af = mapping.findForward("error");
			}

			return af;
	}
}
