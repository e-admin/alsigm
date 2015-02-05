package ieci.tecdoc.isicres.rpadmin.struts.plugin;


import ieci.tecdoc.isicres.rpadmin.struts.acciones.business.Constantes;
import ieci.tecdoc.isicres.rpadmin.struts.util.MvcDefs;

import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import com.ieci.tecdoc.isicres.desktopweb.Keys;

import es.ieci.tecdoc.fwktd.core.spring.configuration.jdbc.datasource.MultiEntityContextHolder;
import es.ieci.tecdoc.isicres.admin.beans.Entidad;
import es.ieci.tecdoc.isicres.admin.core.db.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.core.db.DbConnection;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapConnCfg;
import es.ieci.tecdoc.isicres.admin.core.ldap.LdapConnection;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.api.Login;
import es.ieci.tecdoc.isicres.admin.sbo.idoc.login.LoginMethod;
import es.ieci.tecdoc.isicres.admin.sbo.uas.ldap.UasAuthConfig;
import es.ieci.tecdoc.isicres.admin.sbo.uas.ldap.UasConfigUtil;

public class ConfigPluginAdm implements PlugIn
{

	private static Logger   logger   =  Logger.getLogger(ConfigPluginAdm.class);

	/*
	 * Atributos por defecto del plugin.
	 */
	private ActionServlet  m_servlet               =  null;

	/*
	 * Atributos Ldap
	 */
	private Integer maxChildrenLdap = null;

	public void setMaxChildrenLdap (String maxChildrenLdap) {
		this.maxChildrenLdap = new Integer (maxChildrenLdap) ;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.struts.action.PlugIn#destroy()
	 */
	public void destroy()
	{
		m_servlet.getServletContext().removeAttribute(Constantes.MAX_CHILDREN_LDAP);
		m_servlet = null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.apache.struts.action.PlugIn#init(org.apache.struts.action.ActionServlet,
	 *      org.apache.struts.config.ModuleConfig)
	 */
	public void init(ActionServlet servlet, ModuleConfig config)
	throws ServletException
	{
		// Remember our associated configuration and servlet
		this.m_servlet = servlet;

		// Almacenamos objetos en el contexto de la applicacion
		m_servlet.getServletContext().setAttribute(Constantes.MAX_CHILDREN_LDAP, this.maxChildrenLdap);

		// Comprobar si existe la conexion a la bd
		try
		{
			//Se obtiene la entidad
			Entidad entidad = new Entidad();
			entidad.setIdentificador(MultiEntityContextHolder.getEntity());

			HashMap hashEntidades=new HashMap();
			Login login       =  new Login();
			int loginMethod = 0;

			try{
				loginMethod =  login.getLoginMethod(entidad.getIdentificador());

				hashEntidades.put(entidad.getIdentificador(), new Integer(loginMethod));

				if ( (loginMethod == LoginMethod.LDAP) || (loginMethod == LoginMethod.SSO_LDAP )){
					try {
						DbConnection.open(DBSessionManager.getSession());
						LdapConnCfg connCfg = UasConfigUtil.createLdapConnConfig(entidad.getIdentificador());
						LdapConnection ldapConn = new LdapConnection();
						ldapConn.open(connCfg);

						UasAuthConfig  authCfg     = null;
						authCfg = UasConfigUtil.createUasAuthConfig(entidad.getIdentificador());
						String userAttNameKey =  authCfg.getUserAttrName();
						m_servlet.getServletContext().setAttribute("userAttNameKey", userAttNameKey);
						DbConnection.close();

					}catch (Exception ex){
						m_servlet.getServletContext().setAttribute(MvcDefs.TOKEN_LDAP_CONNECT_ERROR, ex);
					}
				}

			}catch (Exception e) {
				hashEntidades.put(entidad.getIdentificador(), new Integer(-1));
				logger.error("Error en la clase "+this.getClass().getName()+". No se ha podido recuperar la entidad: "+entidad.getIdentificador() );
			}

			m_servlet.getServletContext().setAttribute(MvcDefs.TOKEN_LOGIN_METHOD, hashEntidades);

		}
		catch (Exception e)
		{
			m_servlet.getServletContext().setAttribute(MvcDefs.TOKEN_BD_CONNECT_ERROR, e);
			logger.error(e.getMessage(),e);
		}


		/*try
		{
			//hay n entidades
			ServicioEntidades oServicio = LocalizadorServicios.getServicioEntidades();
			List listaEntidades=oServicio.obtenerEntidades();
			HashMap hashEntidades=new HashMap();
			Login login       =  new Login();
			int loginMethod = 0;
			Entidad entidad = null;
			for(int i=0;i<listaEntidades.size();i++){
				entidad =(Entidad)listaEntidades.get(i);
				try{
					loginMethod =  login.getLoginMethod(entidad.getIdentificador());

					hashEntidades.put(entidad.getIdentificador(), new Integer(loginMethod));

					if ( (loginMethod == LoginMethod.LDAP) || (loginMethod == LoginMethod.SSO_LDAP )){
						try {
							DbConnection.open(DBSessionManager.getSession(entidad.getIdentificador()));
							LdapConnCfg connCfg = UasConfigUtil.createLdapConnConfig(entidad.getIdentificador());
							LdapConnection ldapConn = new LdapConnection();
							ldapConn.open(connCfg);

							UasAuthConfig  authCfg     = null;
							authCfg = UasConfigUtil.createUasAuthConfig(entidad.getIdentificador());
							String userAttNameKey =  authCfg.getUserAttrName();
							m_servlet.getServletContext().setAttribute("userAttNameKey", userAttNameKey);
							DbConnection.close();

						}catch (Exception ex){
							m_servlet.getServletContext().setAttribute(MvcDefs.TOKEN_LDAP_CONNECT_ERROR, ex);
						}
					}

				}catch (Exception e) {
					hashEntidades.put(entidad.getIdentificador(), new Integer(-1));
					logger.error("Error en la clase "+this.getClass().getName()+". No se ha podido recuperar la entidad: "+entidad.getIdentificador() );
				}
			}
			m_servlet.getServletContext().setAttribute(MvcDefs.TOKEN_LOGIN_METHOD, hashEntidades);

		}
		catch (Exception e)
		{
			m_servlet.getServletContext().setAttribute(MvcDefs.TOKEN_BD_CONNECT_ERROR, e);
			logger.error(e.getMessage(),e);
		}*/
	}
}