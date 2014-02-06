
package ieci.tecdoc.sbo.idoc.folder.search;

public class FolderSearchCompCond implements FolderSearchCondition
                                              
{ 
   /**
    * Clase que construye la condición sql que representa la condición de búsqueda
    */
   FolderSearchCondition m_condition1;
   FolderSearchCondition m_condition2;
   String                m_opr;
   
   
   public FolderSearchCompCond(FolderSearchCondition cond1, FolderSearchCondition cond2,
                               String opr) throws Exception
   {
      m_condition1 = cond1;
      m_condition2 = cond2;
      m_opr        = opr;
            
   } 
  
   public String getSqlCondition(int dbEngine) throws Exception
   {
      String          sqlCond1 = m_condition1.getSqlCondition(dbEngine);
      String          sqlCond2 = m_condition2.getSqlCondition(dbEngine);
      StringBuffer    tbdr;         
      
      tbdr = new StringBuffer();
      
      tbdr.append(" ( ");
      tbdr.append(" ( ").append(sqlCond1).append(" ) ");
      tbdr.append(m_opr);
      tbdr.append(" ( ").append(sqlCond2).append(" ) ");
      tbdr.append(" ) ");       
      
      return tbdr.toString();
   }
    
   
} // class
