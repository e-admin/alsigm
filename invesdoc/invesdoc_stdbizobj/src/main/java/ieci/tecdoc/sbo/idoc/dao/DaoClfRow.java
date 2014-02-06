
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.*;
import java.util.Date;

public final class DaoClfRow implements DbInputRecord, DbOutputRecord
{
   public  int    m_id; 
   public  int    m_fdrId; 
   public  String m_name; 
   public  int    m_type;
   public  int    m_parentId;
   public  int    m_info;
   public  int    m_stat;
   public  int    m_refCount;
   public  String m_rems;   
   public  int    m_acsType;
   public  int    m_acsId;   
   public  int    m_crtUsrId;
   public Date    m_crtTs;
   public  int    m_updUsrId;    
   public Date    m_updTs;   
      
   public DaoClfRow()
   {        
   } 
   
   public void setStatementValues(DbInputStatement stmt) throws Exception
   {
      
      int i = 1;
      
      stmt.setLongInteger(i++, m_id);
      stmt.setLongInteger(i++, m_fdrId); 
      stmt.setShortText(i++, m_name);        
      stmt.setLongInteger(i++, m_type);       
      stmt.setLongInteger(i++, m_parentId);  
      stmt.setLongInteger(i++, m_info); 
      stmt.setLongInteger(i++, m_stat);     
      stmt.setLongInteger(i++, m_refCount); 
      stmt.setShortText(i++, m_rems);        
      stmt.setLongInteger(i++, m_acsType);         
      stmt.setLongInteger(i++, m_acsId); 
      stmt.setLongInteger(i++, m_crtUsrId);
      stmt.setDateTime(i++, m_crtTs);
      stmt.setLongInteger(i++, m_updUsrId);  
      stmt.setDateTime(i++, m_updTs); 
           
   } 
   
   public void getStatementValues(DbOutputStatement stmt) throws Exception
   {
      
      int i = 1;
      
      m_id       = stmt.getLongInteger(i++);
      m_fdrId    = stmt.getLongInteger(i++);   
      m_name     = stmt.getShortText(i++);        
      m_type     = stmt.getLongInteger(i++);       
      m_parentId = stmt.getLongInteger(i++);  
      m_info     = stmt.getLongInteger(i++); 
      m_stat     = stmt.getLongInteger(i++);     
      m_refCount = stmt.getLongInteger(i++); 
      m_rems     = stmt.getShortText(i++);        
      m_acsType  = stmt.getLongInteger(i++);         
      m_acsId    = stmt.getLongInteger(i++); 
      m_crtUsrId = stmt.getLongInteger(i++);
      m_crtTs    = stmt.getDateTime(i++);
      m_updUsrId = stmt.getLongInteger(i++);  
      m_updTs    = stmt.getDateTime(i++); 
      
   }  
   
   
   
} // class
