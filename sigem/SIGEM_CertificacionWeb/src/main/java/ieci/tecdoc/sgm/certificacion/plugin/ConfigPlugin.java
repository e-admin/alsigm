package ieci.tecdoc.sgm.certificacion.plugin;

import ieci.tecdoc.sgm.certificacion.utilsweb.Defs;

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
  private String m_redirAutenticacion = null;
  
  /*
   * Métodos 
   */
  public String getRedirAutenticacion() {
    return m_redirAutenticacion;
  }
	
  public void setRedirAutenticacion(String redirAutenticacion) {
    m_redirAutenticacion = redirAutenticacion;
  }
  
/*
   * (non-Javadoc)
   * 
   * @see org.apache.struts.action.PlugIn#destroy()
   */
  public void destroy()
  {
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_REDIRAUTENTICACION);
    
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
    
    m_servlet.getServletContext().setAttribute(Defs.PLUGIN_REDIRAUTENTICACION, this.m_redirAutenticacion);
    
    if (logger.isDebugEnabled())
    {
      logger.debug("Defs.PLUGIN_REDIRAUTENTICACION: " + this.m_redirAutenticacion);
    }
  }
}