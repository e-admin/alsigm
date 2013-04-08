package ieci.tecdoc.sgm.admsistema.plugin;

import ieci.tecdoc.sgm.admsistema.util.Defs;
import javax.servlet.ServletException;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;


public class ConfigPlugin extends BasePlugin
{

  private static Logger logger          = Logger.getLogger(ConfigPlugin.class);

  /*
   * Atributos por defecto del plugin.
   */
  //private ModuleConfig  m_config        = null;
  private ActionServlet m_servlet       = null;

  /*
   * Atributos específicos de la aplicación
   */
  private String[] m_tiposBasesDatos = null;

/*
   * (non-Javadoc)
   * 
   * @see org.apache.struts.action.PlugIn#destroy()
   */
  public void destroy()
  {
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_BASE_DATOS);
    
    m_servlet = null;
    //m_config = null;

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

    //this.m_config = config;
    this.m_servlet = servlet;
    
    int i = 0;
    
    m_tiposBasesDatos = new String[4]; 
    m_tiposBasesDatos[0] = Defs.PLUGIN_BASE_DATOS_POSTGRES;
    m_tiposBasesDatos[1] = Defs.PLUGIN_BASE_DATOS_ORACLE;
    m_tiposBasesDatos[2] = Defs.PLUGIN_BASE_DATOS_DB2;
    m_tiposBasesDatos[3] = Defs.PLUGIN_BASE_DATOS_SQLSERVER;
    
    m_servlet.getServletContext().setAttribute(Defs.PLUGIN_BASE_DATOS, this.m_tiposBasesDatos);
   
    if (logger.isDebugEnabled())
    {
      logger.debug("Defs.PLUGIN_BASE_DATOS: " + this.m_tiposBasesDatos);
    }
  }
}