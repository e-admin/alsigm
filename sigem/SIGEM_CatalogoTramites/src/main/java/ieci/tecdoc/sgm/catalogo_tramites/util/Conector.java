package ieci.tecdoc.sgm.catalogo_tramites.util;

/**
 * Interfaz de comportamiento de conectores
 *
 */
public interface Conector
{
  public abstract void setId(String id);
  public abstract void setDescription(String description);
  public abstract void setType(int type);
  public abstract String getId();
  public abstract String getDescription();
  public abstract int getType();
  
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
