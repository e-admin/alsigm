
package ieci.tecdoc.sbo.idoc.archive.base;

import java.io.Serializable;

import ieci.tecdoc.sbo.util.types.SboType;
import ieci.tecdoc.sbo.acs.base.AcsAccessType;

public final class ArchiveTokenArchHdr implements Serializable
{
   
   private int    m_id;
   private String m_name;
   private String m_tblPrefix;
   private int    m_flags;
   private int    m_accessType;
   private int    m_acsId;
   
   public ArchiveTokenArchHdr()
   {
      m_id         = SboType.NULL_ID;
      m_name       = null;
      m_tblPrefix  = null;
      m_flags      = ArchiveFlag.NONE;
      m_accessType = SboType.NULL_ID;
      m_acsId      = SboType.NULL_ID;      
   }
   
   public ArchiveTokenArchHdr(int id, String name, String tblPrefix, int flags,
                              int accessType, int acsId)
   {
      m_id         = id;
      m_name       = name;
      m_tblPrefix  = tblPrefix;
      m_flags      = flags;
      m_accessType = accessType;
      m_acsId      = acsId;
   }
   
   public int getId()
   {
      return m_id;
   }
   
   public String getName()
   {
      return m_name;
   }
   
   public String getTblPrefix()
   {
      return m_tblPrefix;
   }
   
   public int getFlags()
   {
      return m_flags;
   }
   
   public boolean isPublic()
   {
      if (m_accessType == AcsAccessType.ACS_PUBLIC)
         return true;
      else
         return false;
   }
   
   public int getAccessType()
   {
      return m_accessType;
   }
   
   public int getAcsId()
   {
      return m_acsId;
   }
   
   /**
    * toString methode: creates a String representation of the object
    * @return the String representation
    */
   public String toString() {
    
      StringBuffer buffer = new StringBuffer();
      
      buffer.append("ArchiveTokenArchHdr[");
      buffer.append("m_id = ").append(m_id);
      buffer.append(", m_name = ").append(m_name);
      buffer.append(", m_tblPrefix = ").append(m_tblPrefix);
      buffer.append(", m_flags = ").append(m_flags);
      buffer.append(", m_accessType = ").append(m_accessType);
      buffer.append(", m_acsId = ").append(m_acsId);
      buffer.append("]");
      
      return buffer.toString();
   }
} // class
