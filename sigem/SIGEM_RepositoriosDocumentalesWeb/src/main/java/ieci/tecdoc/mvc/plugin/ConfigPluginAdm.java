package ieci.tecdoc.mvc.plugin;

//import ieci.tecdoc.audit.util.ReloadLog4JConfiguration;
import ieci.tecdoc.core.db.DBSessionManager;
import ieci.tecdoc.core.db.DbConnection;
import ieci.tecdoc.core.ldap.LdapConnCfg;
import ieci.tecdoc.core.ldap.LdapConnection;
import ieci.tecdoc.idoc.admin.api.ObjFactory;
import ieci.tecdoc.idoc.admin.api.system.SystemCfg;
import ieci.tecdoc.mvc.util.Constantes;
import ieci.tecdoc.mvc.util.MvcDefs;
import ieci.tecdoc.sbo.config.CfgMdoConfig;
import ieci.tecdoc.sbo.idoc.api.Login;
import ieci.tecdoc.sbo.idoc.login.LoginMethod;
import ieci.tecdoc.sbo.uas.ldap.UasAuthConfig;
import ieci.tecdoc.sbo.uas.ldap.UasConfigUtil;
import ieci.tecdoc.sgm.entidades.EntidadesManager;
import ieci.tecdoc.sgm.entidades.beans.Entidad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

public class ConfigPluginAdm implements PlugIn
{
	
   private static Logger   logger   =  Logger.getLogger(ConfigPluginAdm.class);

	/*
	 * Atributos por defecto del plugin.
	 */
	private ModuleConfig   m_config                =  null;
	private ActionServlet  m_servlet               =  null;
	/*
	 * Idioma por defecto para la aplicación.
	 */
	private String         m_idioma                =  null;
	/*
	 * skin por defecto en la aplicación.
	 */
	private String         m_default_skin          =  null;
	/*
	 * Parámetros necesarios para conformar el base de la páginas HTML.
	 */
	private String         m_protocol              =  null;
	private String         m_base_dir              =  null;
	/*
	 * Path para las imagenes de configuracion del Tree.
	 */
	private String         m_images_dir            =  null;

	/*
	 * Path y nombre del fichero de configuracion del Log4J.
	 */
	private String         m_log4j_path            =  null;
	private String         m_log4j_file            =  null;
	/*
	 * Parametros para acceder a las plantillas protegidas del portal a las que
	 * solo deberia tener acceso la gente de administración, soporte y/o
	 * mantenimiento.
	 */
	private String         m_invesdoc_admin_login  =  null;
	private String         m_invesdoc_admin_pwd    =  null;
	/*
	 * Servidor y puerto al que realizar las peticiones.
	 */
	private String				m_httpServer				= null;
	private String				m_httpPort					= null;

	/*
	 *
	 */
	private String				m_system_config_name		= null;
	private String				m_ldap_config_name		= null;

	/*
	 * Lista de los idiomas en los que corre la aplicación .
	 */
   private ArrayList       m_languages             =  null;

	/*
	 * Lista de los locales de los idiomas en los que corre la aplicación .
	 */
   private ArrayList       m_locales               =  null;
	
	/*
	 * El locale por defecto de la aplicación .
	 */
   private String          m_defaultLocale         =  null;
   
	/*
	 * Indica cual es la pestaña por defecto que aparecerá marcada.
	 */
   private String          m_default_tab           =  "";
   
	/*
	 * Indica el estado del control de errores. En development mostrara el error
	 * capturado el mensaje y la pila. En produccion mostrara el error capturado
	 * y el mensaje de error.
	 */
   private String          m_exception_handle      =  "";

   private Integer         m_docName_maxlength     =  null;
   
   private Integer maxChildrenLdap = null;
   private Boolean useCertificate = null;
   private Boolean enableCertError9 = null;
   
   
   public void setMaxChildrenLdap (String maxChildrenLdap) {
       this.maxChildrenLdap = new Integer (maxChildrenLdap) ;
   }
   
   public void setUseCertificate (String bool){
       if (bool.equals("false") || bool.equals("true"))
           useCertificate = new Boolean (bool);
       else
           useCertificate = new Boolean (false);
   }
   public void setEnableCertError9 (String bool){
       if (bool.equals("false") || bool.equals("true"))
    	   this.enableCertError9 = new Boolean (bool);
       else
    	   this.enableCertError9 = new Boolean (false);
   }
   
   

   //~ Methods
	// -----------------------------------------------------------------

	/**
	 * DOCUMENT ME!
	 * 
	 * @param idioma
	 *           The idioma to set.
	 */
	public void setIdioma(String idioma)
	{

		if(logger.isDebugEnabled()) logger.debug("m_idioma: " + idioma);
		this.m_idioma = idioma;

	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param default_skin
	 *           The default_skin to set.
	 */
	public void setDefault_skin(String default_skin)
	{

		if(logger.isDebugEnabled())
				logger.debug("m_default_skin: " + default_skin);
		this.m_default_skin = default_skin;

	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param protocol
	 *           The protocol to set.
	 */
	public void setProtocol(String protocol)
	{

		if(logger.isDebugEnabled()) logger.debug("m_protocol: " + protocol);
		this.m_protocol = protocol;

	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param base_dir
	 *           The base_dir to set.
	 */
	public void setBase_dir(String base_dir)
	{

		if(logger.isDebugEnabled()) logger.debug("m_base_dir: " + base_dir);
		this.m_base_dir = base_dir;

	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param images_dir
	 *           The base_dir to set.
	 */
	public void setImages_dir(String images_dir)
	{

		if(logger.isDebugEnabled()) logger.debug("m_images_dir: " + images_dir);
		this.m_images_dir = images_dir;

	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param log4j_path
	 *           The log4j file path to set.
	 */
	public void setLog4j_path(String log4j_path)
	{

		if(logger.isDebugEnabled()) logger.debug("m_log4j_path: " + log4j_path);
		this.m_log4j_path = log4j_path;

	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param log4j_file
	 *           The log4j file name to set.
	 */
	public void setLog4j_file(String log4j_file)
	{

		if(logger.isDebugEnabled()) logger.debug("m_log4j_file: " + log4j_file);
		this.m_log4j_file = log4j_file;

	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param invesdoc_admin_login
	 *           The invesdoc_admin_login to set.
	 */
	public void setInvesdoc_admin_login(String invesdoc_admin_login)
	{

		if(logger.isDebugEnabled())
				logger.debug("m_invesdoc_admin_login: " + invesdoc_admin_login);
		this.m_invesdoc_admin_login = invesdoc_admin_login;

	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param invesdoc_admin_pwd
	 *           The invesdoc_admin_password to set.
	 */
	public void setInvesdoc_admin_pwd(String invesdoc_admin_pwd)
	{

		if(logger.isDebugEnabled())
				logger.debug("m_invesdoc_admin_pwd: " + invesdoc_admin_pwd);
		this.m_invesdoc_admin_pwd = invesdoc_admin_pwd;

	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param httpServer
	 *           The invesdoc_admin_password to set.
	 */
	public void setHttpServer(String httpServer)
	{

		if(logger.isDebugEnabled()) logger.debug("m_httpServer: " + httpServer);
		this.m_httpServer = httpServer;

	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param httpPort
	 *           The invesdoc_admin_password to set.
	 */
	public void setHttpPort(String httpPort)
	{

		if(logger.isDebugEnabled()) logger.debug("m_httpPort: " + httpPort);
		this.m_httpPort = httpPort;

	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param system_config_name
	 *           The Jconfig file name for System configuration File
	 */
	public void setSystem_config_name(String system_config_name)
	{

		if(logger.isDebugEnabled())
				logger.debug("m_system_config_name: " + system_config_name);
		this.m_system_config_name = system_config_name;

	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param ldap_config_name
	 *           The Jconfig file name for Ldap configuration File
	 */
	public void setLdap_config_name(String ldap_config_name)
	{

		if(logger.isDebugEnabled())
				logger.debug("m_ldap_config_name: " + ldap_config_name);
		this.m_ldap_config_name = ldap_config_name;

	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param languages
	 *           Application languages to set.
	 */
	public void setLanguages(String languages)
	{
		m_languages = new ArrayList();

		String[] langs = languages.split(MvcDefs.PERMITTED_PATHS_SEPARATOR);
		for(int i = 0; i < langs.length; i++)
			this.m_languages.add(langs[i].trim());
		if(logger.isDebugEnabled())
				for(int i = 0; i < langs.length; i++)
					if(logger.isDebugEnabled())
							logger.debug("Active application language: " + langs[i]);

	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param localesParam
	 *           Application languages to set.
	 */
	public void setLocales(String localesParam)
	{
		m_locales = new ArrayList();
		String[] locales = localesParam.split(MvcDefs.PERMITTED_PATHS_SEPARATOR);
		for(int i = 0; i < locales.length; i++)
			this.m_locales.add(locales[i].trim());
		if(logger.isDebugEnabled())
				for(int i = 0; i < locales.length; i++)
					if(logger.isDebugEnabled())
							logger.debug("Active application locale: " + locales[i]);

	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @param localesParam
	 *           Application languages to set.
	 */
	public void setDefaultLocale(String defaultLocale)
	{
		if(logger.isDebugEnabled())
				logger.debug("defaultLocale: " + defaultLocale);

		if(defaultLocale != null && defaultLocale == "")
				this.m_defaultLocale = defaultLocale;
	}

	/**
	 * @param default_tab
	 *           The default_tab to set.
	 */
	public void setDefault_tab(String default_tab)
	{
		if(logger.isDebugEnabled()) logger.debug("Default Tab: " + default_tab);
		m_default_tab = getDefaultTab(default_tab);
		if(logger.isDebugEnabled())
				logger.debug("m_default_tab setted: " + m_default_tab);
	}

	public String getException_handle()
	{
		if(logger.isDebugEnabled())
				logger.debug("ExceptionHandle: " + m_exception_handle);
		return m_exception_handle;
	}

	public void setException_handle(String m_exception_handle)
	{
		this.m_exception_handle = m_exception_handle;
	}

	/**
	 * @autor IECISA
	 * 
	 * @param default_tab
	 * 
	 * @return void
	 * @since InvesDoc 1.0
	 */
	private String getDefaultTab(String default_tab)
	{
		if(default_tab.equals(MvcDefs.TOKEN_DEFAULT_SELECTED_TAB_DOCS)
				|| default_tab.equals(MvcDefs.TOKEN_DEFAULT_SELECTED_TAB_VALUES))
			return default_tab;
		else
			return null;

	}

	/**
	 * @return Returns the docName_maxlength.
	 */
	public void setDocName_maxlength(String maxLength)
	{
		try
		{
			this.m_docName_maxlength = new Integer(maxLength);
		}
		catch (NumberFormatException ex)
		{
			logger.error(ex.getMessage(), ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.PlugIn#destroy()
	 */
	public void destroy()
	{

		m_servlet.getServletContext().removeAttribute(MvcDefs.PLUGIN_IDIOMA);
		m_servlet.getServletContext().removeAttribute(MvcDefs.PLUGIN_DEFAULT_SKIN);
		m_servlet.getServletContext().removeAttribute(MvcDefs.PLUGIN_PROTOCOL);
		m_servlet.getServletContext().removeAttribute(MvcDefs.PLUGIN_BASE_DIR);
		m_servlet.getServletContext().removeAttribute(MvcDefs.PLUGIN_IMAGES_DIR);
		m_servlet.getServletContext().removeAttribute(MvcDefs.PLUGIN_LOG4J_PATH);
		m_servlet.getServletContext().removeAttribute(MvcDefs.PLUGIN_LOG4J_FILE);
		m_servlet.getServletContext().removeAttribute(MvcDefs.PLUGIN_INVESDOC_ADMIN_LOGIN);
		m_servlet.getServletContext().removeAttribute(MvcDefs.PLUGIN_INVESDOC_ADMIN_PWD);
		m_servlet.getServletContext().removeAttribute(MvcDefs.PLUGIN_HTTP_SERVER);
		m_servlet.getServletContext().removeAttribute(MvcDefs.PLUGIN_HTTP_PORT);
		m_servlet.getServletContext().removeAttribute(MvcDefs.PLUGIN_APP_LANGUAGES);
		m_servlet.getServletContext().removeAttribute(MvcDefs.PLUGIN_DEAFULT_APP_LOCALE);
		m_servlet.getServletContext().removeAttribute(MvcDefs.PLUGIN_DEFAULT_TAB);
		m_servlet.getServletContext().removeAttribute(MvcDefs.PLUGIN_EXCEPTION_HANDLE);
		m_servlet.getServletContext().removeAttribute(MvcDefs.TOKEN_MAX_FILE_NAME_LENGTH);
		m_servlet.getServletContext().removeAttribute(Constantes.MAX_CHILDREN_LDAP);
		m_servlet.getServletContext().removeAttribute(Constantes.MULTISESSION_CERT_ERROR);
		m_servlet = null;
		m_config = null;

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

		//     Remember our associated configuration and servlet
		this.m_config = config;
		this.m_servlet = servlet;

		// Almacenamos objetos en el contexto de la applicacion
		m_servlet.getServletContext().setAttribute(MvcDefs.PLUGIN_IDIOMA, this.m_idioma);
		m_servlet.getServletContext().setAttribute(MvcDefs.PLUGIN_DEFAULT_SKIN, this.m_default_skin);
		m_servlet.getServletContext().setAttribute(MvcDefs.PLUGIN_PROTOCOL, this.m_protocol);
		m_servlet.getServletContext().setAttribute(MvcDefs.PLUGIN_BASE_DIR, this.m_base_dir);
		m_servlet.getServletContext().setAttribute(MvcDefs.PLUGIN_IMAGES_DIR, this.m_images_dir);
		m_servlet.getServletContext().setAttribute(MvcDefs.PLUGIN_LOG4J_PATH, this.m_log4j_path);
		m_servlet.getServletContext().setAttribute(MvcDefs.PLUGIN_LOG4J_FILE, this.m_log4j_file);
		m_servlet.getServletContext().setAttribute(MvcDefs.PLUGIN_INVESDOC_ADMIN_LOGIN, this.m_invesdoc_admin_login);
		m_servlet.getServletContext().setAttribute(MvcDefs.PLUGIN_INVESDOC_ADMIN_PWD, this.m_invesdoc_admin_pwd);
		m_servlet.getServletContext().setAttribute(MvcDefs.PLUGIN_HTTP_SERVER, this.m_httpServer);
		m_servlet.getServletContext().setAttribute(MvcDefs.PLUGIN_HTTP_PORT, this.m_httpPort);
		m_servlet.getServletContext().setAttribute(MvcDefs.PLUGIN_DEFAULT_TAB, this.m_default_tab);
		m_servlet.getServletContext().setAttribute(MvcDefs.PLUGIN_DEAFULT_APP_LOCALE, this.m_defaultLocale);
		m_servlet.getServletContext().setAttribute(MvcDefs.PLUGIN_EXCEPTION_HANDLE, this.m_exception_handle);
		m_servlet.getServletContext().setAttribute(Constantes.MAX_CHILDREN_LDAP, this.maxChildrenLdap);
		
		m_servlet.getServletContext().setAttribute(Constantes.USE_CERTIFICATE, this.useCertificate);
		m_servlet.getServletContext().setAttribute(Constantes.MULTISESSION_CERT_ERROR, this.enableCertError9);
		

		
        // Comprobar si existe la conexion a la bd
		try
	      {
			 
		 	 //hay n entidades
		     List listaEntidades=EntidadesManager.obtenerEntidades();
		     HashMap hashEntidades=new HashMap();
		     Login login       =  new Login();
		     int loginMethod;
		     for(int i=0;i<listaEntidades.size();i++){
		    	 Entidad entidad=(Entidad)listaEntidades.get(i);
		    	 try{
			    	 loginMethod =  login.getLoginMethod(entidad.getIdentificador());
			    	 hashEntidades.put(entidad.getIdentificador(), new Integer(loginMethod));		    		 
		    	 }catch (Exception e) {
		    		 hashEntidades.put(entidad.getIdentificador(), new Integer(-1));
		    		 logger.error("Error en la clase "+this.getClass().getName()+". No se ha podido recuperar la entidad: "+entidad.getIdentificador() );
				}

		     }
		     m_servlet.getServletContext().setAttribute(MvcDefs.TOKEN_LOGIN_METHOD, hashEntidades);
	         
			
			/*
		     Login login       =  new Login();		     
	         int loginMethod =  login.getLoginMethod("00001");
	         logger.info("Application Login Method: "+ loginMethod);
	         m_servlet.getServletContext().setAttribute(MvcDefs.TOKEN_LOGIN_METHOD, new Integer(loginMethod));
	        */
	         
	         
	         /*
	         if ( (loginMethod == LoginMethod.LDAP) || (loginMethod == LoginMethod.SSO_LDAP )){
	             try {
	                 DbConnection.open(DBSessionManager.getSession("00001"));
			         LdapConnCfg connCfg = UasConfigUtil.createLdapConnConfig();
			         LdapConnection ldapConn = new LdapConnection();
			         ldapConn.open(connCfg);
			         
			         UasAuthConfig  authCfg     = null;
		             authCfg = UasConfigUtil.createUasAuthConfig();
		             String userAttNameKey =  authCfg.getUserAttrName();
		             m_servlet.getServletContext().setAttribute("userAttNameKey", userAttNameKey);
			         
	             }catch (Exception ex){
	                 m_servlet.getServletContext().setAttribute(MvcDefs.TOKEN_LDAP_CONNECT_ERROR, ex);
	             }
	         }
	         */
		     
		     /*
	 		// Determinar si en el sistema está configurada la busqueda documental
	 		SystemCfg systemCfg = ObjFactory.createSystem();
	 		boolean hasFtsConfig = false;
	         try {
	             hasFtsConfig =  systemCfg.hasFtsConfig("00001") ;
	         } catch (Exception e) {
	             logger.debug(e);
	         }
	         m_servlet.getServletContext().setAttribute(Constantes.HAS_FTS_CONFIG, new Boolean(hasFtsConfig));
	         */
	      }
	      catch (Exception e)
	      {
	          m_servlet.getServletContext().setAttribute(MvcDefs.TOKEN_BD_CONNECT_ERROR, e);
	          logger.error(e.getMessage(),e);
	      }
		

	}

	

}