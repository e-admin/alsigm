
package ieci.tecdoc.sbo.acs.base;

public final class AcsObjTokenHdr
{
   
   private int     m_id;
   private int     m_ownerType;
   private int     m_ownerId;
       
   public AcsObjTokenHdr(int id, int ownerType, int ownerId)
   {
      m_id         = id;
      m_ownerType  = ownerType;
      m_ownerId    = ownerId;
   }  
   
   public int getId()
   {
      return m_id;
   }
   
   public int getOwnerType()
   {
      return m_ownerType;
   }
   
   public int getOwnerId()
   {
      return m_ownerId;
   }
   
} // class
