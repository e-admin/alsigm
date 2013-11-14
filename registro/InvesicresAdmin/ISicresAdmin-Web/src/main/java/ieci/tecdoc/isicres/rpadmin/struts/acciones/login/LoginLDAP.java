package ieci.tecdoc.isicres.rpadmin.struts.acciones.login;


import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.Transaction;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.AuthenticationUser;
import com.ieci.tecdoc.common.LDAPAuthenticationUser;
import com.ieci.tecdoc.common.entity.dao.DBEntityDAOFactory;
import com.ieci.tecdoc.common.exception.SecurityException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesdoc.Iuserusertype;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.ISicresGenPerms;
import com.ieci.tecdoc.common.utils.ISicresQueries;
import com.ieci.tecdoc.idoc.authentication.LDAPAuthenticationPolicy;
import com.ieci.tecdoc.idoc.authentication.ldap.LdapBasicFns;
import com.ieci.tecdoc.idoc.authentication.ldap.LdapConnection;
import com.ieci.tecdoc.idoc.authentication.ldap.LdapSearch;
import com.ieci.tecdoc.idoc.decoder.dbinfo.LDAPDef;
import com.ieci.tecdoc.idoc.repository.RepositoryLDAP;
import com.ieci.tecdoc.idoc.utils.CryptoUtils;
import com.ieci.tecdoc.idoc.utils.LDAPRBUtil;
import com.ieci.tecdoc.isicres.desktopweb.utils.RequestUtils;
import com.ieci.tecdoc.isicres.session.security.SecuritySession;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.security.SecurityUseCase;
import com.ieci.tecdoc.utils.HibernateUtil;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.beans.UsuarioRegistradorBean;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapUserConfigUtil;
import es.ieci.tecdoc.isicres.admin.estructura.adapter.ISicresServicioRPAdminAdapter;
import es.ieci.tecdoc.isicres.admin.estructura.dao.LdapUser;
import es.ieci.tecdoc.isicres.admin.estructura.dao.impl.LdapUserImpl;
import es.ieci.tecdoc.isicres.admin.estructura.keys.ISicresAdminUserDefsKeys;
import es.ieci.tecdoc.isicres.admin.service.ISicresServicioRPAdmin;

public class LoginLDAP{

	private static final Logger logger = Logger.getLogger(LoginLDAP.class);

	public static final String PASSWORDCTRL_KEY="PasswordCtrl";

	public static final String NAMECTRL_KEY="NameCtrl";

	public static final String COMA = ",";
	public static final String LITERAL_CN_EQUAL = "CN=";


	public void login(HttpServletRequest request, UseCaseConf useCaseConf) throws ValidationException, SecurityException, Exception{

		SecurityUseCase securityUseCase = new SecurityUseCase();
		//obtenemos el useCaseConf para el proceso de autenticacaion
		String name=LoginAction.getName(request);
		String passwordCrypt=RequestUtils.parseRequestParameterAsString(request, PASSWORDCTRL_KEY);


		// Validacion del usuario.
		securityUseCase.login(useCaseConf, name, passwordCrypt);

		boolean superUser = SecuritySession.isSuperuser(useCaseConf
				.getSessionID());

		CacheBag cacheBag = CacheFactory.getCacheInterface().getCacheEntry(useCaseConf.getSessionID());
		ISicresGenPerms genPerms = (ISicresGenPerms) cacheBag.get(ServerKeys.GENPERMS_USER);

		//comprobamos si es superusuario o tiene algun permiso de administración
		if((superUser) || (valiteAnyPermsAccessAdmin(genPerms))){
			// Una vez validado el usuario se introduce en la sesiÃ³n diversos parametros.
			LoginAction.saveSessionData(request, useCaseConf, superUser,genPerms);
		}else{
			//obtener el dn del usuario que se intenta conectar
			LDAPAuthenticationUser attributesUser = null;

			//obtenemos los attributos del usuario
			attributesUser = getAttributesUserLdap(useCaseConf, name);

			String userLdapAdmin = LdapUserConfigUtil.getUserLDAPAdmin();
			String dnUserLdapAdmin = LdapUserConfigUtil.getDnUserLdapAdmin();
			String dnCompUserLdapAdmin = LITERAL_CN_EQUAL + userLdapAdmin + COMA + dnUserLdapAdmin;

			if (StringUtils.equals(name, userLdapAdmin)
					&& (StringUtils.equalsIgnoreCase(attributesUser.getDn(),
							dnCompUserLdapAdmin))) {
				// Modificamos el perfil del usuario, ya que por configuracion se indica que es administrador de registro
				setProfileAdminToUser(useCaseConf, attributesUser);

				// Volvemos a validar al usuario despues de cambiarle el perfil
				securityUseCase.login(useCaseConf, name, passwordCrypt);

				superUser = SecuritySession.isSuperuser(useCaseConf
						.getSessionID());
			}

			if(superUser){
				// Una vez validado el usuario se introduce en la sesion diversos parametros.
				LoginAction.saveSessionData(request, useCaseConf, superUser,genPerms);
			}else{
				logger.error("Se ha producido un error en el login: El usuario no tiene permisos como Administrador");
				throw new SecurityException(SecurityException.ERROR_USER_APLICATION);
			}
		}
	}

	/**
	 * Función que valida si el usuario tiene alguno de los permisos para acceder a administración
	 *
	 * @param genPerms - Permisos del usuario
	 * @return boolean - true: tiene algún permiso para acceder / false: no tiene ningún permiso de acceso
	 */
	private boolean valiteAnyPermsAccessAdmin(ISicresGenPerms genPerms){
		boolean result = false;
		if (genPerms.getCanModifyAdminUnits()
				|| genPerms.getCanModifyIssueTypes()
				|| genPerms.getCanModifyReports()
				|| genPerms.getCanModifyTransportTypes()
				|| genPerms.getCanModifyUsers()){
			result = true;
		}
		return result;
	}

	/**
	 * Metodo que crea al usuario si es necesario y le asigna el perfil de administrador de registro
	 * @param useCaseConf
	 * @throws SecurityException
	 * @throws Exception
	 * @throws SQLException
	 */
	private void setProfileAdminToUser(UseCaseConf useCaseConf,LDAPAuthenticationUser attributesUser) throws SecurityException{
		try {
			AuthenticationUser user = SecuritySession.getUserLogin(useCaseConf.getSessionID());
			if(user==null){
				// creamos el usuario
				UsuarioRegistradorBean nuevoUsuario = new UsuarioRegistradorBean();
				nuevoUsuario.setLdapGuid(attributesUser.getGuidStringFormat());
				nuevoUsuario.setNombre(attributesUser.getFullName());
				nuevoUsuario.setLdapFullName(attributesUser.getFullName());

				ISicresServicioRPAdmin oServicio = new ISicresServicioRPAdminAdapter();

				Entidad entidad = new Entidad();
				entidad.setIdentificador(useCaseConf.getEntidadId());

				oServicio.crearUsuarioLdap(nuevoUsuario, entidad);

			}else{
				//verificamos los permisos del usuario
				LdapUser users = new LdapUserImpl();
				users.load(user.getId().intValue(), useCaseConf.getEntidadId());

				if ((users.getPermissions().getProductPermissionById(ISicresAdminUserDefsKeys.PRODUCT_IDOC) == null)
						&& (users.getPermissions().getProductPermissionById(
								ISicresAdminUserDefsKeys.PRODUCT_ISICRES) == null)
						&& (users.getPermissions().getProductPermissionById(
								ISicresAdminUserDefsKeys.PRODUCT_IFLOW) == null)) {
					//asignamos los permisos basicos por defecto
					users.assingDefaultPerms(users.getGuid(), useCaseConf.getEntidadId());
				}
			}

			// actualizamos el perfil del usuario
			updateProfileUserLdap(useCaseConf, user);

		} catch (Exception e) {
			logger.error("Se ha producido un error en la creación/modificación del usuario administrador");
			throw new SecurityException("Error de seguridad", e);
		}
	}

	/**
	 * Metodo que actualiza/crea el perfil del usuario
	 * @param useCaseConf
	 * @param user
	 * @throws SecurityException
	 */
	private void updateProfileUserLdap(UseCaseConf useCaseConf,
			AuthenticationUser user) throws SecurityException {
		Iuserusertype userusertype;
		Transaction tran = null;
		try {
			Session session = HibernateUtil.currentSession(useCaseConf.getEntidadId());
			tran = session.beginTransaction();

			List list = ISicresQueries.getUserUserType(session, user.getId()
					.intValue(), 5);

			if (list == null || list.isEmpty()) {
				// creamos el perfil de administrador de registro para el usuario
				userusertype =new Iuserusertype();
				userusertype.setUserid(user.getId().intValue());
				userusertype.setProdid(5);
				userusertype.setType(3);
				session.save(userusertype);
				session.flush();
			}else{
				//actualizamos el perfil que tiene
				userusertype = (Iuserusertype) list.get(0);
				DBEntityDAOFactory.getCurrentDBEntityDAO()
						.updateRole(userusertype.getUserid(),
								userusertype.getProdid(), 3,
								useCaseConf.getEntidadId());
			}

			HibernateUtil.commitTransaction(tran);

		}  catch (HibernateException hE) {
			HibernateUtil.rollbackTransaction(tran);
			logger.error("Imposible crear el permiso administrador");
			throw new SecurityException(SecurityException.ERROR_SQL);
		} catch (Exception e) {
			HibernateUtil.rollbackTransaction(tran);
			logger.error("Imposible crear el permiso administrador");
			throw new SecurityException(SecurityException.ERROR_USER_NOTFOUND);
		} finally {
			HibernateUtil.closeSession(useCaseConf.getEntidadId());
		}
	}

	/**
	 * Metodo que obtiene los atributos del usuario ldap
	 * @param useCaseConf
	 * @param name
	 * @return
	 * @throws SecurityException
	 * @throws Exception
	 */
	private LDAPAuthenticationUser getAttributesUserLdap(
			UseCaseConf useCaseConf, String name) throws SecurityException,
			Exception {
		LDAPAuthenticationUser attributesUser;
		LDAPDef ldapDef = RepositoryLDAP.getInstance(useCaseConf.getEntidadId()).getLDAPInfo();
		if (logger.isDebugEnabled()) {
			logger.debug("LDAPDef [" + ldapDef + "] con el log [" + logger + "]");
		}
		if (ldapDef.getLdapEngine() == 0) {
			throw new SecurityException(
					SecurityException.ERROR_AUTHENTICATION_POLICY_NOTFOUND);
		}

		String dn = ldapDef.getLdapUser();
		String passwordDecrypt = CryptoUtils.decryptPasswordLDAP(ldapDef
				.getLdapPassword());
		String attrID = LDAPRBUtil.getInstance(null).getProperty(
				LDAPAuthenticationPolicy.LDAP_ATTRIBUTES + ldapDef.getLdapEngine());
		String attributes[] = new LDAPAuthenticationPolicy().parseAttributes(attrID);

		LdapConnection conn = new LdapConnection();
		conn.open(ldapDef, dn, passwordDecrypt, 1);
		LdapSearch search = new LDAPAuthenticationPolicy().getSearch(name, conn, ldapDef, attributes);
		attributesUser = new LDAPAuthenticationPolicy().getUserAttributes(search, attributes);

		attributesUser.setGuidStringFormat(LdapBasicFns.formatGuid(conn,
				attributesUser.getGuid()));
		if (logger.isDebugEnabled()) {
			logger.debug("attributesUser [" + attributesUser
					+ "] con el log [" + logger + "]");
		}
		conn.close();
		return attributesUser;
	}
}
