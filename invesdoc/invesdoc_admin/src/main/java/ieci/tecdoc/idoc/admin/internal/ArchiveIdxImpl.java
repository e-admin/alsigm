
package ieci.tecdoc.idoc.admin.internal;

/**
 * Proporciona toda la funcionalidad necesaria para manejar índice. 
 */

import ieci.tecdoc.core.xml.lite.XmlTextBuilder;
import ieci.tecdoc.idoc.admin.api.archive.ArchiveIdx;
import ieci.tecdoc.sbo.util.types.SboType;

import java.util.ArrayList;

public class ArchiveIdxImpl implements ArchiveIdx
{
   private int        m_id;
   private String     m_name;   
   private boolean    m_isUnique;
   private ArrayList  m_fldsId;
   
   public ArchiveIdxImpl()
   {
      m_id         = SboType.NULL_ID;
      m_name       = null;
      m_isUnique   = false;   
      m_fldsId     = new ArrayList();
      
   }
   
   protected ArchiveIdxImpl(int id, String name, boolean isUnique, ArrayList fldsId)
   {
      m_id       = id;
      m_name     = name;
      m_isUnique = isUnique;
      m_fldsId   = fldsId;
   }
   
   /**
    * Obtiene el identificador del índice
    * 
    * @return El identificador
    */
   public int getId()
   {
      return (m_id);
   }
   
   /**
    * Obtiene el nombre del índice
    * 
    * @return El nombre
    */
   public String getName()
   {
      return (m_name);
   }
   
   /**
    * Obtiene si el índice es único ó no
    * 
    * @return (true/false)
    */
   public boolean isUnique()
   {
      return (m_isUnique);
   }
   
   /**
    * Obtiene una lista de enteros (Integer) con los identificadores
    * de los campos que componen el índice
    * 
    * @return Lista de identificadores de campos
    */
   public ArrayList getFldsId()
   {
      return (m_fldsId);
   }
   
   /**
    * Establece una lista de enteros (Integer) con los identificadores
    * de los campos que componene el índice
    * 
    * @param idsFld Lista de identificadores de campos
    */
   public void setFldsId(ArrayList fldsId)
   {
      m_fldsId.clear();
      
      for (int i = 0; i < fldsId.size(); i++)
      {
         m_fldsId.add(fldsId.get(i));
      }
   }
   
   /**
    * Obtiene la información del índice en formato XML.
    * @param header true / false
    * @return La información mencionada.
    */
   public String toXML(boolean header) 
   {
      XmlTextBuilder bdr;
      String tagName = "Index";
      String fields = "";
      
      bdr = new XmlTextBuilder();
      if (header)
         bdr.setStandardHeader();

      bdr.addOpeningTag(tagName);
      
      for (int i = 0; i < m_fldsId.size(); i++)
      {
         if (i > 0)
            fields = fields + ",";
         fields = fields + ((Integer)m_fldsId.get(i)).toString();
      }
      
      bdr.addSimpleElement("Id", Integer.toString(m_id));
      bdr.addSimpleElement("Name", m_name);      
      bdr.addSimpleElement("Unique", Boolean.toString(m_isUnique));
      bdr.addSimpleElement("Fields", fields);
      
      bdr.addClosingTag(tagName);
      
      return bdr.getText();
   }

   /**
    * Obtiene la información del índice en formato XML.
    *
    * @return La información mencionada.
    */
   public String toXML() 
   {
      return toXML(true);
   }
   
   /**
    * Muestra una representación de los valores de la clase en formato XML.
    *  
    * @return La representación mencionada.
    */
   public String toString()
   {
      return toXML(false);
   }
   
}

