
package ieci.tecdoc.sbo.acs.base;

import java.io.Serializable;

import ieci.tecdoc.sbo.util.types.SboType;

public final class AcsAccessTokenDept implements Serializable
{
   
   private int   m_id;
   private int   m_genPerms;
   
   public AcsAccessTokenDept()
   {
      m_id       = SboType.NULL_ID;
      m_genPerms = AcsPermission.NONE;
   }
   
   public AcsAccessTokenDept(int id)
   {
      m_id       = id;
      m_genPerms = AcsPermission.NONE;
   }
   
   public AcsAccessTokenDept(int id, int genPerms)
   {
      m_id       = id;
      m_genPerms = genPerms;
   }  
   
   public int getId()
   {
      return m_id;
   }
   
   public int getGenPerms()
   {
      return m_genPerms;
   }
   
   public boolean isNull()
   {
      if (m_id == SboType.NULL_ID)
         return true;
      else      
         return false;
   }
      
   /**
    * toString methode: creates a String representation of the object
    * @return the String representation
    */
   public String toString() {
      
      StringBuffer buffer = new StringBuffer();
      
      buffer.append("AcsAccessTokenDept[");
      buffer.append("m_id = ").append(m_id);
      buffer.append(", m_genPerms = ").append(m_genPerms);
      buffer.append("]");
      
      return buffer.toString();
   }
} // class
