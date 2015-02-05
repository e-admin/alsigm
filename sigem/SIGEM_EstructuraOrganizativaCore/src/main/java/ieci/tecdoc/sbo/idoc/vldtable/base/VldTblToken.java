package ieci.tecdoc.sbo.idoc.vldtable.base;

import java.io.Serializable;


/**
 * User: RobertoBas
 * Date: 18-nov-2004
 * Time: 13:45:27
 */
public class VldTblToken implements Serializable
{
   private VldTblTokenBInfo m_bInfo;
   private VldTblTokenVInfo m_vInfo;


   public VldTblToken(VldTblTokenBInfo bInfo, VldTblTokenVInfo vInfo)
   {
      this.m_bInfo = bInfo;
      this.m_vInfo = vInfo;
   }


   public VldTblToken(String VInfo,int type,String vldTblName, int vldTblId, int bTblId, String BInfo, String bTblName)
   {
      this.m_bInfo = new VldTblTokenBInfo( BInfo , bTblName) ;
      this.m_vInfo = new VldTblTokenVInfo( VInfo , type , vldTblName, vldTblId, bTblId ) ;
   }

   public VldTblTokenBInfo getBInfo()
   {
      return m_bInfo;
   }


   public void setBInfo(VldTblTokenBInfo BInfo)
   {
      this.m_bInfo = BInfo;
   }

   public VldTblTokenVInfo getVInfo()
   {
      return m_vInfo;
   }

   public void setVInfo(VldTblTokenVInfo VInfo)
   {
      this.m_vInfo = VInfo;
   }

}
