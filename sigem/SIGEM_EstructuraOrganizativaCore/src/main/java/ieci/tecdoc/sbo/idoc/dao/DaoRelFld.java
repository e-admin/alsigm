
package ieci.tecdoc.sbo.idoc.dao;

import java.io.Serializable;

public final class DaoRelFld implements Serializable
{
   
   private int     m_id;
   private String  m_name;   
   private String  m_colName;   
   
   public DaoRelFld(int id, String name, String colName)
   {
      m_id         = id;
      m_name       = name;     
      m_colName    = colName;      
   } 
   
   public int getId()
   {
      return m_id;
   }
   
   public String getName()
   {
      return m_name;
   }
   
   public String getColName()
   {
      return m_colName;
   }
   
   /**
    * toString methode: creates a String representation of the object
    * @return the String representation
    */
   public String toString() {
    
      StringBuffer buffer = new StringBuffer();
      
      buffer.append("DaoRelFld[");
      buffer.append("m_id = ").append(m_id);
      buffer.append(", m_name = ").append(m_name);      
      buffer.append(", m_colName = ").append(m_colName);     
      buffer.append("]");
      
      return buffer.toString();
   }
} // class
