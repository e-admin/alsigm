package ieci.tecdoc.isicres.rpadmin.struts.acciones.unidades;


import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.Organizaciones;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Departamento;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class ListadoUnidadHijosAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(ListadoUnidadHijosAction.class);
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {


		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();
		String idUnidad = null;
		String accion = (String)request.getAttribute("accion");
		String idTipo = request.getParameter("idTipo");
		String nodoRaiz = request.getParameter("nodoRaiz");
		if(nodoRaiz==null)
			nodoRaiz = (String)request.getAttribute("nodoRaiz");

		// Tipo departamento
		//Se obtiene la entidad
		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		if (isLdapMethod(entidad.getIdentificador()))
			request.setAttribute("tipoDepartamento", String.valueOf(Departamento.LDAP_DEPT_TYPE));
		else
			request.setAttribute("tipoDepartamento", String.valueOf(Departamento.SICRES_DEPT_TYPE));

		if(nodoRaiz == null || !new Boolean(nodoRaiz).booleanValue()) {

			if(accion == null || accion.equals("")) {
				idUnidad = request.getParameter("idUnidad");
			}

			if( idUnidad == null || idUnidad.equals("")) {
				idUnidad = (String)request.getSession(false).getAttribute("idUnidad");
			}

			if( idTipo == null || idTipo.equals("")) {
				idTipo = (String)request.getSession(false).getAttribute("idTipo");
			}

			Organizaciones organizaciones = oServicio.obtenerHijosOrganizacion(Integer.parseInt(idUnidad), entidad);
			request.getSession(false).setAttribute("idUnidad", idUnidad);
			request.getSession(false).setAttribute("idTipo", idTipo);
			request.setAttribute("idTipo", idTipo);
			request.setAttribute("idPadre", idUnidad);
			request.setAttribute("organizaciones", organizaciones);

			return mapping.findForward("showChildren");
		}
		else
		{
			request.setAttribute("idTipo", "");
			request.setAttribute("idPadre", "");
			Organizaciones organizaciones = oServicio.obtenerHijosOrganizacion(0, entidad);
			request.setAttribute("organizaciones", organizaciones);
			return mapping.findForward("showRoot");
		}
	}

}
