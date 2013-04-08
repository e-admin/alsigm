package ieci.tecdoc.sbo.acs.base;

import ieci.tecdoc.sbo.util.types.SboType;

public class AcsAccessTokenProduct
{
   private AcsAccessToken m_acsToken;
   private int            m_productId;
   private boolean        m_hasAccess;
   
   public AcsAccessTokenProduct()
   {
      m_acsToken  = null;
      m_productId = SboType.NULL_ID;
      m_hasAccess = false;
   }
   
   public void setAcsAccessToken(AcsAccessToken acsToken)
   {
      AcsAccessTokenGroups group;
      
      m_acsToken = new AcsAccessToken();
      m_acsToken.setUser(acsToken.getUserId(),acsToken.getUser().getProfile(),acsToken.getUser().getGenPerms());
      if (acsToken.getDeptId() != SboType.NULL_ID)
         m_acsToken.setDept(acsToken.getDeptId(),acsToken.getDept().getGenPerms());
      
      group = acsToken.getGroups();
      for (int i=0; i < group.count(); i++)
      {
         AcsAccessTokenGroup grp = group.get(i);
         m_acsToken.addGroup(grp.getId(),grp.getGenPerms());
      }
      
   }
   
   public void setProductId(int productId)
   {
      m_productId = productId;
   }
   
   public void setHasAccess(boolean has)
   {
      m_hasAccess = has;
   }
   
   public AcsAccessToken getAcsAccessToken()
   {
      return m_acsToken;   
   }
   
   public int getProductId()
   {
      return m_productId;
   }
   
   public boolean hasAccess()
   {
      return m_hasAccess;
   }
   
}
