package ieci.tecdoc.sgm.autenticacion.plugin;

import ieci.tecdoc.sgm.autenticacion.utils.Defs;

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
  private String m_redirConsulta = null;
  private String m_redirPagoElectronico = null;
  private String m_redirNotificacion = null;
  private String m_redirRegistroTelematico = null;
  private String m_redirCertificacion = null;
  private String m_redirConsultaRegistroTelematico = null;
  
  /*
   * Métodos 
   */
  public String getRedirConsulta() {
	return m_redirConsulta;
  }

  public void setRedirConsulta(String redirConsulta) {
	this.m_redirConsulta = redirConsulta;
  }
  
  public String getRedirPagoElectronico() {
	return m_redirPagoElectronico;
  }

  public void setRedirPagoElectronico(String redirPagoElectronico) {
	this.m_redirPagoElectronico = redirPagoElectronico;
  }

  public String getRedirNotificacion() {
	return m_redirNotificacion;
  }

  public void setRedirNotificacion(String redirNotificacion) {
	this.m_redirNotificacion = redirNotificacion;
  }

  public String getRedirRegistroTelematico() {
	return m_redirRegistroTelematico;
  }

  public void setRedirRegistroTelematico(String redirRegistroTelematico) {
	this.m_redirRegistroTelematico = redirRegistroTelematico;
  }
 
  public String getRedirCertificacion() {
	return m_redirCertificacion;
  }

  public void setRedirCertificacion(String redirCertificacion) {
	m_redirCertificacion = redirCertificacion;
  }
 
  public String getRedirConsultaRegistroTelematico() {
	return m_redirConsultaRegistroTelematico;
  }

  public void setRedirConsultaRegistroTelematico(String consultaRegistroTelematico) {
	m_redirConsultaRegistroTelematico = consultaRegistroTelematico;
  }

/*
   * (non-Javadoc)
   * 
   * @see org.apache.struts.action.PlugIn#destroy()
   */
  public void destroy()
  {
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_REDIRCONSULTA);
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_REDIRPAGOELECTRONICO);
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_REDIRNOTIFICACION);
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_REDIRREGISTROTELEMATICO);
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_REDIRCERTIFICACION);
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_REDIRCONSULTAREGISTROTELEMATICO);
    
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
    
    m_servlet.getServletContext().setAttribute(Defs.PLUGIN_REDIRCONSULTA, this.m_redirConsulta);
    m_servlet.getServletContext().setAttribute(Defs.PLUGIN_REDIRPAGOELECTRONICO, this.m_redirPagoElectronico);
    m_servlet.getServletContext().setAttribute(Defs.PLUGIN_REDIRNOTIFICACION, this.m_redirNotificacion);
    m_servlet.getServletContext().setAttribute(Defs.PLUGIN_REDIRREGISTROTELEMATICO, this.m_redirRegistroTelematico);
    m_servlet.getServletContext().setAttribute(Defs.PLUGIN_REDIRCERTIFICACION, this.m_redirCertificacion);
    m_servlet.getServletContext().setAttribute(Defs.PLUGIN_REDIRCONSULTAREGISTROTELEMATICO, this.m_redirConsultaRegistroTelematico);
    
    if (logger.isDebugEnabled())
    {
      logger.debug("Defs.PLUGIN_REDIRCONSULTA: " + this.m_redirConsulta);
      logger.debug("Defs.PLUGIN_REDIRPAGOELECTRONICO: " + this.m_redirPagoElectronico);
      logger.debug("Defs.PLUGIN_REDIRNOTIFICACION: " + this.m_redirNotificacion);
      logger.debug("Defs.PLUGIN_REDIRREGISTROTELEMATICO: " + this.m_redirRegistroTelematico);
      logger.debug("Defs.PLUGIN_REDIRCERTIFICACION: " + this.m_redirCertificacion);
      logger.debug("Defs.PLUGIN_REDIRCONSULTAREGISTROTELEMATICO: " + this.m_redirConsultaRegistroTelematico);
    }
  }
}