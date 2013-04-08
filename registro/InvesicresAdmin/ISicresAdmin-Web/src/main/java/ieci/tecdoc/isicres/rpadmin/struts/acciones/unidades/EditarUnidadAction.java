package ieci.tecdoc.isicres.rpadmin.struts.acciones.unidades;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.forms.UnidadForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.OrganizacionBean;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class EditarUnidadAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(EditarUnidadAction.class);
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

		String idUnidad = (String)request.getParameter("idUnidad");

		if(idUnidad == null || idUnidad.equals("")) {
			idUnidad = (String)request.getSession(false).getAttribute("idUnidad");
		}

		if( idUnidad != null && !idUnidad.equals("")) {

			//Mostramos la página de editarUnidad.jsp
			request.setAttribute("idUnidad", idUnidad);
			UnidadForm unidadForm = (UnidadForm)form;

			Entidad entidad = new Entidad();
			entidad.setIdentificador(MultiEntityContextHolder.getEntity());

			OrganizacionBean organizacion = oServicio.obtenerOrganizacion(Integer.parseInt(idUnidad), entidad);
			BeanUtils.copyProperties(unidadForm, organizacion);

			return mapping.findForward("success");
		}
		return mapping.findForward("editUnit");
	}
}
