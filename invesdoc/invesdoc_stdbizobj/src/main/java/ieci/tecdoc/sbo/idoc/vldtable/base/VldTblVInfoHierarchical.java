/*
 * Created on 12-sep-2005
 *
 */
package ieci.tecdoc.sbo.idoc.vldtable.base;


/**
 * @author egonzalez
 */
public class VldTblVInfoHierarchical extends VldTblVInfo
{
   
   private int m_parentVTblId = -1;
   private int m_childVTblId  = -1;
   
   public VldTblVInfoHierarchical(int parentVTblId, int childVTblId)
   {
      super (null, null, -1, null, null, null);
      m_parentVTblId = parentVTblId;
      m_childVTblId = childVTblId;
   }
   
   public int getChildVTblId()
   {
      return m_childVTblId;
   }
   
   public void setChildVTblId(int childVTblId)
   {
      this.m_childVTblId = childVTblId;
   }
   
   public int getParentVTblId()
   {
      return m_parentVTblId;
   }
   
   public void setParentVTblId(int parentVTblId)
   {
      this.m_parentVTblId = parentVTblId;
   }
   
}
