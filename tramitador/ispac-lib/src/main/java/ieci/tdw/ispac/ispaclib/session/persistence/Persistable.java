/*
 * Persistable.java
 *
 * Created on June 4, 2004, 3:23 PM
 */

package ieci.tdw.ispac.ispaclib.session.persistence;

import ieci.tdw.ispac.api.errors.ISPACException;

public interface Persistable
{
  /**
   * Convierte el objeto en un string en formato xml
   * @return string en formato xml que representa al objeto
   */
  public String toXmlString ();
  
  /**
   * Carga el objeto a partir de un string en formato xml
   * @param string con la informacion a cargar en el objeto
   */
  public void loadObject (String xml) throws ISPACException;
  
}
