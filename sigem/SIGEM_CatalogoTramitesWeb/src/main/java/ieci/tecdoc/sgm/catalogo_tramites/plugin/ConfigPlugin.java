package ieci.tecdoc.sgm.catalogo_tramites.plugin;

import ieci.tecdoc.sgm.catalogo_tramites.utils.Defs;
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
  private String[] m_horas = null;
  private String[] m_minutos = null;
  private String[] m_dias = null;
  private String[] m_meses = null;

/*
   * (non-Javadoc)
   * 
   * @see org.apache.struts.action.PlugIn#destroy()
   */
  public void destroy()
  {
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_DIAS);
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_HORAS);
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_MESES);
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_MINUTOS);
    
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
    
    m_dias = new String[31]; 
    for(i=1; i<32; i++)
    	m_dias[i-1] = ((i >= 10) ? "" : "0") + i;
    
 	m_horas = new String[24]; 
 	for(i=0; i<24; i++) 
 		m_horas[i] = ((i > 9) ? "" : "0") + i;
 	
	m_minutos = new String[4];
	i=0;
 	for(int k=0; k<60; k=k+15)
 		m_minutos[i++] = ((k >= 10) ? "" : "0") + k;
 	 
	m_meses = new String[12]; 
 	for(i=1; i<13; i++) 
 		m_meses[i-1] = "calendar.mes." + i;
 	 
    m_servlet.getServletContext().setAttribute(Defs.PLUGIN_DIAS, this.m_dias);
    m_servlet.getServletContext().setAttribute(Defs.PLUGIN_HORAS, this.m_horas);
    m_servlet.getServletContext().setAttribute(Defs.PLUGIN_MESES, this.m_meses);
    m_servlet.getServletContext().setAttribute(Defs.PLUGIN_MINUTOS, this.m_minutos);
   
    if (logger.isDebugEnabled())
    {
      logger.debug("Defs.PLUGIN_DIAS: " + this.m_dias);
      logger.debug("Defs.PLUGIN_HORAS: " + this.m_horas);
      logger.debug("Defs.PLUGIN_MESES: " + this.m_meses);
      logger.debug("Defs.PLUGIN_MINUTOS: " + this.m_minutos);
    }
  }
}