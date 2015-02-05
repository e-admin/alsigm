package ieci.tecdoc.sgm.backoffice.plugin;

import ieci.tecdoc.sgm.backoffice.utils.Defs;

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
  private String m_singleSignOn = null;

  /*
   * Métodos
   */
  public String getSingleSignOn() {
	return m_singleSignOn;
  }

  public void setSingleSignOn(String singleSignOn) {
	this.m_singleSignOn = singleSignOn;
  }

/*
   * (non-Javadoc)
   *
   * @see org.apache.struts.action.PlugIn#destroy()
   */
  public void destroy()
  {
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_SINGLE_SIGN_ON);

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

    //cogemos la configuracion de la externalizacion
    populate();

    m_servlet.getServletContext().setAttribute(Defs.PLUGIN_SINGLE_SIGN_ON, getSingleSignOn());

    if (logger.isDebugEnabled())
    {
      logger.debug("Defs.PLUGIN_SINGLE_SIGN_ON: " + getSingleSignOn());
    }
  }

  protected void populate()
  {
	  ConfigLoader configloader = new ConfigLoader();
	  setSingleSignOn(configloader.getSingleSignOnValue());
  }
}