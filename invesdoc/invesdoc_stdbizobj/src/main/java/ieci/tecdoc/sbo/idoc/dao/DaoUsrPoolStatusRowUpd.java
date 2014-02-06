
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.*;
import java.util.Date;

public final class DaoUsrPoolStatusRowUpd implements DbInputRecord
{
   public  short   m_status; 
   public  Date    m_ts;   
      
   public DaoUsrPoolStatusRowUpd()
   {        
   } 
   
   public void setStatementValues(DbInputStatement stmt) throws Exception
   {
      
      int i = 1;
           
      stmt.setShortInteger(i++, m_status);  
      stmt.setDateTime(i++, m_ts); 
      
   }   
   
} // class
