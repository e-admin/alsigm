
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.*;
import java.util.Date;

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
