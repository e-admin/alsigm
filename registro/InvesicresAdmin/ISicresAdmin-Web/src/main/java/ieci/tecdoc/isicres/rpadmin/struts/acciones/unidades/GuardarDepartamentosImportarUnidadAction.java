package ieci.tecdoc.isicres.rpadmin.struts.acciones.unidades;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.business.Constantes;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.business.NodeImplLDAP;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.business.ServiceTree;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapConstants;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminException;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class GuardarDepartamentosImportarUnidadAction extends RPAdminWebAction {
	private static final Logger logger = Logger.getLogger(GuardarDepartamentosImportarUnidadAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

		//Se obtiene la entidad
		Entidad entidad = new Entidad();
		entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		String id = request.getParameter("idSelect");
		String isNodoSelected = request.getParameter("nodoSelected");
		String idUnidadSeleccionada = (String)request.getSession(false).getAttribute("idUnidad");
		try {
			// Tipo departamento
			if (isLdapMethod(entidad.getIdentificador())){
				String nodeDn = null;
				ServiceTree serviceTree = (ServiceTree)request.getSession(false).getAttribute(Constantes.LDAP_SERVICE_GROUP_TREE);

				if (serviceTree != null){
					boolean enc = serviceTree.searchNode(serviceTree.getTree(), Integer.parseInt(id));
					if (enc){
						NodeImplLDAP nodo = (NodeImplLDAP) serviceTree.fin.getRoot();
						nodeDn = nodo.getCodigo(); // Sacar el dn a partir del id del árbol
					}else{
						// TODO OMAR Cambiar esta excepción
						throw new ISicresRPAdminException("No se ha encontrado el nodo en el árbol");
					}
				} else {
					// TODO OMAR Cambiar esta excepción
					throw new ISicresRPAdminException("No se ha encontrado el árbol en sesión");
				}
				int maxChildrenLdap = ((Integer)request.getSession().getServletContext().getAttribute(Constantes.MAX_CHILDREN_LDAP) ).intValue();
				oServicio.importarGruposLdap(nodeDn, maxChildrenLdap, LdapConstants.LDAP_TYPE_TREE_GROUP, Boolean.valueOf(isNodoSelected).booleanValue(), idUnidadSeleccionada, entidad);
			}else
				oServicio.importarDepartamentos(Integer.parseInt(id), Boolean.valueOf(isNodoSelected).booleanValue(), idUnidadSeleccionada, entidad);
		} catch(ISicresRPAdminException e){
			logger.error("Error en la aplicación", e);
			ActionErrors errores = new ActionErrors();
			ActionError error = new ActionError("ieci.tecdoc.sgm.rpadmin.error.mensaje", e.getMessage());
			errores.add("Error interno", error);
			saveErrors(request, errores);
			return mapping.findForward("error");
		}
        return mapping.findForward("success");
	}
}