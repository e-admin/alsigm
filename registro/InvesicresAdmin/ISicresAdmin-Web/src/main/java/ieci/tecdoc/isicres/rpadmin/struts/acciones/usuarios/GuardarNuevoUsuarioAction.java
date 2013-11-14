package ieci.tecdoc.isicres.rpadmin.struts.acciones.usuarios;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.business.Constantes;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.business.NodeImplLDAP;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.business.ServiceTree;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.ldap.LdapException;
import ieci.tecdoc.isicres.rpadmin.struts.forms.UsuarioForm;
import ieci.tecdoc.isicres.rpadmin.struts.util.Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringEscapeUtils;
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
import es.ieci.tecdoc.isicres.admin.beans.UsuarioRegistradorBean;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapBasicFns;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapConnCfg;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapConnection;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapUasFns;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminException;
import es.ieci.tecdoc.isicres.admin.sbo.uas.ldap.UasConfigUtil;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class GuardarNuevoUsuarioAction extends RPAdminWebAction {

	private static final Logger logger = Logger
			.getLogger(GuardarNuevoUsuarioAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionForward af = null;
		try {
			ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();
			UsuarioForm usuarioForm = (UsuarioForm) form;
			ActionErrors errores = Utils.validarDatosUsuario(usuarioForm);
			if (errores != null) {
				saveErrors(request, errores);
				af = mapping.findForward("error");
			} else {
				usuarioForm.toUpperCase(request);
				UsuarioRegistradorBean nuevoUsuario = new UsuarioRegistradorBean();
				BeanUtils.copyProperties(nuevoUsuario, usuarioForm);

				if (logger.isDebugEnabled()) {
					logger.debug("El usuario creado es: " + nuevoUsuario.toString());
				}

				//Se obtiene la entidad
				Entidad entidad = new Entidad();
				entidad.setIdentificador(MultiEntityContextHolder.getEntity());

				if (isLdapMethod(entidad.getIdentificador())){
					ServiceTree serviceTree = (ServiceTree)request.getSession(false).getAttribute(Constantes.LDAP_SERVICE_USER_TREE);
					if (serviceTree!=null){
						boolean enc = serviceTree.searchNode(serviceTree.getTree(), Integer.parseInt(usuarioForm.getTreeId()));
						if (enc){
							NodeImplLDAP nodo = (NodeImplLDAP) serviceTree.fin.getRoot();
							String Dn = nodo.getCodigo(); // Sacar el dn a partir del id del árbol

					        LdapConnection ldapConn = new LdapConnection();
					        LdapConnCfg connCfg = null;

					        connCfg = UasConfigUtil.createLdapConnConfig(entidad.getIdentificador());
							ldapConn.open(connCfg);

							// Obtener su guid
							String userGuid=LdapUasFns.findUserGuidByDn(ldapConn, Dn);
							nuevoUsuario.setLdapGuid(userGuid);

							String fullName = LdapBasicFns.getEntryRdn(StringEscapeUtils.unescapeJavaScript(Dn));
							int index = fullName.indexOf("=");
							fullName = fullName.substring(index+1, fullName.length());

							nuevoUsuario.setNombre(fullName);
							nuevoUsuario.setLdapFullName(fullName);

							oServicio.crearUsuarioLdap(nuevoUsuario, entidad);
						} else {
							throw new ISicresRPAdminException(LdapException.LDAP_NODE_NOT_FOUND);
						}
					} else {
						throw new ISicresRPAdminException(LdapException.LDAP_TREE_CONFIG_NOT_FOUND);
					}
				} else {
					oServicio.crearUsuario(nuevoUsuario, entidad);
				}

				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionMessage("ieci.tecdoc.sgm.rpadmin.usuarios.resultado.guardadoOK");
				messages.add("Result: ", message);
				saveMessages(request, messages);
				af = mapping.findForward("success");
			}
		} catch (ISicresRPAdminException e) {

			logger.error("Error en la aplicacion", e);
			ActionErrors errores = new ActionErrors();
			ActionError error = null;
			if (e.getErrorCode() == ISicresRPAdminException.OFICINA_INEXISTENTE) {
				error = new ActionError(
						"ieci.tecdoc.sgm.rpadmin.error.mensaje.nuevo.usuario.oficina.inexistente");
			} else if( e.getErrorCode() == LdapException.LDAP_NODE_NOT_FOUND) {
					error = new ActionError("ieci.tecdoc.sgm.rpadmin.error.nodo.ldap.no.encontrado");
			} else if( e.getErrorCode() == LdapException.LDAP_TREE_CONFIG_NOT_FOUND) {
					error = new ActionError("ieci.tecdoc.sgm.rpadmin.error.configuracion.arbol.ldap.no.encontrado");
			} else {
				error = new ActionError("ieci.tecdoc.sgm.rpadmin.error.mensaje", e.getMessage());
			}
			errores.add("Error interno", error);
			saveErrors(request, errores);
			af = mapping.findForward("error");
		}

		return af;
	}
}
