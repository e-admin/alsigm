
package ieci.tecdoc.sbo.idoc.archive.base;

import java.io.Serializable;

import ieci.tecdoc.sbo.util.types.SboType;

public final class ArchiveTokenFld implements Serializable
{
   
   private int     m_id;
   private String  m_name;
   private int     m_type;
   private int     m_len;
   private boolean m_isNullable;
   private String  m_colName;
   private boolean m_isDoc;
   private boolean m_isMult;
   private String  m_remarks;
   
   public ArchiveTokenFld()
   {
      m_id         = SboType.NULL_ID;
      m_name       = null;     
      m_type       = 0;
      m_len        = 0;
      m_isNullable = true;   
      m_colName    = null;
      m_isDoc      = false;
      m_isMult     = false;  
      m_remarks    = null;
   }
   
   public ArchiveTokenFld(int id, String name, int type, int len, boolean isNullable,
                          String colName, boolean isDoc, boolean isMult, String remarks)
   {
      m_id         = id;
      m_name       = name;
      m_type       = type;
      m_len        = len;
      m_isNullable = isNullable;
      m_colName    = colName;
      m_isDoc      = isDoc;
      m_isMult     = isMult;
      m_remarks    = remarks;
   }
   
   public int getId()
   {
      return m_id;
   }
   
   public String getName()
   {
      return m_name;
   }
   
   public String getRemarks()
   {
      return m_remarks;
   }
   
   public String getColName()
   {
      return m_colName;
   }
   
   public int getType()
   {
      return m_type;
   }
   
   public int getLen()
   {
      return m_len;
   }
   
   public boolean isNullable()
   {
      return m_isNullable;
   }
   
   public boolean isDoc()
   {
      return m_isDoc;
   }
   
   public boolean isMult()
   {
      return m_isMult;
   }
   
   public boolean isRel()
   {
      boolean isRel = false;
      
      if (!isMult() && 
          (getType() != ArchiveFldType.LONG_TEXT) )
         isRel = true;
      
      return isRel;
   }
   
   public boolean isExt()
   {
      boolean isExt = false;
      
      if (getType() == ArchiveFldType.LONG_TEXT )
         isExt = true;
      
      return isExt;
   }
   
   /**
    * toString methode: creates a String representation of the object
    * @return the String representation
    */
   public String toString() {
    
      StringBuffer buffer = new StringBuffer();
      
      buffer.append("ArchiveTokenFld[");
      buffer.append("m_id = ").append(m_id);
      buffer.append(", m_name = ").append(m_name);
      buffer.append(", m_type = ").append(m_type);
      buffer.append(", m_len = ").append(m_len);
      buffer.append(", m_isNullable = ").append(m_isNullable);
      buffer.append(", m_colName = ").append(m_colName);
      buffer.append(", m_isDoc = ").append(m_isDoc);
      buffer.append(", m_isMult = ").append(m_isMult);
      buffer.append("]");
      
      return buffer.toString();
   }
} // class
