package ieci.tecdoc.sgm.registro.plugin;

import ieci.tecdoc.sgm.registro.utils.Defs;

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
  private String m_organismo = null;
  private String m_tmpUploadPath = null;
  private String m_tmpXmlPath = null;
  private String m_tramitesPath = null;
  private String m_aFirma = null;
  private String m_plantilla = null;
  private String m_certificado = null;
  private String m_redirAutenticacion = null;
  private String m_iniciarExpediente = null;
  private String m_iniciarExpedienteConVirus = null;
  private String m_registrarConVirus = null;
  private String m_iniciarExpedienteErrorRegistroEstadoError = null;
  private String m_subsanacionSinNumeroRegistroInicialCodigoOficina = null;
  private String m_subsanacionSinNumeroRegistroInicialFirmarSolicitud = null;
  private String m_iniciarExpedienteErrorEnviarEmailOrigen =null;
  private String m_iniciarExpedienteErrorEnviarEmailDestino =null;
  private String m_iniciarExpedienteErrorEnviarEmailAsunto =null;

  /*
   * Métodos
   */
  public String getOrganismo() {
	return m_organismo;
  }

  public void setOrganismo(String organismo) {
	this.m_organismo = organismo;
  }

  public String getTmpUploadPath() {
	return m_tmpUploadPath;
  }

  public void setTmpUploadPath(String uploadPath) {
	m_tmpUploadPath = uploadPath;
  }

  public String getTmpXmlPath() {
	return m_tmpXmlPath;
  }

  public void setTmpXmlPath(String xmlPath) {
	m_tmpXmlPath = xmlPath;
  }

  public String getTramitesPath() {
	return m_tramitesPath;
  }

  public void setTramitesPath(String path) {
	m_tramitesPath = path;
  }

  public String getAFirma() {
	return m_aFirma;
  }

  public void setAFirma(String aFirma) {
	this.m_aFirma = aFirma;
  }

  public String getPlantilla() {
	return m_plantilla;
  }

  public void setPlantilla(String plantilla) {
	m_plantilla = plantilla;
  }

  public String getCertificado() {
    return m_certificado;
  }

  public void setCertificado(String certificado) {
    m_certificado = certificado;
  }

  public String getRedirAutenticacion() {
    return m_redirAutenticacion;
  }

  public void setRedirAutenticacion(String redirAutenticacion) {
    m_redirAutenticacion = redirAutenticacion;
  }

  public String getIniciarExpedienteConVirus() {
	return m_iniciarExpedienteConVirus;
  }

  public void setIniciarExpedienteConVirus(String expedienteConVirus) {
	m_iniciarExpedienteConVirus = expedienteConVirus;
  }

  public String getRegistrarConVirus() {
	return m_registrarConVirus;
  }

  public void setRegistrarConVirus(String conVirus) {
	m_registrarConVirus = conVirus;
  }


  public void setIniciarExpediente(String iniciarExpediente) {
	m_iniciarExpediente = iniciarExpediente;
  }

  public String getIniciarExpediente() {
	return m_iniciarExpediente;
  }

  public String getIniciarExpedienteErrorRegistroEstadoError() {
	return m_iniciarExpedienteErrorRegistroEstadoError;
  }

  public void setIniciarExpedienteErrorRegistroEstadoError(String iniciarExpedienteErrorRegistroEstadoError) {
	m_iniciarExpedienteErrorRegistroEstadoError = iniciarExpedienteErrorRegistroEstadoError;
  }

  public String getM_subsanacionSinNumeroRegistroInicialCodigoOficina() {
	return m_subsanacionSinNumeroRegistroInicialCodigoOficina;
  }

  public void setM_subsanacionSinNumeroRegistroInicialCodigoOficina(
		String mSubsanacionSinNumeroRegistroInicialCodigoOficina) {
	m_subsanacionSinNumeroRegistroInicialCodigoOficina = mSubsanacionSinNumeroRegistroInicialCodigoOficina;
  }

  public String getM_subsanacionSinNumeroRegistroInicialFirmarSolicitud() {
	return m_subsanacionSinNumeroRegistroInicialFirmarSolicitud;
  }

  public void setM_subsanacionSinNumeroRegistroInicialFirmarSolicitud(
		String mSubsanacionSinNumeroRegistroInicialFirmarSolicitud) {
	m_subsanacionSinNumeroRegistroInicialFirmarSolicitud = mSubsanacionSinNumeroRegistroInicialFirmarSolicitud;
  }
  
  
  

/*
   * (non-Javadoc)
   *
   * @see org.apache.struts.action.PlugIn#destroy()
   */
  public void destroy()
  {
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_ORGANISMO);
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_TMP_PATH_UPLOAD);
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_TMP_PATH_XML);
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_TMP_PATH_TRAMITES);
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_AFIRMA);
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_PLANTILLA);
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_CERTIFICADO);
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_REDIRAUTENTICACION);
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_INICIAREXPEDIENTECONVIRUS);
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_REGISTRARCONVIRUS);
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_INICIAREXPEDIENTE);
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_INICIAREXPEDIENTEERRORREGISTROESTADOERROR);
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_SUBSANACIONSINNUMEROREGISTROINICIAL_CODIGO_OFICINA);
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_SUBSANACIONSINNUMEROREGISTROINICIAL_FIRMAR_SOLICITUD);
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_INICIAREXPEDIENTEERROR_ENVIAR_EMAIL_ORIGEN);
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_INICIAREXPEDIENTEERROR_ENVIAR_EMAIL_DESTINO);
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_INICIAREXPEDIENTEERROR_ENVIAR_EMAIL_ASUNTO);
    
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

    m_servlet.getServletContext().setAttribute(Defs.PLUGIN_ORGANISMO, this.m_organismo);
    m_servlet.getServletContext().setAttribute(Defs.PLUGIN_TMP_PATH_UPLOAD, this.m_tmpUploadPath);
    m_servlet.getServletContext().setAttribute(Defs.PLUGIN_TMP_PATH_XML, this.m_tmpXmlPath);
    m_servlet.getServletContext().setAttribute(Defs.PLUGIN_TMP_PATH_TRAMITES, this.m_tramitesPath);
    m_servlet.getServletContext().setAttribute(Defs.PLUGIN_AFIRMA, this.m_aFirma);
    m_servlet.getServletContext().setAttribute(Defs.PLUGIN_PLANTILLA, this.m_plantilla);
    m_servlet.getServletContext().setAttribute(Defs.PLUGIN_CERTIFICADO, this.m_certificado);
    m_servlet.getServletContext().setAttribute(Defs.PLUGIN_REDIRAUTENTICACION, this.m_redirAutenticacion);
    m_servlet.getServletContext().setAttribute(Defs.PLUGIN_INICIAREXPEDIENTECONVIRUS, this.m_iniciarExpedienteConVirus);
    m_servlet.getServletContext().setAttribute(Defs.PLUGIN_REGISTRARCONVIRUS, this.m_registrarConVirus);
    m_servlet.getServletContext().setAttribute(Defs.PLUGIN_INICIAREXPEDIENTE, this.m_iniciarExpediente);
    m_servlet.getServletContext().setAttribute(Defs.PLUGIN_INICIAREXPEDIENTEERRORREGISTROESTADOERROR, this.m_iniciarExpedienteErrorRegistroEstadoError);
    m_servlet.getServletContext().setAttribute(Defs.PLUGIN_SUBSANACIONSINNUMEROREGISTROINICIAL_CODIGO_OFICINA, this.m_subsanacionSinNumeroRegistroInicialCodigoOficina);
    m_servlet.getServletContext().setAttribute(Defs.PLUGIN_SUBSANACIONSINNUMEROREGISTROINICIAL_FIRMAR_SOLICITUD, this.m_subsanacionSinNumeroRegistroInicialFirmarSolicitud);
    m_servlet.getServletContext().setAttribute(Defs.PLUGIN_INICIAREXPEDIENTEERROR_ENVIAR_EMAIL_ORIGEN, this.m_iniciarExpedienteErrorEnviarEmailOrigen);
    m_servlet.getServletContext().setAttribute(Defs.PLUGIN_INICIAREXPEDIENTEERROR_ENVIAR_EMAIL_DESTINO, this.m_iniciarExpedienteErrorEnviarEmailDestino);
    m_servlet.getServletContext().setAttribute(Defs.PLUGIN_INICIAREXPEDIENTEERROR_ENVIAR_EMAIL_ASUNTO, this.m_iniciarExpedienteErrorEnviarEmailAsunto);
    
    if (logger.isDebugEnabled())
    {
      logger.debug("Defs.PLUGIN_ORGANISMO: " + this.m_organismo);
      logger.debug("Defs.PLUGIN_TMP_PATH_UPLOAD: " + this.m_tmpUploadPath);
      logger.debug("Defs.PLUGIN_TMP_PATH_XML: " + this.m_tmpXmlPath);
      logger.debug("Defs.PLUGIN_TMP_PATH_TRAMITES: " + this.m_tramitesPath);
      logger.debug("Defs.PLUGIN_AFIRMA: " + this.m_aFirma);
      logger.debug("Defs.PLUGIN_PLANTILLA: " + this.m_plantilla);
      logger.debug("Defs.PLUGIN_CERTIFICADO: " + this.m_certificado);
      logger.debug("Defs.PLUGIN_REDIRAUTENTICACION: " + this.m_redirAutenticacion);
      logger.debug("Defs.PLUGIN_INICIAREXPEDIENTECONVIRUS: " + this.m_iniciarExpedienteConVirus);
      logger.debug("Defs.PLUGIN_REGISTRARCONVIRUS: " + this.m_registrarConVirus);
      logger.debug("Defs.PLUGIN_INICIAREXPEDIENTE: " + this.m_iniciarExpediente);
      logger.debug("Defs.PLUGIN_INICIAREXPEDIENTEERRORREGISTROESTADOERROR: " + this.m_iniciarExpedienteErrorRegistroEstadoError);
      logger.debug("Defs.PLUGIN_SUBSANACIONSINNUMEROREGISTROINICIAL_CODIGO_OFICINA: " + this.m_subsanacionSinNumeroRegistroInicialCodigoOficina);
      logger.debug("Defs.PLUGIN_SUBSANACIONSINNUMEROREGISTROINICIAL_FIRMAR_SOLICITUD: " + this.m_subsanacionSinNumeroRegistroInicialFirmarSolicitud);
      logger.debug("Defs.PLUGIN_INICIAREXPEDIENTEERROR_ENVIAR_EMAIL_ORIGEN: "+this.m_iniciarExpedienteErrorEnviarEmailOrigen);
      logger.debug("Defs.PLUGIN_INICIAREXPEDIENTEERROR_ENVIAR_EMAIL_DESTINO: "+this.m_iniciarExpedienteErrorEnviarEmailDestino);
      logger.debug("Defs.PLUGIN_INICIAREXPEDIENTEERROR_ENVIAR_EMAIL_ASUNTO: "+this.m_iniciarExpedienteErrorEnviarEmailAsunto);
    }
  }

  protected void populate()
  {
	  ConfigLoader configloader = new ConfigLoader();
	  this.m_organismo=configloader.getOrganismoValue();
	  this.m_tmpUploadPath=configloader.getTmpUploadPathValue();
	  this.m_tmpXmlPath=configloader.getTmpXmlPathValue();
	  this.m_tramitesPath=configloader.getTramitesPathValue();
	  this.m_aFirma=configloader.getAFirmaValue();
	  this.m_plantilla=configloader.getPlantillaValue();
	  this.m_certificado=configloader.getCertificadoValue();
	  this.m_redirAutenticacion=configloader.getRedirAutenticacionValue();
	  this.m_iniciarExpedienteConVirus=configloader.getIniciarExpedienteConVirusValue();
	  this.m_registrarConVirus=configloader.getRegistrarConVirusValue();
	  this.m_iniciarExpediente=configloader.getIniciarExpedienteValue();
	  this.m_iniciarExpedienteErrorRegistroEstadoError=configloader.getIniciarExpedienteErrorRegistroEstadoErrorValue();
	  this.m_subsanacionSinNumeroRegistroInicialCodigoOficina=configloader.getSubsanacionSinNumeroRegistroInicialCodigoOficinaValue();
	  this.m_subsanacionSinNumeroRegistroInicialFirmarSolicitud=configloader.getSubsanacionSinNumeroRegistroInicialFirmarSolicitudValue();
	  this.m_iniciarExpedienteErrorEnviarEmailOrigen=configloader.getIniciarExpendienteErrorEnviarEmailOrigen();
	  this.m_iniciarExpedienteErrorEnviarEmailDestino=configloader.getIniciarExpendienteErrorEnviarEmailDestino();
	  this.m_iniciarExpedienteErrorEnviarEmailAsunto=configloader.getIniciarExpendienteErrorEnviarEmailAsunto();
  }
}