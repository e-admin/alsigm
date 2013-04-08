package ieci.tecdoc.sgm.geolocalizacion.plugin;

import ieci.tecdoc.sgm.geolocalizacion.utils.Defs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

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
  private List m_tipoVias = null;
  
  private int m_zoomMapa = 0;
  
  private int m_capaMapa = 0;
  
  private int m_anchoMapa = 0;
  
  private int m_altoMapa = 0;
  
  /*
   * Métodos 
   */
  public List getTipoVias() {
	return m_tipoVias;
  }

  public void setTipoVias(List tipoVias) {
	this.m_tipoVias = tipoVias;
  }
  
  public int getAltoMapa() {
	 return m_altoMapa;
  }

  public void setAltoMapa(int altoMapa) {
	m_altoMapa = altoMapa;
  }

  public int getAnchoMapa() {
	return m_anchoMapa;
  }

  public void setAnchoMapa(int anchoMapa) {
	m_anchoMapa = anchoMapa;
  }

  public int getCapaMapa() {
	return m_capaMapa;
  }

  public void setCapaMapa(int capaMapa) {
	m_capaMapa = capaMapa;
  }

  public int getZoomMapa() {
	return m_zoomMapa;
  }

  public void setZoomMapa(int zoomMapa) {
	m_zoomMapa = zoomMapa;
  }

/*
   * (non-Javadoc)
   * 
   * @see org.apache.struts.action.PlugIn#destroy()
   */
  public void destroy()
  {
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_LISTADO_TIPO_VIAS);
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_ZOOM_MAPA);
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_CAPA_MAPA);
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_ALTO_MAPA);
    m_servlet.getServletContext().removeAttribute(Defs.PLUGIN_ANCHO_MAPA);
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

    this.m_servlet = servlet;
    
    m_tipoVias = new ArrayList();
    
    ResourceBundle bundle;
    try {
    	bundle = ResourceBundle.getBundle ("ieci.tecdoc.sgm.geolocalizacion.resources.vias");
    	HashMap hmVias = new HashMap();
    	ArrayList ids = new ArrayList();
    	
        for (Enumeration en = bundle.getKeys(); en.hasMoreElements(); ){
        	String cadena = (String)en.nextElement();
        	String identificador = getIdentificador(cadena);
        	String tipoDato = getTipoDato(cadena);
        	if (identificador != null && tipoDato != null) {
        		String valor = bundle.getString(cadena);
        		String[] datos;
        		if (hmVias.containsKey(identificador))
        			datos = (String[])hmVias.get(identificador);
        		else {
        			ids.add(identificador);
        			datos = new String[2];
        		}
        		if (Defs.PLUGIN_CODIGO.equalsIgnoreCase(tipoDato))
        			datos[0] = valor;
        		else datos[1] = valor;
       			hmVias.put(identificador, datos);
        	}
        }
        
        Collections.sort(ids);
        
        for(int i=0; i<ids.size(); i++){
        	this.m_tipoVias.add(hmVias.get((String)ids.get(i)));
        }
    
        m_servlet.getServletContext().setAttribute(Defs.PLUGIN_LISTADO_TIPO_VIAS, this.m_tipoVias);
        m_servlet.getServletContext().setAttribute(Defs.PLUGIN_ZOOM_MAPA, new Integer(this.m_zoomMapa));
        m_servlet.getServletContext().setAttribute(Defs.PLUGIN_CAPA_MAPA, new Integer(this.m_capaMapa));
        m_servlet.getServletContext().setAttribute(Defs.PLUGIN_ALTO_MAPA, new Integer(this.m_altoMapa));
        m_servlet.getServletContext().setAttribute(Defs.PLUGIN_ANCHO_MAPA, new Integer(this.m_anchoMapa));
    } catch (Exception e) {
    	logger.error("Error cargando listado de vías para la Geolocalización", e.getCause());
    }
    
    if (logger.isDebugEnabled())
    {
      logger.debug("Defs.PLUGIN_LISTADO_TIPO_VIAS: " + this.m_tipoVias);
      logger.debug("Defs.PLUGIN_ZOOM_MAPA: " + this.m_zoomMapa);
      logger.debug("Defs.PLUGIN_CAPA_MAPA: " + this.m_capaMapa);
      logger.debug("Defs.PLUGIN_ALTO_MAPA: " + this.m_altoMapa);
      logger.debug("Defs.PLUGIN_ANCHO_MAPA: " + this.m_anchoMapa);
    }
  }
  
  private static String getIdentificador (String cadena){
	  int index = cadena.indexOf(".");
	  if (index == -1)
		  return null;
	  return cadena.substring(0, index);
  }
  
  private static String getTipoDato(String cadena){
	  int index = cadena.indexOf(".");
	  if (index == -1)
		  return null;
	  return cadena.substring(index+1, cadena.length());
  }
}