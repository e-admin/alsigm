package ieci.tecdoc.sgm.consulta_telematico.plugin;
/*
 *  $Id: ConfigPlugin.java,v 1.2.2.1 2008/02/05 13:31:39 jconca Exp $
 */
import ieci.tecdoc.sgm.consulta_telematico.utils.Defs;

import javax.servlet.ServletException;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.config.ModuleConfig;

/**
 * @deprecated
 */
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
  private String m_aplicacionTelematico = null;
  private String m_carpetaTelematico = null;
  
  /*
   * Métodos 
   */
  public String getRedirAutenticacion() {
	return m_redirAutenticacion;
  }

  public void setRedirAutenticacion(String redirAutenticacion) {
	this.m_redirAutenticacion = redirAutenticacion;
  }
  
  public String getAplicacionTelematico() {
	return m_aplicacionTelematico;
  }

  public void setAplicacionTelematico(String aplicacionTelematico) {
	m_aplicacionTelematico = aplicacionTelematico;
  }

  public String getCarpetaTelematico() {
	return m_carpetaTelematico;
  }

  public void setCarpetaTelematico(String carpetaTelematico) {
	m_carpetaTelematico = carpetaTelematico;
  }

/*
   * (non-Javadoc)
   * 
   * @see org.apache.struts.action.PlugIn#destroy()
   */
  public void destroy()
  {
    m_servlet.getServletContext().removeAttribute(Defs.REDIRECCION_AUTENTICACION);
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_APLICACION_TELEMATICO);
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_CARPETA_TELEMATICO);
    
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
    
    //cogemos la configuracion externalizada
    populate();
    
    m_servlet.getServletContext().setAttribute(Defs.REDIRECCION_AUTENTICACION, this.m_redirAutenticacion);
    m_servlet.getServletContext().setAttribute(Defs.PLUGIN_APLICACION_TELEMATICO, this.m_aplicacionTelematico);
    m_servlet.getServletContext().setAttribute(Defs.PLUGIN_CARPETA_TELEMATICO, this.m_carpetaTelematico);
    
    if (logger.isDebugEnabled())
    {
      logger.debug("Defs.REDIRECCION_AUTENTICACION: " + this.m_redirAutenticacion);
      logger.debug("Defs.PLUGIN_APLICACION_TELEMATICO: " + this.m_aplicacionTelematico);
      logger.debug("Defs.PLUGIN_CARPETA_TELEMATICO: " + this.m_carpetaTelematico);
    }
  }
  
  protected void populate(){
	  ConfigLoader configloader = new ConfigLoader();
	  this.m_redirAutenticacion=configloader.getRedirAutenticacionValue();
	  this.m_aplicacionTelematico=configloader.getAplicacionTelematicoValue();
	  this.m_carpetaTelematico=configloader.getCarpetaTelematicoValue();
	  
  }
  
  
}