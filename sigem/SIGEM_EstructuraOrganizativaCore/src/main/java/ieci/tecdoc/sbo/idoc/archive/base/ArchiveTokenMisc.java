
package ieci.tecdoc.sbo.idoc.archive.base;

import java.io.Serializable;

public final class ArchiveTokenMisc implements Serializable
{
   
   private String m_fdrName;
   private int    m_volListType;
   private int    m_volListId;
   
   public ArchiveTokenMisc(String fdrName, int volListType, int volListId)
   {  
      m_fdrName     = fdrName;
      m_volListType = volListType;
      m_volListId   = volListId;  
   }
   
   public int getVolListId()
   {
      return m_volListId;
   }
   
   public int getVolListType()
   {
      return m_volListType;
   }
      
   /**
    * toString methode: creates a String representation of the object
    * @return the String representation
    * @author info.vancauwenberge.tostring plugin

    */
   public String toString() 
   {
      StringBuffer buffer = new StringBuffer();
      buffer.append("ArchiveTokenMisc[");

      buffer.append("m_fdrName = ").append(m_fdrName);
      buffer.append(", m_volListType = ").append(m_volListType);
      buffer.append(", m_volListId = ").append(m_volListId);

      buffer.append("]");
      return buffer.toString();
   }
   
} // class
