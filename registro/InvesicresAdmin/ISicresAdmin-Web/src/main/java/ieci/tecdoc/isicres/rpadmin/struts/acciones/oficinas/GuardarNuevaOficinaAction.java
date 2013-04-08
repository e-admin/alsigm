package ieci.tecdoc.isicres.rpadmin.struts.acciones.oficinas;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.business.Constantes;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.business.NodeImplLDAP;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.business.ServiceTree;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.ldap.LdapException;
import ieci.tecdoc.isicres.rpadmin.struts.forms.OficinaForm;

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
import es.ieci.tecdoc.isicres.admin.beans.OficinaBean;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapConnCfg;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapConnection;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapUasFns;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.estructura.beans.Departamento;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminException;
import es.ieci.tecdoc.isicres.admin.sbo.uas.ldap.UasConfigUtil;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;


public class GuardarNuevaOficinaAction extends RPAdminWebAction {

	private static final Logger logger = Logger.getLogger(GuardarNuevaOficinaAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ActionForward af = null;
		OficinaForm oficinaForm = null;
		try {

			ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();
		    //Se obtiene la entidad
			Entidad entidad = new Entidad();
			entidad.setIdentificador(MultiEntityContextHolder.getEntity());

		    oficinaForm = (OficinaForm)form;
		    oficinaForm.toUpperCase(request);

			OficinaBean nuevaOficina = new OficinaBean();
			BeanUtils.copyProperties(nuevaOficina, oficinaForm);

			if( logger.isDebugEnabled() ) {
				logger.debug("La oficina creada es: " + nuevaOficina.toString());
			}

			// Si es grupo LDAP hay que obtener el guid
			if (nuevaOficina.getTipoDepartamento() == Departamento.LDAP_DEPT_TYPE){

				ServiceTree serviceTree = (ServiceTree)request.getSession(false).getAttribute(Constantes.LDAP_SERVICE_GROUP_TREE);

				if (serviceTree!=null){
					boolean enc = serviceTree.searchNode(serviceTree.getTree(), nuevaOficina.getDeptId());

					if (enc){

						NodeImplLDAP nodo = (NodeImplLDAP) serviceTree.fin.getRoot();
						String Dn = nodo.getCodigo(); // Sacar el dn a partir del id del árbol

				        LdapConnection ldapConn = new LdapConnection();
				        LdapConnCfg connCfg = null;

				        connCfg = UasConfigUtil.createLdapConnConfig(entidad.getIdentificador());
						ldapConn.open(connCfg);

						// Obtener su guid
						String groupGuid=LdapUasFns.findGroupGuidByDn(ldapConn, Dn);
						nuevaOficina.setLdapGuid(groupGuid);
						nuevaOficina.setLdapDn(Dn);
					} else {
						throw new ISicresRPAdminException(LdapException.LDAP_NODE_NOT_FOUND);
					}
				} else {
					throw new ISicresRPAdminException(LdapException.LDAP_TREE_CONFIG_NOT_FOUND);
				}
			}

			oServicio.crearOficina(nuevaOficina, entidad);

			ActionMessages messages = new ActionMessages();
			ActionMessage mesage = new ActionMessage("ieci.tecdoc.sgm.rpadmin.oficinas.resultado.guardadoOK");
			messages.add("Result: ", mesage);
			saveMessages(request, messages);
			af = mapping.findForward("success");

		}
		catch ( ISicresRPAdminException e)
		{
			logger.error("Error en la aplicación", e);
			ActionErrors errores = new ActionErrors();
			ActionError error = null;

			if( e.getErrorCode() == ISicresRPAdminException.OFICINA_SICRES_EXISTENTE) {
				error = new ActionError("ieci.tecdoc.sgm.rpadmin.error.mensaje.oficina.ya.referenciada", oficinaForm.getNombreDept());
			} else if( e.getErrorCode() == LdapException.LDAP_NODE_NOT_FOUND) {
				error = new ActionError("ieci.tecdoc.sgm.rpadmin.error.nodo.ldap.no.encontrado");
			} else if( e.getErrorCode() == LdapException.LDAP_TREE_CONFIG_NOT_FOUND) {
				error = new ActionError("ieci.tecdoc.sgm.rpadmin.error.configuracion.arbol.ldap.no.encontrado");
			} else if( e.getErrorCode() == ISicresRPAdminException.CODIGO_OFICINA_SICRES_EXISTENTE) {
				error = new ActionError("ieci.tecdoc.sgm.rpadmin.error.mensaje.codigo.oficina.duplicado");
			}
			else
			{
				error = new ActionError("ieci.tecdoc.sgm.rpadmin.error.mensaje", e.getMessage());
			}
			errores.add("Error interno", error);
			saveErrors(request, errores);
			af = mapping.findForward("error");
		}

		return af;
	}

}
