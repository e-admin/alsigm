package ieci.tecdoc.sgm.core.services.calendario;

import java.util.Date;

/**
 * Clase que implementa la interfaz Día del Calendario
 * @author x53492no
 *
 */
public class CalendarioDia
{
	/**
	 * Constructor de la clase CalendarioDia
	 *
	 */
  public CalendarioDia()
  {
     this.fecha = null;
     this.descripcion = null;
     this.fijo = true;
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
	
  public boolean getFijo() {
	return fijo;
  }
	
  public void setFijo(boolean fijo) {
	this.fijo = fijo;
  }
  
  protected int id;
  protected String fecha;
  protected String descripcion;
  protected boolean fijo;
  
  public static final int TIPO_FIJO = 0;
  public static final int TIPO_VARIABLE = 1;
}
