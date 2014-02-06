
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.*;
import java.util.Date;

public final class DaoFdrHdrRow implements DbInputRecord, DbOutputRecord
{
   public  int  m_id; 
   public  int  m_versStat;  
   public  int  m_refCount;
   public  int  m_acsType;
   public  int  m_acsId;   
   public  int  m_crtUsrId;
   public Date  m_crtTs;
   public  int  m_updUsrId;    
   public Date  m_updTs;
   public  int  m_accUsrId;    
   public Date  m_accTs; 
   public  int  m_accCount;   
   
      
   public DaoFdrHdrRow()
   {        
   } 
   
   public void setStatementValues(DbInputStatement stmt) throws Exception
   {
      
      int i = 1;
      
      stmt.setLongInteger(i++, m_id);
      stmt.setLongInteger(i++, m_versStat);     
      stmt.setLongInteger(i++, m_refCount);         
      stmt.setLongInteger(i++, m_acsType);         
      stmt.setLongInteger(i++, m_acsId); 
      stmt.setLongInteger(i++, m_crtUsrId);
      stmt.setDateTime(i++, m_crtTs);
      stmt.setLongInteger(i++, m_updUsrId);  
      stmt.setDateTime(i++, m_updTs); 
      stmt.setLongInteger(i++, m_accUsrId);  
      stmt.setDateTime(i++, m_accTs); 
      stmt.setLongInteger(i++, m_accCount);      
      
   }
   
   public void getStatementValues(DbOutputStatement stmt) throws Exception
   {
      
      int i = 1;
      
      m_id       = stmt.getLongInteger(i++);
      m_versStat = stmt.getLongInteger(i++);     
      m_refCount = stmt.getLongInteger(i++);         
      m_acsType  = stmt.getLongInteger(i++);         
      m_acsId    = stmt.getLongInteger(i++); 
      m_crtUsrId = stmt.getLongInteger(i++);
      m_crtTs    = stmt.getDateTime(i++);
      m_updUsrId = stmt.getLongInteger(i++);  
      m_updTs    = stmt.getDateTime(i++); 
      m_accUsrId = stmt.getLongInteger(i++);  
      m_accTs    = stmt.getDateTime(i++); 
      m_accCount = stmt.getLongInteger(i++);      
      
   }    
   
   
   
} // class
