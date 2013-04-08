
package es.ieci.tecdoc.isicres.admin.sbo.idoc.dao;


import java.util.Date;

import es.ieci.tecdoc.isicres.admin.base.dbex.DbInputRecord;
import es.ieci.tecdoc.isicres.admin.base.dbex.DbInputStatement;

public final class DaoPageAnnsRowUpd implements DbInputRecord
{
   public  String  m_data;     
   public  int     m_updUsrId;    
   public  Date    m_updTs;   
      
   public DaoPageAnnsRowUpd()
   {        
   } 
   
   public void setStatementValues(DbInputStatement stmt) throws Exception
   {
      
      int i = 1;
      
      stmt.setLongText(i++, m_data);       
      stmt.setLongInteger(i++, m_updUsrId);  
      stmt.setDateTime(i++, m_updTs); 
      
   }   
   
} // class
