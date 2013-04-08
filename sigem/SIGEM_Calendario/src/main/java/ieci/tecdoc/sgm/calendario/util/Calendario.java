package ieci.tecdoc.sgm.calendario.util;

/**
 * Clase que implementa la interfaz Calendario
 * @author x53492no
 *
 */
public interface Calendario
{  
  public abstract String getDias();
  public abstract void setDias(String dias);
  public abstract String getHoraFin();
  public abstract void setHoraFin(String horaFin);
  public abstract String getHoraInicio();
  public abstract void setHoraInicio(String horaInicio);
  public abstract String getMinutoFin();
  public abstract void setMinutoFin(String minutoFin);
  public abstract String getMinutoInicio();
  public abstract void setMinutoInicio(String minutoInicio);
 
  /**
   * Recoge los valores de la instancia en una cadena xml
   * 
   * @param header
   *           Si se incluye la cabecera
   * @return los datos en formato xml
   */
  public abstract String toXML(boolean header);

  /**
   * Devuelve los valores de la instancia en una cadena de caracteres.
   */
  public abstract String toString();
}
