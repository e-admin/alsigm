
package ieci.tecdoc.sbo.acs.std;

import ieci.tecdoc.sbo.util.types.SboType;

public final class AcsCrtrPermInfo
{
   
   private int     m_id;
   private int     m_type;
       
   public AcsCrtrPermInfo()
   {
      m_id    = SboType.NULL_ID;
      m_type  = SboType.NULL_ID;      
   }  
   
   public int getId()
   {
      return m_id;
   }
   
   public int getType()
   {
      return m_type;
   }
   
   public void setId(int id)
   {
      m_id = id;
   }
   
   public void setType(int type)
   {
      m_type = type;
   }
   
   
} // class
