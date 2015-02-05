package ieci.tecdoc.sbo.idoc.vldtable.base;

import ieci.tecdoc.core.search.SearchConditionItem;
import ieci.tecdoc.core.search.SearchUtil;

import java.util.ArrayList;

/**
 * User: RobertoBas
 * Date: 19-nov-2004
 * Time: 17:10:13
 */
public class VldTblSearchCondition
{
   SearchConditionItem m_condition;
   String m_colName;
   String m_opr;
   ArrayList m_vals ;
   int m_dataType;
   
   public VldTblSearchCondition(VldTblToken vldToken, String colName, String opr, ArrayList vals)
                                throws Exception
   {
      init(vldToken, colName, opr, vals); 
   }
   
   public VldTblSearchCondition(VldTblToken vldToken, String colName, String opr, Object val) throws Exception
   {
      ArrayList vals = new ArrayList();
      vals.add(val); 
      
      init(vldToken, colName, opr, vals);
   }

   public String getSqlCondition(int dbEngine) throws Exception
   {
      return m_condition.getSqlCondition(dbEngine);

   }
   
   private void init(VldTblToken vldToken, String colName, String opr, ArrayList vals)
                throws Exception
   {
       this.m_colName  = colName;
       this.m_opr      = opr;
       this.m_vals     = vals;
       this.m_dataType = SearchUtil.convertDBTypeToSearchDataType(vldToken.getBInfo().getColType(m_colName) );
       
       m_condition = new SearchConditionItem(m_colName, m_opr, m_dataType, m_vals);
   }          

}
