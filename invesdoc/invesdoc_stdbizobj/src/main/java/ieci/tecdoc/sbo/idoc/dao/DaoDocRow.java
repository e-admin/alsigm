
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.*;
import java.util.Date;

public final class DaoDocRow implements DbInputRecord, DbOutputRecord
{
   public  int    m_id; 
   public  int    m_fdrId; 
   public  int    m_archId;
   public  String m_name; 
   public  int    m_clfId;
   public  int    m_type;
   public  String m_title;
   public  String m_author;
   public  String m_keywords;  
   public  int    m_stat;
   public  int    m_refCount;
   public  String m_rems;   
   public  int    m_acsType;
   public  int    m_acsId;   
   public  int    m_crtUsrId;
   public Date    m_crtTs;
   public  int    m_updUsrId;    
   public Date    m_updTs; 
   public  int    m_accUsrId;    
   public Date    m_accTs; 
   public  int    m_accCount;
   public Date    m_ts; 
      
   public DaoDocRow()
   {        
   } 
   
   public void setStatementValues(DbInputStatement stmt) throws Exception
   {
      
      int i = 1;
      
      stmt.setLongInteger(i++, m_id);
      stmt.setLongInteger(i++, m_fdrId); 
      stmt.setLongInteger(i++, m_archId); 
      stmt.setShortText(i++, m_name);  
      stmt.setLongInteger(i++, m_clfId);       
      stmt.setLongInteger(i++, m_type);       
      stmt.setShortText(i++, m_title); 
      stmt.setShortText(i++, m_author); 
      stmt.setShortText(i++, m_keywords);        
      stmt.setLongInteger(i++, m_stat);     
      stmt.setLongInteger(i++, m_refCount); 
      stmt.setShortText(i++, m_rems);        
      stmt.setLongInteger(i++, m_acsType);         
      stmt.setLongInteger(i++, m_acsId); 
      stmt.setLongInteger(i++, m_crtUsrId);
      stmt.setDateTime(i++, m_crtTs);
      stmt.setLongInteger(i++, m_updUsrId);  
      stmt.setDateTime(i++, m_updTs); 
      stmt.setLongInteger(i++, m_accUsrId);  
      stmt.setDateTime(i++, m_accTs); 
      stmt.setLongInteger(i++, m_accCount);  
      stmt.setDateTime(i++, m_ts); 
           
   } 
   
   public void getStatementValues(DbOutputStatement stmt) throws Exception
   {
      
      int i = 1;
      
      m_id       = stmt.getLongInteger(i++);
      m_fdrId    = stmt.getLongInteger(i++); 
      m_archId   = stmt.getLongInteger(i++);   
      m_name     = stmt.getShortText(i++); 
      m_clfId    = stmt.getLongInteger(i++);         
      m_type     = stmt.getLongInteger(i++);       
      m_title    = stmt.getShortText(i++); 
      m_author   = stmt.getShortText(i++); 
      m_keywords = stmt.getShortText(i++);        
      m_stat     = stmt.getLongInteger(i++);     
      m_refCount = stmt.getLongInteger(i++); 
      m_rems     = stmt.getShortText(i++);        
      m_acsType  = stmt.getLongInteger(i++);         
      m_acsId    = stmt.getLongInteger(i++); 
      m_crtUsrId = stmt.getLongInteger(i++);
      m_crtTs    = stmt.getDateTime(i++);
      m_updUsrId = stmt.getLongInteger(i++);  
      m_updTs    = stmt.getDateTime(i++);
      m_accUsrId = stmt.getLongInteger(i++);  
      m_accTs    = stmt.getDateTime(i++); 
      m_accCount = stmt.getLongInteger(i++);  
      m_ts       = stmt.getDateTime(i++);  
      
   }   
   
   
   
} // class
