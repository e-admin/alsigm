package ieci.tecdoc.sbo.idoc.vldtable.base;

/**
 * User: RobertoBas
 * Date: 18-nov-2004
 * Time: 15:52:48
 */
public class VldTblSearchQuery
{
   private VldTblSearchCondition m_searchCondition;

   public String getSqlCondition(int dbEngine)throws Exception
   {

      StringBuffer            tbdr;
      int                     i, numConds;
      VldTblSearchCondition   item = null;

      tbdr = new StringBuffer();

         item = (VldTblSearchCondition)m_searchCondition;

         tbdr.append(item.getSqlCondition(dbEngine));


      return tbdr.toString();

   }


   public String getSqlQual(int dbEngine) throws Exception
   {

      String qual = "";
      String where;
      String orderBy;

      where   = getSqlCondition(dbEngine);

      if (!where.equals(""))
         qual = qual + " WHERE " + where;

      
      return qual;

   }
   public VldTblSearchCondition getSearchCondition()
   {
      return m_searchCondition;
   }

   public void setSearchCondition(VldTblSearchCondition searchCondition)
   {
      this.m_searchCondition = searchCondition;
   }
   public String getOrderBy (int dbEngine)
   {
     return getSearchCondition().m_colName;
   }
}
