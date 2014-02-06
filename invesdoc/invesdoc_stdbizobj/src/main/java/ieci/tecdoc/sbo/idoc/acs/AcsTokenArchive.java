
package ieci.tecdoc.sbo.idoc.acs;

import java.io.Serializable;

import ieci.tecdoc.core.collections.IeciTdLongIntegerArrayList;

//Permisos de acceso sobre un archivador para un usuario
public final class AcsTokenArchive implements Serializable
{
   
   private int                        m_acsId;
   private int                        m_mgrId;
   private IeciTdLongIntegerArrayList m_perms;
   private int                        m_crtrId;
   private int                        m_crtrType;
         
   public AcsTokenArchive(int acsId, int mgrId, IeciTdLongIntegerArrayList perms,
                          int crtrId, int crtrType)
   {
      m_acsId    = acsId;
      m_mgrId    = mgrId;
      m_perms    = perms;
      m_crtrId   = crtrId; 
      m_crtrType = crtrType;  
   }
   
   public int getMgrId()
   {
      return m_mgrId;
   }
   
   public int getAcsId()
   {
      return m_acsId;
   }
   
   public int getCrtrId()
   {
      return m_crtrId;
   }
   
   public int getCrtrType()
   {
      return m_crtrType;
   }
   
   public IeciTdLongIntegerArrayList getPerms()
   {
      return m_perms;
   }
   
   public boolean hasPerm(int perm)
   {
      boolean has = false;
      int     i, count;
      
      count = m_perms.count();
      
      for(i = 0; i < count; i++)
      {
         if (perm == m_perms.get(i))
         {
            has = true;
            break;
         }
      }
      
      return has;
   }
   
   /**
    * toString methode: creates a String representation of the object
    * @return the String representation
    */
   public String toString()
   {
      StringBuffer buffer   = new StringBuffer();
      int          numPerms = m_perms.count();
      
      buffer.append("AcsTokenArchive[");
      buffer.append("m_acsId = ").append(m_acsId);
      buffer.append(", m_mgrId = ").append(m_mgrId);
      buffer.append(", m_perms = [");
      
      for(int i = 0; i < numPerms; i++)
      {          
          buffer.append(" perm").append(i+1);
          buffer.append(" = ").append((m_perms.get(i)));
          
          if (i < (numPerms - 1) )
             buffer.append(",");

      }
      
      buffer.append("]");
     
      buffer.append(", m_crtrId = ").append(m_crtrId);
      buffer.append(", m_crtrType = ").append(m_crtrType);
      buffer.append("]");
      
      return buffer.toString();
   }
} // class
