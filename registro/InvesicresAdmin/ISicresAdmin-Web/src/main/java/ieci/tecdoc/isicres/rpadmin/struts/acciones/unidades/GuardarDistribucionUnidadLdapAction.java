package ieci.tecdoc.isicres.rpadmin.struts.acciones.unidades;

import ieci.tecdoc.isicres.rpadmin.struts.acciones.RPAdminWebAction;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.business.Constantes;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.business.NodeImplLDAP;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.business.ServiceTree;
import ieci.tecdoc.isicres.rpadmin.struts.acciones.ldap.LdapException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import es.ieci.tecdoc.isicres.admin.core.beans.SicresOficinaImpl;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapConnCfg;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapConnection;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapConstants;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapUasFns;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresAdminEstructuraAdapter;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.estructura.beans.GrupoLdap;
import es.ieci.tecdoc.isicres.admin.estructura.beans.UsuarioLdap;
import es.ieci.tecdoc.isicres.admin.exception.ISicresRPAdminException;
import es.ieci.tecdoc.isicres.admin.rpadmin.manager.ISicresRPAdminOficinaManager;
import es.ieci.tecdoc.isicres.admin.rpadmin.manager.ISicresRPAdminUserManager;
import es.ieci.tecdoc.isicres.admin.sbo.uas.ldap.UasConfigUtil;
import es.ieci.tecdoc.isicres.admin.service.ISicresAdminEstructuraService;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class GuardarDistribucionUnidadLdapAction extends RPAdminWebAction {

	private static final Logger logger = Logger
			.getLogger(GuardarDistribucionUnidadLdapAction.class);

	public ActionForward executeAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionForward af = null;

		try {

			ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

			//Se obtiene la entidad
			Entidad entidad = new Entidad();
			entidad.setIdentificador(MultiEntityContextHolder.getEntity());

			String idOrg = (String) request.getSession(false).getAttribute(
					"idDistribucion");
			String idString = (String) request.getParameter("id");

			String idTipo = null;
			int idDest = 0;
			ServiceTree serviceTree = (ServiceTree) request.getSession(false)
					.getAttribute(Constantes.LDAP_SERVICE_GROUP_TREE);

			boolean isValid = false;
			String errorKey = "ieci.tecdoc.sgm.rpadmin.error.mensaje";

			if (serviceTree != null) {
				int id = Integer.parseInt(idString);
				boolean enc = serviceTree.searchNode(serviceTree.getTree(), id);

				if (enc) {

					NodeImplLDAP nodo = (NodeImplLDAP) serviceTree.fin
							.getRoot();
					String dn = nodo.getCodigo(); // Sacar el dn a partir del
					// id del árbol

					LdapConnection ldapConn = new LdapConnection();
					LdapConnCfg connCfg = null;

					connCfg = UasConfigUtil.createLdapConnConfig(entidad
							.getIdentificador());
					ldapConn.open(connCfg);

					if (nodo.getTipo() == LdapConstants.PERSON) {
						idTipo = "1";
					} else if (nodo.getTipo() == LdapConstants.DEPARTMENT) {
						idTipo = "2";
					} else if (nodo.getTipo() == LdapConstants.GROUP) {
						idTipo = "3";
					} else if (nodo.getTipo() == LdapConstants.ORGANIZATIONAL_UNIT) {
						idTipo = "4";
					} else if (nodo.getTipo() == LdapConstants.OTHER) {
						idTipo = "0";
					}

					if (nodo.getTipo() == LdapConstants.PERSON) {
						String userGuid = LdapUasFns.findUserGuidByDn(ldapConn,
								dn);

						// Comprobar que existe el usuario
						try {
							UsuarioLdap user = ISicresRPAdminUserManager
									.getUserLdapByGuid(userGuid, entidad
											.getIdentificador());
							if (user != null) {
								idDest = user.get_id();
								isValid = true;
							} else {
								isValid = false;
								errorKey = "ieci.tecdoc.sgm.rpadmin.error.mensaje.select.user.invalid";
							}
						} catch (Exception e) {
							// Si hay excepción es porque no existia el usuario
							isValid = false;
							errorKey = "ieci.tecdoc.sgm.rpadmin.error.mensaje.select.user.invalid";
						}
					} else {
						String groupGuid = LdapUasFns.findGroupGuidByDn(
								ldapConn, dn);

						SicresOficinaImpl oficinaDAO = null;
						try {
							oficinaDAO = ISicresRPAdminOficinaManager
									.getOficinaByLdapGroup(groupGuid, entidad
											.getIdentificador());
						} catch (Exception e) {
							// Si hay excepción es porque no existia la oficina.
							if (logger.isDebugEnabled()) {
								logger.debug(
										"No existe la oficina para el grupo con guid ["
												+ groupGuid
												+ "] en la entidad ["
												+ entidad.getIdentificador()
												+ "]", e);
							}
						}
						if (oficinaDAO != null) {
							idDest = oficinaDAO.getDeptId();
							isValid = true;
						} else {
							//validamos si existe el grupo LDAP
							ISicresAdminEstructuraService oServicioEstructura = new ISicresAdminEstructuraAdapter();

							GrupoLdap grupoLdap = null;
							try{
								grupoLdap = oServicioEstructura.getGrupoLdap(groupGuid, entidad
									.getIdentificador());
							}catch (Exception e){
								// Si hay excepción es porque no existia el grupo en invesdoc.
								if (logger.isDebugEnabled()) {
									logger.debug(
											"No existen datos para el grupo con guid ["
													+ groupGuid
													+ "] en la entidad ["
													+ entidad.getIdentificador()
													+ "]", e);
								}
							}
							if(grupoLdap!= null){
								idTipo = "3";
								idDest = grupoLdap.get_id();
								isValid = true;
							}else{
								isValid = false;
								errorKey = "ieci.tecdoc.sgm.rpadmin.error.mensaje.select.group.invalid";
							}
						}
					}
				} else {
					throw new ISicresRPAdminException(
							LdapException.LDAP_NODE_NOT_FOUND);
				}
			} else {
				throw new ISicresRPAdminException(
						LdapException.LDAP_TREE_CONFIG_NOT_FOUND);
			}

			if (isValid) {

				int[] ids = new int[1];
				ids[0] = idDest;

				oServicio.crearDistribuciones(Integer.parseInt(idOrg), Integer
						.parseInt(idTipo), ids, entidad);

				ActionMessages messages = new ActionMessages();
				ActionMessage mesage = new ActionMessage(
						"ieci.tecdoc.sgm.rpadmin.unidades.distribucion.usuarios.guardadoOK");
				messages.add("Result: ", mesage);
				request.setAttribute("messages", messages);
			} else {
				logger
						.info("Error en la aplicación. No se puede asociar el destino a la lista de distribucion");

				ActionErrors errores = new ActionErrors();
				ActionError error = new ActionError(errorKey);
				errores.add("Error interno", error);
				request.setAttribute("errors", errores);
			}

			af = mapping.findForward("success");

		} catch (Exception e) {

			logger.error("Error en la aplicación", e);
			ActionErrors errores = new ActionErrors();
			ActionError error = new ActionError(
					"ieci.tecdoc.sgm.rpadmin.error.mensaje", e.getMessage());
			errores.add("Error interno", error);
			request.setAttribute("errors", errores);

			af = mapping.findForward("success");
		}

		return af;
	}

}
