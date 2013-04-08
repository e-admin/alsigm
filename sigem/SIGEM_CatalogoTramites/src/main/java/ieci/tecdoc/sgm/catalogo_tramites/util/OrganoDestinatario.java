package ieci.tecdoc.sgm.catalogo_tramites.util;

/**
 * Interfaz con el comportamiento de un órgano destinatario
 *
 */
public interface OrganoDestinatario
{
  public abstract void setId(String id);
  public abstract void setDescription(String description);
  public abstract String getId();
  public abstract String getDescription();
  
  /**
   * Recoge los valores de la instancia en una cadena xml
   * 
   * @param header Si se incluye la cabecera
   * @return los datos en formato xml
   */
  public abstract String toXML(boolean header);

  /**
   * Devuelve los valores de la instancia en una cadena de caracteres.
   */
  public abstract String toString();
}
