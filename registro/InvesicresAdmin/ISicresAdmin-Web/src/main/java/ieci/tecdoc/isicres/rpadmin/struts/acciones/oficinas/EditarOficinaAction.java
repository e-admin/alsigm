package ieci.tecdoc.isicres.rpadmin.struts.acciones.oficinas;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.business.ServiceTreeLdap;
import ieci.tecdoc.isicres.rpadmin.struts.forms.OficinaForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.OficinaBean;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresAdminEstructuraAdapter;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Departamento;
import es.ieci.tecdoc.isicres.admin.estructura.beans.GrupoLdap;
import es.ieci.tecdoc.isicres.admin.service.ISicresAdminEstructuraService;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class EditarOficinaAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(EditarOficinaAction.class);
	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

		String id = (String)request.getParameter("idOficina");
		if(id == null || id.equals("")) {
			id = (String)request.getSession(false).getAttribute("id");
		}

		if( id != null && !id.equals("")) {

			//Mostramos la página de editarOficina.jsp
			//Se obtiene la entidad
			Entidad entidad = new Entidad();
			entidad.setIdentificador(MultiEntityContextHolder.getEntity());

			request.setAttribute("tipoOficina", oServicio.obtenerTipoOficinasCombo(entidad));
			request.setAttribute("entidadRegistral", oServicio.obtenerEntidadesRegistralesCombo(entidad));
			request.setAttribute("departamento", oServicio.obtenerDepartamentosCombo(entidad));
			request.setAttribute("id", id);
			request.getSession(false).setAttribute("id", id);
			OficinaForm oficinaForm = (OficinaForm)form;

			OficinaBean oficina = oServicio.obtenerOficina(Integer.parseInt(id), entidad);
			request.setAttribute("nombreOficina", oficina.getNombre());
			request.setAttribute("deptId", String.valueOf(oficina.getDeptId()));

			//Si no tenemos oficina en el formulario es la primera vez que se carga, rellenar con datos de negocio
			if(oficinaForm.getId()==null) {
				BeanUtils.copyProperties(oficinaForm, oficina);
			}

			if (isLdapMethod(entidad.getIdentificador())){
				ISicresAdminEstructuraService servicioEstructuraOrganizativa = new ISicresAdminEstructuraAdapter();
				Departamento dept = servicioEstructuraOrganizativa.getDepartamentoLite(oficina.getDeptId(), entidad.getIdentificador());
				if (dept!=null){
					// Obtener el grupo ldap
					GrupoLdap grupoLdap = servicioEstructuraOrganizativa.getGrupoLdapById(dept.get_creatorId(), entidad.getIdentificador());
					if ((oficina!=null)&&(grupoLdap!=null)){
						oficinaForm.setLdapDescription(ServiceTreeLdap.getLdapNodeTitle(grupoLdap.get_fullname()));
					}
				}

				oficinaForm.setTipoDepartamento(String.valueOf(Departamento.LDAP_DEPT_TYPE));
			} else {
				oficinaForm.setTipoDepartamento(String.valueOf(Departamento.SICRES_DEPT_TYPE));
			}
		}
		return mapping.findForward("success");
	}

}
