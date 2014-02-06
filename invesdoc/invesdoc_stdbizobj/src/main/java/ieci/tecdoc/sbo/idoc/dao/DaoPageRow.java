
package ieci.tecdoc.sbo.idoc.dao;

import ieci.tecdoc.core.db.*;
import java.util.Date;

public final class DaoPageRow implements DbInputRecord, DbOutputRecord
{
   public  int    m_id; 
   public  int    m_fdrId; 
   public  String m_name; 
   public  int    m_sortOrder;
   public  int    m_docId;
   public  int    m_fileId;
   public  int    m_volId;   
   public  String m_loc; 
   public  int    m_annId;  
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
      
   public DaoPageRow()
   {        
   } 
   
   public void setStatementValues(DbInputStatement stmt) throws Exception
   {
      
      int i = 1;
      
      stmt.setLongInteger(i++, m_id);
      stmt.setLongInteger(i++, m_fdrId); 
      stmt.setShortText(i++, m_name);  
      stmt.setLongInteger(i++, m_sortOrder);       
      stmt.setLongInteger(i++, m_docId);       
      stmt.setLongInteger(i++, m_fileId);  
      stmt.setLongInteger(i++, m_volId); 
      stmt.setShortText(i++, m_loc);
      stmt.setLongInteger(i++, m_annId); 
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
           
   } 
   
    public void getStatementValues(DbOutputStatement stmt) throws Exception
   {
      
      int i = 1;
      
      m_id        = stmt.getLongInteger(i++);
      m_fdrId     = stmt.getLongInteger(i++);        
      m_name      = stmt.getShortText(i++); 
      m_sortOrder = stmt.getLongInteger(i++);       
      m_docId     = stmt.getLongInteger(i++);       
      m_fileId    = stmt.getLongInteger(i++);  
      m_volId     = stmt.getLongInteger(i++); 
      m_loc       = stmt.getShortText(i++);
      m_annId     = stmt.getLongInteger(i++); 
      m_stat      = stmt.getLongInteger(i++);     
      m_refCount  = stmt.getLongInteger(i++); 
      m_rems      = stmt.getShortText(i++);        
      m_acsType   = stmt.getLongInteger(i++);         
      m_acsId     = stmt.getLongInteger(i++); 
      m_crtUsrId  = stmt.getLongInteger(i++);
      m_crtTs     = stmt.getDateTime(i++);
      m_updUsrId  = stmt.getLongInteger(i++);  
      m_updTs     = stmt.getDateTime(i++);
      m_accUsrId  = stmt.getLongInteger(i++);  
      m_accTs      = stmt.getDateTime(i++); 
      m_accCount = stmt.getLongInteger(i++); 
   }   
    
   
   
   
} // class
