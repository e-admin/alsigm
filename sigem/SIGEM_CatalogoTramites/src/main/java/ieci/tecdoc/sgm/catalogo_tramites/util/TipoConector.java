package ieci.tecdoc.sgm.catalogo_tramites.util;

/**
 * Interfaz de comportamiento de los tipos de conectores
 *
 */
public interface TipoConector
{
  public abstract void setId(int id);
  public abstract void setDescription(String description);
  public abstract int getId();
  public abstract String getDescription();
  
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
