package ieci.tecdoc.sgm.calendario.util;

import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;

import java.io.Serializable;

/**
 * Clase que implementa la interfaz Día del Calendario
 * @author x53492no
 *
 */
public class CalendarioDiaImpl implements CalendarioDia, Serializable
{
	/**
	 * Constructor de la clase CalendarioDia
	 *
	 */
  public CalendarioDiaImpl()
  {
	 this.id = -1;
     this.fecha = null;
     this.descripcion = null;
     this.fijo = -1;
  }
  

  public int getId() {
	return id;
  }
	
  public void setId(int id) {
	this.id = id;
  }
	  
  public String getDescripcion() {
	return descripcion;
  }
	
  public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
  }

  public String getFecha() {
	return fecha;
  }
	
  public void setFecha(String fecha) {
	this.fecha = fecha;
  }
	
  public int getFijo() {
	return fijo;
  }
	
  public void setFijo(int fijo) {
	this.fijo = fijo;
  }
  
  /**
   * Devuelve una cadena XMl con la información asociada al dia del calendario.
   * @param header Establece si el XML debe llevar cabecera.
   * @return String Cadena XML.
   */   
  public String toXML(boolean header) 
  {
     XmlTextBuilder bdr;
     String tagName = "Dia_Festivo";
     
     bdr = new XmlTextBuilder();
     if (header)
        bdr.setStandardHeader();

     bdr.addOpeningTag(tagName);
     
     bdr.addSimpleElement("Id", ""+id);
     bdr.addSimpleElement("Fecha", fecha.toString());
     bdr.addSimpleElement("Descripcion", descripcion);
     bdr.addSimpleElement("Fijo", ""+fijo);

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
    
  protected int id;
  protected String fecha;
  protected String descripcion;
  protected int fijo;
}
