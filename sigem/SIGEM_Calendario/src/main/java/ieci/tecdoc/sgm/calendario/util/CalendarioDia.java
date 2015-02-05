package ieci.tecdoc.sgm.calendario.util;


/**
 * Clase que implementa la interfaz Día del Calendario
 * @author x53492no
 *
 */
public interface CalendarioDia
{
	public abstract int getId();
	public abstract void setId(int id);
	public abstract String getDescripcion();
	public abstract void setDescripcion(String descripcion);
	public abstract String getFecha();
	public abstract void setFecha(String fecha);
	public abstract int getFijo();
	public abstract void setFijo(int fijo);

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
