
package ieci.tecdoc.core.xml.core;

/** Representa una colección de elementos de un documento xml. */ 

public final class XmlElements
{
   
   private XmlElement[] m_elems;
   
   /** Construye un objeto de la clase.
   * @param elems Array de elementos. */
   
   XmlElements(XmlElement[] elems)
   {
      m_elems = elems;
   }
   
   /** Devuelve el número de elementos de la colección.
   * @return El número mencionado. */
   
   public int getCount()
   {     
      return m_elems.length;
   }
   
   /** Devuelve el elemento especificado.
   * @param index Indice del elemento (1, 2, ...).
   * @return El elemento mencionado. */
   
   public XmlElement getItem(int index)
   {     
      return m_elems[index - 1];
   }
   
} // class XmlElements
