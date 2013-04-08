
package ieci.tecdoc.sbo.idoc.archive.base;

import java.io.Serializable;

import ieci.tecdoc.sbo.util.types.SboType;

public final class ArchiveTokenDivider implements Serializable
{
   
   private int     m_id;
   private String  m_name;
   private int     m_parentId;
   private int     m_type;   
   
   public ArchiveTokenDivider()
   {
      m_id         = SboType.NULL_ID;
      m_name       = null; 
      m_parentId   = SboType.NULL_ID;   
      m_type       = ArchiveDividerType.NORMAL;     
   }
   
   public ArchiveTokenDivider(int id, String name, int parentId, int type)
   {
      m_id         = id;
      m_name       = name;
      m_parentId   = parentId;
      m_type       = type;      
   }
   
   public int getId()
   {
      return m_id;
   }
   
   public String getName()
   {
      return m_name;
   }  
   
   public int getType()
   {
      return m_type;
   }
   
   public int getParentId()
   {
      return m_parentId;
   }
   
} // class
