package ieci.tecdoc.sgm.core.services.calendario;

/**
 * Clase que implementa la interfaz Calendario
 * @author x53492no
 *
 */
public class Calendario
{
	/**
	 * Constructor de la clase Calendario
	 *
	 */
  public Calendario()
  {
     this.dias = null;
     this.horaInicio = null;
     this.minutoInicio = null;
     this.horaFin = null;
     this.minutoFin = null;
  }
  
  public String[] getDias() {
	return dias;
  }
	
  public void setDias(String[] dias) {
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
  
  
  protected String[] dias;
  protected String horaInicio;
  protected String minutoInicio;
  protected String horaFin;
  protected String minutoFin;
  
  public static final int TIPO_LABORABLE = 0;
  public static final int TIPO_NO_LABORABLE = 1;
}
