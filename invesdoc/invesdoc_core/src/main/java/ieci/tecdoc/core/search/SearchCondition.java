
package ieci.tecdoc.core.search;

import java.util.ArrayList;
import ieci.tecdoc.core.textutil.TextParserX;

public final class SearchCondition
{
      
   private ArrayList m_searchConditionItems;
   private ArrayList m_searchOrderItems;
   private String    m_tblName;  
  
   public SearchCondition(String tblName)
   {
      m_searchConditionItems = new ArrayList(); 
      m_searchOrderItems     = new ArrayList();
      m_tblName              = tblName;
   }
   
   public String getSqlWhere(int dbEngine)throws Exception
   {
      
      StringBuffer          tbdr;     
      int                   i, numConds;
      SearchConditionItem   item = null;
      
      tbdr = new StringBuffer();
      
      numConds = m_searchConditionItems.size();
      
      for(i = 0; i < numConds; i++)
      {
         item = (SearchConditionItem)m_searchConditionItems.get(i);
         
         tbdr.append(item.getSqlCondition(dbEngine));
           
         if (i < (numConds - 1))
            tbdr.append(" AND ");        
      }
       
      return tbdr.toString();
      
   }  
   
   public String getSqlOrderBy()throws Exception
   {
      
      StringBuffer          tbdr;     
      int                   i, numItems;
      SearchOrderItem       item = null;
      
      tbdr = new StringBuffer();
      
      numItems = m_searchOrderItems.size();
      
      for(i = 0; i < numItems; i++)
      {
         item = (SearchOrderItem)m_searchOrderItems.get(i);
         
         tbdr.append(item.getSqlOrderItem());
           
         if (i < (numItems - 1))
            tbdr.append(" , ");        
      }
       
      return tbdr.toString();
      
   }  
   
   public String getSqlQual(int dbEngine) throws Exception
   {
      
      String qual = "";
      String where;     
      String orderBy;
      
      where   = getSqlWhere(dbEngine);
      orderBy = getSqlOrderBy();
      
      if (!where.equals(""))
         qual = qual + " WHERE " + where; 
      
      if (!orderBy.equals(""))
         qual = qual + " ORDER BY " + orderBy; 
      
      return qual;
      
   }
   
   // El nombre de la columna debe venir sin cualificar
   public void addSearchCondition(String colName, String opr, int dataType,
                                  Object val) throws Exception
   { 
      ArrayList vals = new ArrayList();
      
      vals.add(val);
      
      addSearchCondition(colName, opr, dataType, vals);   
      
   }  
   
   //El nombre de la columna debe venir sin cualificar
   public void addSearchCondition(String colName, String opr, int dataType,
                                  ArrayList vals) throws Exception
   {
      SearchConditionItem item = new SearchConditionItem(getQualifiedColName(colName), 
                                                         opr, dataType, vals);
      
      m_searchConditionItems.add(item);     
      
   }       
   
   //Hace la conversión de string al tipo que corresponda de base de datos
   // El nombre de la columna debe venir sin cualificar
   public void addSearchCondition(String colName, String opr, int dataType,
                                  String val, TextParserX parser) throws Exception
   {
      Object value;
      
      value = SearchUtil.parseSearchValue(parser, dataType, val);
      
      addSearchCondition(getQualifiedColName(colName), opr, 
                         dataType, val);
      
   } 
   
   // Hace la conversión de string al tipo que corresponda de base de datos
   // El nombre de la columna debe venir sin cualificar
   public void addSearchCondition(String colName, String opr, int dataType,
                                  String[] vals, TextParserX parser) throws Exception
   {
      ArrayList values;
      
      values = SearchUtil.parseSearchValues(parser, dataType, vals);
      
      addSearchCondition(getQualifiedColName(colName), opr, 
                         dataType, values);
      
   } 
   
   private String getQualifiedColName(String colName)
   {
      
      String text;

      text =  m_tblName + "." + colName;
      
      return text;
      
   } 
   
   public String getTblName()
   {      
      return m_tblName;      
   }
   
   // El nombre de la columna debe venir sin cualificar
   public void addSearchOrder(String colName, boolean desc) throws Exception
   {
      SearchOrderItem item = new SearchOrderItem(getQualifiedColName(colName), 
                                                 desc);
      
      m_searchOrderItems.add(item);     
      
   }  
      
} // class
