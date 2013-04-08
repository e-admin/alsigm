package ieci.tecdoc.sgm.calendario.util;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

import java.io.Serializable;

/**
 * Clase que implementa la interfaz Conector
 * @author x53492no
 *
 */
public class CalendarioImpl implements Calendario, Serializable
{
	/**
	 * Constructor de la clase Calendario
	 *
	 */
  public CalendarioImpl()
  {
     this.dias = null;
     this.horaInicio = null;
     this.minutoInicio = null;
     this.horaFin = null;
     this.minutoFin = null;
  }
  
  public String getDias() {
	return dias;
  }
	
  public void setDias(String dias) {
	this.dias = dias;
  }  
	
  public String getHoraFin() {
	return horaFin;
  }
	
  public void setHoraFin(String horaFin) {
	this.horaFin = horaFin;
  }
	
  public String getHoraInicio() {
	return horaInicio;
  }
	
  public void setHoraInicio(String horaInicio) {
	this.horaInicio = horaInicio;
  }
	
  public String getMinutoFin() {
	return minutoFin;
  }
	
  public void setMinutoFin(String minutoFin) {
	this.minutoFin = minutoFin;
  }
	
  public String getMinutoInicio() {
	return minutoInicio;
  }
	
  public void setMinutoInicio(String minutoInicio) {
	this.minutoInicio = minutoInicio;
  }
  
  /**
   * Devuelve una cadena XMl con la información asociada al dia del calendario.
   * @param header Establece si el XML debe llevar cabecera.
   * @return String Cadena XML.
   */   
  public String toXML(boolean header) 
  {
     XmlTextBuilder bdr;
     String tagName = "Dia_Calendario";
     
     bdr = new XmlTextBuilder();
     if (header)
        bdr.setStandardHeader();

     bdr.addOpeningTag(tagName);
     
     bdr.addSimpleElement("Dias_No_Laborables", dias);
     bdr.addSimpleElement("Fecha_Completa_Inicio", horaInicio + ":" + minutoInicio);
     bdr.addSimpleElement("Fecha_Completa_Fin", horaFin + ":" + minutoFin);

     bdr.addClosingTag(tagName);
     
     return bdr.getText();
  }

  /**
   * Devuelve una cadena con la información asociada a un calendario.
   * @return String Cadena XML con la información del documento.
   */
  public String toString()
  {
     return toXML(false);
  }
  
  protected String dias;
  protected String horaInicio;
  protected String minutoInicio;
  protected String horaFin;
  protected String minutoFin;

}
